package dev.pp.commands.cli;

import dev.pp.basics.annotations.NotNull;
import dev.pp.commands.command.CommandSpec;
import dev.pp.commands.command.CommandSpecs;
import dev.pp.commands.command.bultin.HelpCommandSpec;
import dev.pp.commands.errors.CLIExceptionHandler;
import dev.pp.parameters.cli.CLIArguments;
import dev.pp.parameters.parameters.Parameters;

public class CLICommands {

    public static void runAndExit (
        @NotNull String[] cliArguments,
        @NotNull CommandSpecs commandSpecs,
        @NotNull String appName ) {

        System.exit ( runAndReturnExitStatus ( cliArguments, commandSpecs, appName ) );
    }

    public static int runAndReturnExitStatus (
        @NotNull String[] cliArguments,
        @NotNull CommandSpecs commandSpecs,
        @NotNull String appName ) {

        if ( cliArguments.length < 1 ) {
            System.err.println ( "Usage: " + appName + " {command_name} {arguments}" );
            if ( commandSpecs.containsHelpCommand() ) {
                System.err.println ( "To display help, type:" );
                System.err.println ( appName + " " + HelpCommandSpec.DEFAULT_NAME );
                return 1;
            }
        }

        try {
            run ( cliArguments, commandSpecs );
            return 0;
        } catch ( Throwable e ) {
            CLIExceptionHandler.handleThrowable ( e );
            return 1;
        }
    }

    public static <I,O> O run (
        @NotNull String[] cliArguments,
        @NotNull CommandSpecs commandSpecs ) throws Exception {

        @NotNull CommandSpec<I,O> commandSpec = getCommandSpec ( cliArguments, commandSpecs );
        /*
        Parameters<I> parameters = CLIArguments.parseToParameters (
            arguments, 1, null, commandSpec.getInputParameters() );
        return commandSpec.execute ( parameters );
         */
        Parameters<String> stringParameters = CLIArguments.parseToStringParameters (
            cliArguments, 1, null, commandSpec.getInputParameterSpecs () );
        return commandSpec.execute ( stringParameters, commandSpec.getInputParameterSpecs () );
    }

    private static <I,O> CommandSpec<I,O> getCommandSpec (
        @NotNull String[] cliArguments,
        @NotNull CommandSpecs commandSpecs ) throws CLIArgumentsException {

        if ( cliArguments.length < 1 ) {
            throw new CLIArgumentsException ( "A command name must be specified. Valid command names are: " +
                commandSpecs.sortedNamesAsString(),
                cliArguments );
        }

        String commandName = cliArguments[0];
        if ( commandName == null || commandName.isEmpty() ) {
            throw new CLIArgumentsException ( "The command name cannot be empty. Valid command names are: " +
                commandSpecs.sortedNamesAsString(),
                cliArguments );
        }

        if ( ! commandSpecs.containsName ( commandName ) ) {
            throw new CLIArgumentsException ( "Command '" + commandName + "' does not exist. Valid command names are: " +
                commandSpecs.sortedNamesAsString(),
                cliArguments );
        }

        return commandSpecs.get ( commandName );
    }
}
