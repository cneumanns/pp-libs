package dev.pp.basics.utilities.string;

import dev.pp.basics.annotations.NotNull;

public class StringConstants {

    public static final @NotNull String OS_LINE_BREAK = System.getProperty ( "line.separator" );
    public static final @NotNull String UNIX_NEW_LINE = String.valueOf ( '\n' );
    public static final @NotNull String WINDOWS_LINE_BREAK = "\r\n";
    public static final @NotNull String WINDOWS_LINE_BREAK_START = "\r";

    public static final @NotNull String NULL_AS_STRING = "null";
}
