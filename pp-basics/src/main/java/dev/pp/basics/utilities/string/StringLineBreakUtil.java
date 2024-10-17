package dev.pp.basics.utilities.string;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

public class StringLineBreakUtil {

    public static @Nullable String removeOptionalLineBreakAtEnd ( @NotNull String string ) {

        StringBuilder sb = new StringBuilder ( string );
        StringBuilderUtils.removeOptionalLineBreakAtEnd ( sb );
        return sb.isEmpty() ? null : sb.toString();
    }

    public static @NotNull String replaceWindowsLineBreaksWithUnixNewLines ( @NotNull String string ) {

        return string.replace ( StringConstants.WINDOWS_LINE_BREAK, StringConstants.UNIX_NEW_LINE );
    }

    public static @NotNull String replaceLineBreaksWithEscapeCharacters ( @NotNull String string ) {

        return string
            .replace ( StringConstants.UNIX_NEW_LINE, "\\n" )
            .replace ( StringConstants.WINDOWS_LINE_BREAK_START, "\\r" );
    }
}
