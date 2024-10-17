package dev.pp.basics.utilities.character;

public class CharChecks {

    public static boolean isSpaceOrTab ( char c ) {

        return c == ' '
            || c == '\t';
    }

    public static boolean isNotSpaceOrTab ( char c ) { return ! isSpaceOrTab ( c ); }

    public static boolean isLineBreak ( char c ) {

        return c == '\n'
            || c == '\r';
    }

    public static boolean isUnixNewLine ( char c ) {

        return c == CharConstants.UNIX_NEW_LINE;
    }

    public static boolean isWindowsLineBreakStart ( char c ) {

        return c == CharConstants.WINDOWS_LINE_BREAK_START;
    }

    public static boolean isNotLineBreak ( char c ) { return ! isLineBreak ( c ); }

    public static boolean isSpaceOrTabOrLineBreak ( char c ) {

        return c == ' '
            || c == '\n'
            || c == '\r'
            || c == '\t';
    }
}
