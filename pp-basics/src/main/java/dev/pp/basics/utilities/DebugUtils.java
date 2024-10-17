package dev.pp.basics.utilities;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

public class DebugUtils {

    public static void writeNameValue ( @Nullable String name, @Nullable Object value ) {

        System.err.print ( name + ": " );
        System.err.println ( "<" + value + ">" );
    }

    public static void write ( @NotNull String string ) {
        System.err.print ( string );
    }

    public static void writeLine ( @NotNull String string ) {
        System.err.println ( string );
    }

    public static void writeLineBreak() {
        System.err.println();
    }
}
