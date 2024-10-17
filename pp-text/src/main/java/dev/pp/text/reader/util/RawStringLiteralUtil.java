package dev.pp.text.reader.util;

import dev.pp.basics.annotations.NotNull;
import dev.pp.text.inspection.InvalidTextException;
import dev.pp.text.reader.CharReader;
import dev.pp.text.reader.CharReaderImpl;
import dev.pp.text.resource.String_TextResource;

import java.io.IOException;
import java.io.StringReader;

public class RawStringLiteralUtil {

    public static final char RAW_STRING_OUTER_DELIMITER = '~';
    public static final char RAW_STRING_INNER_DELIMITER = '|';

    private static final @NotNull String ERROR_ID = "INVALID_RAW_STRING_LITERAL";

    public static @NotNull String literalToString (
        @NotNull String rawStringLiteral,
        boolean ignoreTrailingText ) throws IOException, InvalidTextException {

        try ( StringReader stringReader = new StringReader ( rawStringLiteral ) ) {
            CharReader charReader = CharReaderImpl.createAndAdvance (
                stringReader, new String_TextResource ( rawStringLiteral ), null, null );
            String result = readLiteral ( charReader );
            if ( ! ignoreTrailingText && charReader.isNotAtEnd() ) {
                throw new InvalidTextException (
                    "No more text allowed.",
                    ERROR_ID,
                    charReader.currentCharToken() );
            }
            return result;
        }
    }

    public static @NotNull String readLiteral (
        @NotNull CharReader charReader ) throws IOException, InvalidTextException {

        StringBuilder result = new StringBuilder();
        appendLiteral ( result, charReader );
        return result.toString();
    }

    public static void appendLiteral (
        @NotNull Appendable appendable,
        @NotNull CharReader charReader ) throws IOException, InvalidTextException {

        String outerDelimiter = charReader.readWhileAtChar ( RAW_STRING_OUTER_DELIMITER );
        if ( outerDelimiter == null ) {
            throw new InvalidTextException (
                "Expecting " + RAW_STRING_OUTER_DELIMITER + " to start a raw string literal",
                ERROR_ID,
                charReader.currentCharToken() );
        }

        if ( ! charReader.skipChar ( RAW_STRING_INNER_DELIMITER ) ) {
            throw new InvalidTextException (
                "Expecting " + RAW_STRING_INNER_DELIMITER + " to delimit the raw string literal",
                ERROR_ID,
                charReader.currentCharToken() );
        }

        String endDelimiter = RAW_STRING_INNER_DELIMITER + outerDelimiter;

        while ( true ) {

            if ( charReader.isAtEnd() ) {
                throw new InvalidTextException (
                    "Expecting " + endDelimiter + " to end the raw string literal",
                    ERROR_ID,
                    charReader.currentCharToken () );
            }

            if ( charReader.skipString ( endDelimiter ) ) {
                return;
            } else {
                appendable.append ( charReader.currentChar() );
                /* TODO?
                char currentChar = charReader.currentChar();
                // normalize all line breaks to \n
                if ( currentChar != CharConstants.WINDOWS_LINE_BREAK_START ) {
                    appendable.append ( currentChar );
                }
                 */
                charReader.advance();
            }
        }
    }
}
