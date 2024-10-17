package dev.pp.commands.command.bultin;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.commands.command.CommandExecutor;
import dev.pp.commands.command.CommandSpec;
import dev.pp.commands.command.CommandSpecs;
import dev.pp.text.documentation.SimpleDocumentation;
import dev.pp.texttable.data.impls.FormFields;
import dev.pp.texttable.writer.pretty.utilities.TextFormWriterUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.function.Supplier;

public class HelpCommandSpec {


    public static final @NotNull String DEFAULT_NAME = "help";


    public static CommandSpec<Void,Void> create (
        @NotNull Supplier<CommandSpecs> commandSpecs,
        @NotNull String cliExampleAppName,
        @Nullable String cliExampleCommandName,
        @Nullable String cliExampleArguments,
        @NotNull String name,
        @Nullable Set<String> alternativeNames,
        @Nullable Integer sortIndex,
        @Nullable Supplier<SimpleDocumentation> documentationSupplier ) {

            CommandExecutor<Void,Void> executor = parameters -> {
                PrintWriter writer = new PrintWriter ( System.out );
                writeHelp ( writer, commandSpecs, cliExampleAppName, cliExampleCommandName, cliExampleArguments );
                writer.flush();
                return null;
            };
            return new CommandSpec<> (
                name, alternativeNames, null, executor, sortIndex, documentationSupplier );
    }

    public static CommandSpec<Void,Void> create (
        @NotNull Supplier<CommandSpecs> commands,
        @NotNull String cliExampleAppName,
        @Nullable String cliExampleCommandName,
        @Nullable String cliExampleArguments,
        @Nullable Integer sortIndex ) {

        return create (
            commands, cliExampleAppName, cliExampleCommandName, cliExampleArguments,
            DEFAULT_NAME,
            new LinkedHashSet<> ( List.of ( "-h", "--help", "/h", "/help" ) ),
            sortIndex,
            () -> new SimpleDocumentation (
                "Display Help",
                "Display help in the terminal",
                cliExampleAppName + " " + DEFAULT_NAME )
            );
    }


    private static void writeHelp (
        @NotNull PrintWriter writer,
        @NotNull Supplier<CommandSpecs> commandSpecsSupplier,
        @NotNull String cliExampleAppName,
        @Nullable String cliExampleCommandName,
        @Nullable String cliExampleArguments ) throws IOException {

        writer.println ( "The following commands are available:" );
        writer.println();
        CommandSpecs commandSpecs = commandSpecsSupplier.get();
        int labelWidth = 20;
        int valueWidth = 120 - 2 - labelWidth;
        TextFormWriterUtil.writeFormFields (
            commandSpecsFormFields ( commandSpecs, labelWidth, valueWidth ), labelWidth, valueWidth, writer );
        writer.println();

        writer.println ( "Note: Alternative (shorter) command names are shown in parenthesis." );
        writer.println();

        writer.println ( "To execute a command, type:" );
        writer.print ( cliExampleAppName );
        writer.println ( " {command_name} {arguments}" );
        if ( cliExampleCommandName != null ) {
            writer.println ( "For example:" );
            writer.print ( cliExampleAppName );
            writer.print ( " " );
            writer.print ( cliExampleCommandName );
            if ( cliExampleArguments != null ) {
                writer.print ( " " );
                writer.print ( cliExampleArguments );
            }
            writer.println();
        }
        writer.println();

        writer.println ( "Note: Command- and argument-names are case-insensitive." );
        writer.println ( "      (e.g. foo, Foo, and FOO are the same)." );
        writer.println();

        if ( commandSpecs.containsName ( CommandInfoCommandSpec.NAME ) ) {
            writer.println ( "For more information about a specific command, use the '" + CommandInfoCommandSpec.NAME + "' command," );
            writer.println ( "followed by the command name." );
            writer.println ( "For example:" );
            writer.print ( cliExampleAppName );
            writer.print ( " " );
            writer.print ( CommandInfoCommandSpec.NAME );
            writer.print ( " " );
            writer.print ( cliExampleCommandName );
            writer.println();
            writer.println();
        }

        if ( commandSpecs.containsName ( CommandsInfoCommandSpec.NAME ) ) {
            writer.println ( "For more information about all commands, use the '" + CommandsInfoCommandSpec.NAME + "' command." );
            writer.println ( "For example:" );
            writer.print ( cliExampleAppName );
            writer.print ( " " );
            writer.print ( CommandsInfoCommandSpec.NAME );
            writer.println();
            writer.println();
        }
    }

    private static FormFields<String> commandSpecsFormFields (
        @NotNull CommandSpecs commandSpecs, int labelWidth, int valueWidth ) {

        FormFields<String> formFields = new FormFields<String>()
            .add ( "Name", "Title" )
            .add ( "-".repeat ( labelWidth ), "-".repeat ( valueWidth ) );

        for ( CommandSpec<?,?> commandSpec : commandSpecs.listSortedBySortIndexThenByName() ) {

            formFields.add ( commandSpec.getName(), commandSpec.documentationTitle() );

            String alternativeNames = commandSpec.alternativeNamesAsString();
            if ( alternativeNames != null ) {
                formFields.add ( "(" + alternativeNames + ")", null );
            }
        }

        return formFields;
    }
}
