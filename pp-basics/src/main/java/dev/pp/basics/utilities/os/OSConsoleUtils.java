package dev.pp.basics.utilities.os;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.io.Console;
import java.io.PrintWriter;

public class OSConsoleUtils {

    private static final @Nullable Console console = System.console();
    private static final @Nullable PrintWriter writer = console != null ? console.writer() : null;

    public static boolean hasConsole() { return console != null ;}

    public static void write ( @NotNull String string ) {
        assert writer != null;

        writer.print ( string );
        writer.flush();
    }

    public static void writeLine ( @NotNull String string ) {
        assert writer != null;

        writer.println ( string );
    }

    public static void writeLineBreak() {
        assert writer != null;

        writer.println();
    }

    public static @Nullable String readLine() {
        assert console != null;

        return console.readLine();
    }

    public static @Nullable String askString ( @NotNull String message ) {
        assert console != null;

        return console.readLine ( message );
    }

    public static void askPressEnterToContinue() {

        askPressEnter ( "Press <Enter> to continue: " );
    }

    public static void askPressEnter ( @Nullable String message ) {
        assert console != null;

        if ( message != null ) write ( message );
        readLine();
    }
}
