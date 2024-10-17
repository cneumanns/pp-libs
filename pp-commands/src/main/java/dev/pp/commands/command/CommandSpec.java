package dev.pp.commands.command;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameters.MutableOrImmutableParameters;
import dev.pp.parameters.parameters.Parameters;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.MutableParameterSpecs;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class CommandSpec<I,O> {


    public static @NotNull <I,O> Builder<I,O> builder (
        @NotNull String name,
        @NotNull CommandExecutor<I,O> executor ) {

        return new Builder<> ( name, executor );
    }


    public static class Builder<I,O> {

        private final @NotNull String name;

        private @Nullable Set<String> alternativeNames;

        private @Nullable MutableParameterSpecs<I> mutableInputParameters;
        private @Nullable ParameterSpecs<I> inputParameters;

        private final @NotNull CommandExecutor<I,O> executor;

        private @Nullable Integer sortIndex;

        private @Nullable Supplier<SimpleDocumentation> documentationSupplier;


        public Builder (
            @NotNull String name,
            @NotNull CommandExecutor<I,O> executor ) {

            this.name = name;
            this.alternativeNames = null;
            this.mutableInputParameters = null;
            this.inputParameters = null;
            this.executor = executor;
            this.sortIndex = null;
            this.documentationSupplier = null;
        }


        public Builder<I,O> alternativeNames ( @Nullable Set<String> alternativeNames ) {
            this.alternativeNames = alternativeNames;
            return this;
        }

        public Builder<I,O> alternativeName ( @NotNull String alternativeName ) {
            this.alternativeNames = Set.of ( alternativeName );
            return this;
        }

        public Builder<I,O> inputParameters ( @Nullable MutableParameterSpecs<I> inputParameters ) {
            this.mutableInputParameters = inputParameters;
            return this;
        }

        public Builder<I,O> inputParameters ( @Nullable ParameterSpecs<I> inputParameters ) {
            this.inputParameters = inputParameters;
            return this;
        }

        public Builder<I,O> addInputParameter ( @NotNull ParameterSpec<I> parameter ) {
            if ( mutableInputParameters == null ) mutableInputParameters = new MutableParameterSpecs<> ();
            mutableInputParameters.add ( parameter );
            return this;
        }

        public Builder<I,O> sortIndex ( @Nullable Integer sortIndex ) {
            this.sortIndex = sortIndex;
            return this;
        }

        public Builder<I,O> documentationSupplier ( @Nullable Supplier<SimpleDocumentation> documentationSupplier ) {
            this.documentationSupplier = documentationSupplier;
            return this;
        }

        public Builder<I,O> documentation ( @Nullable SimpleDocumentation documentation ) {
            this.documentationSupplier = () -> documentation;
            return this;
        }

        public Builder<I,O> documentation (
            @NotNull String title,
            @Nullable String description,
            @Nullable String examples ) {

            return documentation ( new SimpleDocumentation ( title, description, examples ) );
        }


        public CommandSpec<I,O> build() {

            ParameterSpecs<I> mergedInputParameters;
            if ( mutableInputParameters != null && inputParameters != null ) {
                mutableInputParameters.addAll ( inputParameters );
                mergedInputParameters = mutableInputParameters.makeImmutableOrNull();
            } else if ( mutableInputParameters != null ) {
                mergedInputParameters = mutableInputParameters.makeImmutableOrNull();
            } else if ( inputParameters != null ) {
                mergedInputParameters = inputParameters;
            } else {
                mergedInputParameters = null;
            }

            return new CommandSpec<> (
                name,
                alternativeNames,
                mergedInputParameters,
                executor,
                sortIndex,
                documentationSupplier );
        }
    }


    private final @NotNull String name;
    public @NotNull String getName() { return name; }

    private final @Nullable Set<String> alternativeNames;
    public @Nullable Set<String> getAlternativeNames() { return alternativeNames; }

    private final @Nullable ParameterSpecs<I> inputParameterSpecs;
    public @Nullable ParameterSpecs<I> getInputParameterSpecs() { return inputParameterSpecs; }

    private final @NotNull CommandExecutor<I,O> executor;
    public @NotNull CommandExecutor<I,O> getExecutor() { return executor; }

    private @Nullable Integer sortIndex;
    public @Nullable Integer getSortIndex() { return sortIndex; }
    public void setSortIndex ( @Nullable Integer sortIndex ) { this.sortIndex = sortIndex; }

    private final @Nullable Supplier<SimpleDocumentation> documentationSupplier;
    public @Nullable Supplier<SimpleDocumentation> getDocumentationSupplier() { return documentationSupplier; }

    /* TODO?
		boolean allowProcessExitAfterExecution
		@Nullable String menuP
        boolean obsolete
     */


    public CommandSpec (
        @NotNull String name,
        @Nullable Set<String> alternativeNames,
        @Nullable ParameterSpecs<I> inputParameterSpecs,
        @NotNull CommandExecutor<I,O> executor,
        @Nullable Integer sortIndex,
        @Nullable Supplier<SimpleDocumentation> documentationSupplier ) {

        if ( alternativeNames != null && alternativeNames.contains ( name ) ) throw new IllegalArgumentException (
            "Name '" + name + "' cannot be re-used in the list of alternative names." );

        this.name = name;
        this.alternativeNames = alternativeNames;
        this.inputParameterSpecs = inputParameterSpecs;
        this.executor = executor;
        this.sortIndex = sortIndex;
        this.documentationSupplier = documentationSupplier;
    }


    // Execute

    public O execute ( @Nullable Map<String, String> stringMap ) throws Exception {

        return executor.execute ( stringMap, inputParameterSpecs );
    }

    public O execute (
        @Nullable MutableOrImmutableParameters<String> stringParameters,
        @Nullable ParameterSpecs<I> parameterSpecs ) throws Exception {

        return executor.execute ( stringParameters, parameterSpecs );
    }

    public O execute (
        @Nullable Parameters<I> parameters ) throws Exception {

        return executor.execute ( parameters );
    }


    // Queries

    public @NotNull List<String> allNames() {

        ArrayList<String> list = new ArrayList<>();
        list.add ( name );
        if ( alternativeNames != null ) list.addAll ( alternativeNames );
        return list;
    }

    public @Nullable String alternativeNamesAsString ( @NotNull String separator ) {

        if ( alternativeNames == null ) {
            return null;
        } else {
            return namesSetToString ( alternativeNames, separator );
        }
    }

    public @Nullable String alternativeNamesAsString() {
        return alternativeNamesAsString ( ", " );
    }

    private @Nullable String namesSetToString ( @Nullable Set<String> names, @NotNull String separator ) {

        if ( names == null || names.isEmpty() ) {
            return null;
        } else {
            return String.join ( separator, names );
        }
    }

    // public int sortIndexOrZero() { return sortIndex == null ? 0 : sortIndex; }

    public int sortIndexOrMax() { return sortIndex == null ? Integer.MAX_VALUE : sortIndex; }


    // Documentation

    public @Nullable SimpleDocumentation documentation() {
        return documentationSupplier != null ? documentationSupplier.get() : null;
    }

    public @Nullable String documentationTitle() {
        SimpleDocumentation documentation = documentation ();
        return documentation != null ? documentation.getTitle() : null;
    }

    public @Nullable String documentationDescription() {
        SimpleDocumentation documentation = documentation ();
        return documentation != null ? documentation.getDescription() : null;
    }

    public @Nullable String documentationExamples() {
        SimpleDocumentation documentation = documentation ();
        return documentation != null ? documentation.getExamples() : null;
    }


    @Override
    public String toString() { return name; }
}
