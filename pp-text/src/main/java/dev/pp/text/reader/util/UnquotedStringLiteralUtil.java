package dev.pp.text.reader.util;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.InvalidTextException;
import dev.pp.text.reader.CharReader;
import dev.pp.text.reader.CharReaderImpl;
import dev.pp.text.resource.String_TextResource;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Set;

public class UnquotedStringLiteralUtil {

    public static final @NotNull Map<Character,Character> DEFAULT_ESCAPE_MAP =
        Map.of (
            '\\', '\\',
            's', ' ',
            't', '\t',
            'r', '\r',
            'n', '\n',
            ',', ',',
            ';', ';',
            ':', ':'
            // '"', '\"',
        );
    public static final @NotNull Set<Character> DEFAULT_END_CHARS = Set.of ( ' ', '\t', '\r', '\n', ',', ';', ':' );

    private static final @NotNull String ERROR_ID = "INVALID_UNQUOTED_STRING_LITERAL";


    public static @NotNull String literalToString (
        @NotNull String unquotedStringLiteral,
        @NotNull Map<Character,Character> escapeMap,
        @Nullable Set<Character> endChars,
        boolean ignoreTrailingText ) throws IOException, InvalidTextException {

        try ( StringReader stringReader = new StringReader ( unquotedStringLiteral ) ) {
            CharReader charReader = CharReaderImpl.createAndAdvance (
                stringReader, new String_TextResource ( unquotedStringLiteral ), null, null );
            String result = readLiteral ( charReader, escapeMap, endChars );
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
        @NotNull Map<Character,Character> escapeMap,
        @Nullable Set<Character> endChars ) throws IOException, InvalidTextException {

        StringBuilder result = new StringBuilder();
        appendLiteral ( result, charReader, escapeMap, endChars );
        return result.toString();
    }

    public static void appendLiteral (
        @NotNull Appendable appendable,
        @NotNull CharReader charReader,
        @NotNull Map<Character,Character> escapeMap,
        @Nullable Set<Character> endChars ) throws IOException, InvalidTextException {

        CharEscapeUtil.unescapeTextAndAppend (
            charReader, escapeMap, endChars, null,true, appendable );
    }
}
