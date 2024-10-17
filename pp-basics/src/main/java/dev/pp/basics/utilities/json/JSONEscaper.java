package dev.pp.basics.utilities.json;

import dev.pp.basics.annotations.NotNull;

public class JSONEscaper {

    public static @NotNull String escapeString ( @NotNull String unescapedString ) {

        StringBuilder r = new StringBuilder();

        for ( int i = 0; i < unescapedString.length(); i++ ){
            char currentChar = unescapedString.charAt ( i );

            switch ( currentChar ) {
                case '"' -> r.append ( "\\\"" );
                case '\\' -> r.append ( "\\\\" );
                case '/' -> r.append ( "\\/" ); // see https://stackoverflow.com/questions/1580647/json-why-are-forward-slashes-escaped
                // case '/' -> sb.append ( "\\/" );
                case '\b' -> r.append ( "\\b" );
                case '\f' -> r.append ( "\\f" );
                case '\n' -> r.append ( "\\n" );
                case '\r' -> r.append ( "\\r" );
                case '\t' -> r.append ( "\\t" );
                default -> {
                    // Control characters
                    // Reference: http://www.unicode.org/versions/Unicode5.1.0/
                    if ( ( currentChar <= '\u001F' ) ||
                        ( currentChar >= '\u007F' && currentChar <= '\u009F' ) ||
                        ( currentChar >= '\u2000' && currentChar <= '\u20FF' ) ) {

                        r.append ( String.format ( "\\u%04x", (int) currentChar ) );
                    } else {
                        r.append ( currentChar );
                    }
                }
            }
        }

        return r.toString();
    }
}
