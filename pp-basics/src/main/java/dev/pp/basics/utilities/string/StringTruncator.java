package dev.pp.basics.utilities.string;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

public class StringTruncator {

    public static final int DEFAULT_TRUNCATE_WIDTH = 50;

    public static @NotNull String truncate ( @NotNull String string, int maxLength ) {
        if ( maxLength < 1 ) throw new IllegalArgumentException ( "maxLength must be >= 1" );

        if ( string.length() <= maxLength ) {
            return string;
        } else {
            return ( string.substring ( 0, maxLength ) );
        }
    }

    public static @NotNull String truncateWithEllipses ( @NotNull String string ) {

        return truncateWithEllipses ( string, DEFAULT_TRUNCATE_WIDTH );
    }

    public static @NotNull String truncateWithEllipses ( @NotNull String string, int maxLength ) {
        assert maxLength >= 5;

        if ( string.length() <= maxLength ) {
            return string;
        } else {
            return ( string.substring ( 0, maxLength - 4 ) + " ..." );
        }
    }

    public static @NotNull String rightPadOrTruncate ( String string, int length ) {

        if ( string.length() > length ) {
            return truncate ( string, length );
        } else if ( string.length() == length ) {
            return string;
        } else {
            return truncate ( string + " ".repeat ( length ), length );
        }
    }

    public static @Nullable String removeOptionalLineBreakAtEnd ( @NotNull String string ) {

        // TODO don't use StringBuilder
        StringBuilder sb = new StringBuilder ( string );
        StringBuilderUtils.removeOptionalLineBreakAtEnd ( sb );
        return sb.isEmpty() ? null : sb.toString ();
    }
}
