package dev.pp.text.reader.util;

import dev.pp.basics.annotations.NotNull;
import dev.pp.text.inspection.InvalidTextException;
import dev.pp.text.reader.CharReader;
import dev.pp.text.reader.CharReaderImpl;
import dev.pp.text.resource.String_TextResource;
import dev.pp.text.token.TextToken;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Set;

public class QuotedStringLiteralUtil {

    public static final char DELIMITER = '\"';
    public static final @NotNull Map<Character,Character> DEFAULT_ESCAPE_MAP =
        Map.of (
            '\\', '\\',
            '"', '\"',
            't', '\t',
            'r', '\r',
            'n', '\n'
        );
    public static final @NotNull Set<Character> END_CHARS = Set.of ( DELIMITER );
    public static final @NotNull Set<Character> INVALID_CHARS = Set.of ( '\t', '\r', '\n' );


    private static final @NotNull String ERROR_ID = "INVALID_QUOTED_STRING_LITERAL";


    public static @NotNull String literalToString (
        @NotNull String quotedStringLiteral,
        @NotNull Map<Character,Character> escapeMap,
        boolean ignoreTrailingText ) throws IOException, InvalidTextException {

        try ( StringReader stringReader = new StringReader ( quotedStringLiteral ) ) {
            CharReader charReader = CharReaderImpl.createAndAdvance (
                stringReader, new String_TextResource ( quotedStringLiteral ), null, null );
            String result = readLiteral ( charReader, escapeMap );
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
        @NotNull CharReader charReader,
        @NotNull Map<Character,Character> escapeMap ) throws IOException, InvalidTextException {

        StringBuilder result = new StringBuilder();
        appendLiteral ( result, charReader, escapeMap );
        return result.toString();
    }

    public static void appendLiteral (
        @NotNull Appendable appendable,
        @NotNull CharReader charReader,
        @NotNull Map<Character,Character> escapeMap ) throws IOException, InvalidTextException {

        TextToken startToken = charReader.currentCharToken();

        if ( ! charReader.skipChar ( DELIMITER ) ) {
            throw new InvalidTextException (
                "Expecting " + DELIMITER + " to start a quoted string literal",
                ERROR_ID,
                charReader.currentCharToken() );
        }

        // Empty string
        if ( charReader.skipChar ( DELIMITER ) ) {
            return;
        }

        CharEscapeUtil.unescapeTextAndAppend (
            charReader, escapeMap, END_CHARS, INVALID_CHARS, true, appendable );

        if ( ! charReader.skipChar ( DELIMITER ) ) {
            throw new InvalidTextException (
                "Missing closing " + DELIMITER + " to end the quoted string literal.",
                ERROR_ID,
                startToken );
        }
    }
}
