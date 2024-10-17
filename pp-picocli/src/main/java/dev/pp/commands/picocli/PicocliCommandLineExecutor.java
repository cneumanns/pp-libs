package dev.pp.commands.picocli;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.commands.command.CommandSpecs;
import dev.pp.commands.errors.CLIExceptionHandler;
import dev.pp.parameters.parameterspecs.ParameterSpecs;
import picocli.CommandLine;
import picocli.CommandLine.ParseResult;

import java.util.Map;

public class PicocliCommandLineExecutor {

    public static int executeCommand (
        @NotNull String[] CLIArgs,
        @NotNull String versionInfo,
        @NotNull CommandSpecs commandSpecs ) {

        try {
            picocli.CommandLine.Model.CommandSpec picocliMainCommand = picocli.CommandLine.Model.CommandSpec.create()
                .mixinStandardHelpOptions ( true )
                .version ( versionInfo );

            CommandLine commandLine = new CommandLine ( picocliMainCommand )
                .setSubcommandsCaseInsensitive ( true )
                .setOptionsCaseInsensitive ( true )
                .setCaseInsensitiveEnumValuesAllowed ( true );

            for ( dev.pp.commands.command.CommandSpec<?,?> ppCommandSpec : commandSpecs.list () ) {
                commandLine.addSubcommand ( CommandSpecToPicocliConverter.convert ( ppCommandSpec ) );
            }

            CommandLine.ParseResult parseResult = commandLine.parseArgs ( CLIArgs );

            if ( ! parseResult.hasSubcommand() ) {
                executeMainCommand ( parseResult );
            } else {
                executeSubCommand ( parseResult, commandSpecs );
            }

            return 0;

        } catch ( Throwable e ) {
            CLIExceptionHandler.handleThrowable ( e );
            return 1;
        }
    }

    private static void executeMainCommand ( ParseResult parseResult ) throws Exception {

        if ( ! CommandLine.printHelpIfRequested ( parseResult ) ) {
            throw new Exception ( "Command line arguments are required. Use option '-h' to get help." );
        }
    }

    private static void executeSubCommand ( ParseResult mainParseResult, @NotNull CommandSpecs commandSpecs ) throws Exception {

        ParseResult subParseResult = mainParseResult.subcommand();
        picocli.CommandLine.Model.CommandSpec picocliCommandSpec = subParseResult.commandSpec();
        String commandName = picocliCommandSpec.name();

        dev.pp.commands.command.CommandSpec<?,?> ppCommandSpec = commandSpecs.get ( commandName );
        ParameterSpecs<?> parameterSpecs = ppCommandSpec.getInputParameterSpecs ();

        @Nullable Map<String, String> stringParams =
            parameterSpecs != null
            ? PicocliHelper.parseResultToStringMap ( subParseResult, parameterSpecs )
            : null;

        ppCommandSpec.execute ( stringParams );
    }
}
