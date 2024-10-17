package dev.pp.commands.command.bultin;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.commands.command.CommandExecutor;
import dev.pp.commands.command.CommandSpec;
import dev.pp.commands.command.CommandSpecs;
import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.MutableParameterSpecs;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import dev.pp.text.documentation.SimpleDocumentation;
import dev.pp.texttable.data.impls.FormFields;
import dev.pp.texttable.writer.pretty.utilities.TextFormWriterUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class CommandInfoCommandSpec {


    public static final @NotNull String NAME = "command_info";

    public static final @NotNull ParameterSpec<String> COMMAND_NAME_PARAMETER_SPEC = new ParameterSpec.Builder<> (
        "command_name", CommonDataTypes.STRING )
        .alternativeName ( "cn" )
        .position ( 0 )
        .documentation ( "Command Name",
            "The name of the command.",
            null )
        .build();

    public static final @NotNull ParameterSpecs<String> PARAMETER_SPECS = new MutableParameterSpecs<String>()
        .add ( COMMAND_NAME_PARAMETER_SPEC )
        .makeImmutable();


    public static CommandSpec<String,Void> create (
        @NotNull Supplier<CommandSpecs> commandSpecsSupplier,
        @NotNull String cliExampleAppName,
        @Nullable String cliExampleCommandName,
        @Nullable Integer sortIndex ) {

        CommandExecutor<String,Void> executor = parameters -> {

            assert parameters != null;
            PrintWriter writer = new PrintWriter ( System.out );
            String commandName = parameters.nonNullValue ( COMMAND_NAME_PARAMETER_SPEC );
            CommandSpecs commandSpecs = commandSpecsSupplier.get();
            CommandSpec<?,?> commandSpec = commandSpecs.getOrNull ( commandName );
            if ( commandSpec == null ) {
                writer.println ( "Command '" + commandName + "' does not exist." );
                writer.println ( "The following commands are available: " + commandSpecs.sortedNamesAsString() );
            } else {
                writeCommandInfo ( writer, commandSpec );
            }
            writer.flush();
            return null;
        };

        return new CommandSpec<> (
            NAME, Set.of ( "cmdi" ), PARAMETER_SPECS, executor, sortIndex,
            () -> new SimpleDocumentation (
                "Display Info About a Command",
                "Display information about a specific command in the terminal",
                cliExampleAppName + " " + NAME + " " + cliExampleCommandName )
            );
    }

    static void writeCommandInfo (
        @NotNull PrintWriter writer,
        @NotNull CommandSpec<?,?> commandSpec ) throws IOException {

        String title = commandSpec.documentationTitle ();
        if ( title == null ) title = commandSpec.getName ();
        writer.println ( title );
        writer.println ( "-".repeat ( title.length () ) );
        writer.println ();

        ParameterSpecs<?> specs = commandSpec.getInputParameterSpecs();
        List<? extends ParameterSpec<?>> parametersSpecs = specs == null ? null : specs.listSortedByPositionalIndexThenName();
        TextFormWriterUtil.writeFormFields (
            commandSpecFormFields ( commandSpec, parametersSpecs ), 20, 120 - 20 - 2, writer );
        writer.println();

        writeParameters ( writer, parametersSpecs );
    }

    private static FormFields<String> commandSpecFormFields (
        @NotNull CommandSpec<?,?> commandSpec,
        @Nullable List<? extends ParameterSpec<?>> parametersSpecs ) {

        FormFields<String> formFields = new FormFields<String>()
            .add ( "Command name", commandSpec.getName() )
            .addIfNonNullValue ( "Alt. name(s)", commandSpec.alternativeNamesAsString() )
            .addIfNonNullValue ( "Description", commandSpec.documentationDescription() )
            .addIfNonNullValue ( "Example(s)", commandSpec.documentationExamples() );

        if ( parametersSpecs == null || parametersSpecs.isEmpty() ) {
            formFields.add ( "Parameters", "none" );
        }

        return formFields;
    }

    private static void writeParameters (
        @NotNull PrintWriter writer,
        @Nullable List<? extends ParameterSpec<?>> parametersSpecs ) throws IOException {

        if ( parametersSpecs == null ) return;

        writer.println ( parametersSpecs.size() == 1 ? "Parameter:" : "Parameters:" );
        for ( ParameterSpec<?> parameterSpec : parametersSpecs ) {
            TextFormWriterUtil.writeFormFields (
                parameterSpecFormFields ( parameterSpec ), 20, 120 - 20 - 2, writer );
            writer.println();
        }
    }

    private static FormFields<String> parameterSpecFormFields (
        @NotNull ParameterSpec<?> parametersSpec ) {

        return new FormFields<String>()
            .addIfNonNullValue ( "Title", parametersSpec.documentationTitle() )
            .add ( "Name", parametersSpec.getName() )
            .addIfNonNullValue ( "Alt. name(s)", parametersSpec.alternativeNamesAsString() )
            .addIfNonNullValue ( "Description", parametersSpec.documentationDescription() )
            .addIfNonNullValue ( "Example(s)", parametersSpec.documentationExamples() )
            .addIfNonNullValue ( "Position", parametersSpec.positionForHumansAsString() )
            .add ( "Type", parametersSpec.typeName() )
            .addIfNonNullValue ( "Default value", parametersSpec.defaultValueAsStringOrElse ( "none" ) )
            .add ( "Required", parametersSpec.requiredAsString() );
    }
}
