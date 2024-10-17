package dev.pp.commands.command.bultin;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.commands.command.CommandExecutor;
import dev.pp.commands.command.CommandSpec;
import dev.pp.commands.command.CommandSpecs;
import dev.pp.text.documentation.SimpleDocumentation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.function.Supplier;

public class CommandsInfoCommandSpec {


    public static final @NotNull String NAME = "commands_info";


    public static CommandSpec<Void,Void> create (
        @NotNull Supplier<CommandSpecs> commandSpecsSupplier,
        @NotNull String cliExampleAppName,
        @Nullable Integer sortIndex ) {

        CommandExecutor<Void,Void> executor = parameters -> {

            PrintWriter writer = new PrintWriter ( System.out );
            CommandSpecs commandSpecs = commandSpecsSupplier.get();
            writeCommandsInfo ( writer, commandSpecs );
            writer.flush();
            return null;
        };

        return new CommandSpec<> (
            NAME, Set.of ( "cmdsi" ), null, executor, sortIndex,
            () -> new SimpleDocumentation (
                "Display Info About All Commands",
                "Display information about all available commands in the terminal",
                cliExampleAppName + " " + NAME )
        );
    }

    private static void writeCommandsInfo (
        @NotNull PrintWriter writer,
        @NotNull CommandSpecs commandSpecs ) throws IOException {

        writer.println ( "List of Commands" );
        writer.println ( "================");
        writer.println();

        for ( CommandSpec<?,?> commandSpec : commandSpecs.listSortedByName() ) {
            CommandInfoCommandSpec.writeCommandInfo ( writer, commandSpec );
            writer.println();
        }
    }
}
