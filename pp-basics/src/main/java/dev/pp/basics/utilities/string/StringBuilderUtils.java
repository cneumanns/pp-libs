package dev.pp.basics.utilities.string;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

public class StringBuilderUtils {

    public static StringBuilder removeOptionalLineBreakAtEnd ( @NotNull StringBuilder sb ) {

        if ( sb.length() == 0 ) return sb;

        int lastIndex = sb.length() - 1;
        if ( sb.charAt ( lastIndex ) != '\n' ) return sb;

        sb.deleteCharAt ( lastIndex );

        lastIndex = lastIndex - 1;
        if ( sb.charAt ( lastIndex ) == '\r' ) sb.deleteCharAt ( lastIndex );

        return sb;
    }

    public static void appendLine ( @NotNull StringBuilder sb, @Nullable String line ) {

        appendIfNotNull ( sb, line );
        appendLineBreak ( sb );
    }

    public static void appendLineBreak ( @NotNull StringBuilder sb ) {

        sb.append ( StringConstants.OS_LINE_BREAK );
    }

    public static void appendIfNotNull ( @NotNull StringBuilder sb, @Nullable String string ) {

        if ( string != null ) sb.append ( string );
    }
}
