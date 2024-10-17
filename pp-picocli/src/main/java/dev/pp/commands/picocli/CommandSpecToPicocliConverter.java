package dev.pp.commands.picocli;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.MutableOrImmutableParameterSpecs;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import picocli.CommandLine;

public class CommandSpecToPicocliConverter {

    public static @NotNull picocli.CommandLine.Model.CommandSpec convert (
        @NotNull dev.pp.commands.command.CommandSpec<?,?> ppCommandSpec ) {

        picocli.CommandLine.Model.CommandSpec picocliCommandSpec =
            picocli.CommandLine.Model.CommandSpec.create()
            .name ( ppCommandSpec.getName() );

        if ( ppCommandSpec.getAlternativeNames() != null ) {
            picocliCommandSpec.aliases ( ppCommandSpec.getAlternativeNames().toArray ( new String[0] ) );
        }

        @Nullable String title = ppCommandSpec.documentationTitle ();
        if ( title != null ) {
            picocliCommandSpec.usageMessage ( new CommandLine.Model.UsageMessageSpec().description ( title ) );
        }

        ParameterSpecs<?> inputParameters = ppCommandSpec.getInputParameterSpecs ();
        if ( inputParameters != null ) {
            addParameterSpecsToPicocliCommandSpec ( inputParameters, picocliCommandSpec );
        }

        return picocliCommandSpec;
    }

    private static void addParameterSpecsToPicocliCommandSpec (
        @NotNull MutableOrImmutableParameterSpecs<?> parameterSpecs,
        @NotNull picocli.CommandLine.Model.CommandSpec picocliCommandSpec ) {

        for ( ParameterSpec<?> parameterSpec : parameterSpecs.listSortedByIndex () ) {
            @Nullable Integer positionalIndex = parameterSpec.getPosition ();
            if ( positionalIndex == null ) {
                picocliCommandSpec.addOption ( ParameterSpecToPicocliConverter.convertToStringOption ( parameterSpec ) );
            } else {
                picocliCommandSpec.addPositional ( ParameterSpecToPicocliConverter.convertToStringPositional ( parameterSpec ) );
            }
        }
    }
}
