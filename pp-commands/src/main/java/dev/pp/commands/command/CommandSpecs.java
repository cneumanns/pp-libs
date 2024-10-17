package dev.pp.commands.command;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.commands.command.bultin.CommandInfoCommandSpec;
import dev.pp.commands.command.bultin.CommandsInfoCommandSpec;
import dev.pp.commands.command.bultin.HelpCommandSpec;

import java.util.*;

public class CommandSpecs {


    private final @NotNull Map<String, CommandSpec<?,?>> allNamesMap;
    private final @NotNull List<CommandSpec<?,?>> list;


    public CommandSpecs () {

        this.list = new ArrayList<> ();
        this.allNamesMap = new HashMap<>();
    }


    public CommandSpecs add ( @NotNull CommandSpec<?,?> commandSpec, @Nullable Integer sortIndex ) {

        commandSpec.setSortIndex ( sortIndex );
        return add ( commandSpec );
    }

    public CommandSpecs add ( @NotNull CommandSpec<?,?> commandSpec ) {

        // name must be unique
        for ( String name : commandSpec.allNames () ) {
            checkNotExists ( name );
            allNamesMap.put ( name.toLowerCase(), commandSpec );
        }

        list.add ( commandSpec );

        return this;
    }

    public CommandSpecs addHelpCommand (
        @NotNull String cliExampleAppName,
        @Nullable String cliExampleCommandName,
        @Nullable String cliExampleArguments,
        @Nullable Integer sortIndex ) {

        return add ( HelpCommandSpec.create (
            () -> this, cliExampleAppName, cliExampleCommandName, cliExampleArguments, sortIndex ) );
    }

    public CommandSpecs addCommandInfoCommand (
        @NotNull String cliExampleAppName,
        @Nullable String cliExampleCommandName,
        @Nullable Integer sortIndex ) {

        return add ( CommandInfoCommandSpec.create (
            () -> this, cliExampleAppName, cliExampleCommandName, sortIndex ) );
    }

    public CommandSpecs addCommandsInfoCommand (
        @NotNull String cliExampleAppName,
        @Nullable Integer sortIndex ) {

        return add ( CommandsInfoCommandSpec.create (
            () -> this, cliExampleAppName, sortIndex ) );
    }


    // Getters

    public @NotNull <I,O> CommandSpec<I,O> get ( @NotNull String name ) {

        CommandSpec<I,O> command = getOrNull ( name );
        if ( command != null ) {
            return command;
        } else {
            throw new IllegalArgumentException ( "Command '" + name + "' does not exist." );
        }
    }

    public @Nullable <I,O> CommandSpec<I,O> getOrNull ( @NotNull String name ) {

        if ( containsName ( name ) ) {
            @SuppressWarnings ( "unchecked" )
            CommandSpec<I,O> result = (CommandSpec<I,O>) allNamesMap.get ( name.toLowerCase() );
            return result;
        } else {
            return null;
        }
    }

    public @NotNull List<CommandSpec<?,?>> list() { return new ArrayList<> ( list ); }

    public @NotNull List<CommandSpec<?,?>> listSortedByName() {

        return list
            .stream()
            .sorted ( Comparator.comparing ( CommandSpec::getName ) )
            .toList();
    }

    public @NotNull List<CommandSpec<?,?>> listSortedBySortIndex () {

        return list
            .stream()
            .sorted ( Comparator.comparingInt ( CommandSpec::sortIndexOrMax ) )
            .toList();
    }


    public @NotNull List<CommandSpec<?,?>> listSortedBySortIndexThenByName() {

        return list
            .stream()
            .sorted ( (commandSpec1, commandSpec2) -> {
                int index1 = commandSpec1.sortIndexOrMax();
                int index2 = commandSpec2.sortIndexOrMax();
                if ( index1 != index2 ) {
                    return Integer.compare ( index1, index2 );
                } else {
                    return commandSpec1.getName().compareTo ( commandSpec2.getName() );
                }
            } )
            .toList();
    }


    // Names

    public boolean containsName ( @NotNull String name ) { return allNamesMap.containsKey ( name.toLowerCase() ); }

    public boolean containsHelpCommand() { return containsName ( HelpCommandSpec.DEFAULT_NAME ); }

    public @Nullable Set<String> sortedNames() {

        Set<String> set = new LinkedHashSet<>();
        for ( CommandSpec<?,?> commandSpec : listSortedByName () ) {
            set.add ( commandSpec.getName() );
        }

        return set.isEmpty() ? null : set;
    }

    public @Nullable String sortedNamesAsString() {
        return sortedNamesAsString ( ", " );
    }

    public @Nullable String sortedNamesAsString ( @NotNull String separator ) {

        return namesSetToString ( sortedNames(), separator );
    }

    private @Nullable String namesSetToString ( @Nullable Set<String> names, @NotNull String separator ) {

        if ( names == null || names.isEmpty() ) {
            return null;
        } else {
            return String.join ( separator, names );
        }
    }


    /*
    private void checkExists ( @NotNull String name ) {

        if ( ! containsName ( name ) )
            throw new IllegalArgumentException ( "Command '" + name + "' does not exist." );
    }
     */

    private void checkNotExists ( @NotNull String name ) {

        if ( containsName ( name ) )
            throw new IllegalArgumentException ( "Command '" + name + "' exists already." );
    }
}
