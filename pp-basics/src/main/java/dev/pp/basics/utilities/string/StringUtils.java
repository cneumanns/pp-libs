package dev.pp.basics.utilities.string;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.util.regex.Pattern;

public class StringUtils {

    public static @Nullable String stringFromReader ( @NotNull Reader reader ) throws IOException {

        int charArraySize = 8 * 1024;
        char[] charArray = new char [ charArraySize ];
        StringBuilder sb = new StringBuilder();
        int numCharsRead;
        while ( (numCharsRead = reader.read ( charArray, 0, charArraySize )) != -1 ) {
            sb.append ( charArray, 0, numCharsRead );
        }
        return sb.isEmpty() ? null : sb.toString();
    }

    public static boolean stringMatchesRegex ( @NotNull String string, @NotNull Pattern pattern ) {

        return pattern.matcher ( string ).matches();
    }

    public static @NotNull String replaceQuoteWith2Quotes ( @NotNull String string ) {

        return string.replace ( "\"", "\"\"" );
    }

    public static @NotNull String replaceLineBreaksWithUnixEscape ( @NotNull String string ) {

        return string
            .replace ( "\r", "" )
            .replace ( "\n", "\\n" );
    }

    /* not used
    public static @Nullable String removeLeadingLineBreak ( @NotNull String string ) {

        if ( string.isEmpty() ) return null;

        char firstChar = string.charAt ( 0 );
        switch ( firstChar ) {
            case CharConstants.UNIX_NEW_LINE:
                return StringUtilities.emptyStringToNull ( string.substring ( 1 ) );
            case CharConstants.WINDOWS_LINE_BREAK_START:
                return StringUtilities.emptyStringToNull ( string.substring ( 2 ) );
            default:
                return string;
        }
    }
    */

    public static @Nullable String emptyStringToNull ( @NotNull String string ) {

        return string.isEmpty() ? null : string;
    }
}
