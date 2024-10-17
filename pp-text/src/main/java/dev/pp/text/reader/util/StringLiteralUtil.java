package dev.pp.text.reader.util;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.InvalidTextException;
import dev.pp.text.reader.CharReader;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class StringLiteralUtil {

    public static @NotNull String literalToString (
        @NotNull String stringLiteral,
        @NotNull Map<Character,Character> escapeMap,
        @Nullable Set<Character> endCharsForUnquotedStringLiteral,
        boolean ignoreTrailingText ) throws IOException, InvalidTextException {

/*
        try ( StringReader stringReader = new StringReader ( stringLiteral ) ) {
            CharReader charReader = CharReaderImpl.createAndAdvance (
                stringReader, new String_TextResource ( stringLiteral ), null, null );
            String result = readLiteral ( charReader, escapeMap, endCharsForUnquotedStringLiteral );
            if ( ! ignoreTrailingText && charReader.isNotAtEnd() ) {
                throw new InvalidTextException (
                    "No more text allowed.",
                    "INVALID_STRING_LITERAL",
                    charReader.currentCharToken() );
            }
            return result;
        }
 */
        if ( stringLiteral.isEmpty() ) {
            return stringLiteral;
        }

        if ( stringLiteral.startsWith ( MultilineStringLiteralUtil.MIN_DELIMITER ) ) {
            return MultilineStringLiteralUtil.literalToString (
                stringLiteral, escapeMap, ignoreTrailingText );

        } else if ( stringLiteral.charAt ( 0 ) == QuotedStringLiteralUtil.DELIMITER ) {
            return QuotedStringLiteralUtil.literalToString (
                stringLiteral, escapeMap, ignoreTrailingText );

        } else if ( stringLiteral.charAt ( 0 ) == RawStringLiteralUtil.RAW_STRING_OUTER_DELIMITER ) {
            return RawStringLiteralUtil.literalToString (
                stringLiteral, ignoreTrailingText );

        } else {
            return UnquotedStringLiteralUtil.literalToString (
                stringLiteral, escapeMap, endCharsForUnquotedStringLiteral, ignoreTrailingText );
        }
    }

    public static @NotNull String readLiteral (
        @NotNull CharReader charReader,
        @NotNull Map<Character,Character> escapeMap,
        @Nullable Set<Character> endCharsForUnquotedStringLiteral ) throws IOException, InvalidTextException {

        StringBuilder result = new StringBuilder();
        appendLiteral ( result, charReader, escapeMap, endCharsForUnquotedStringLiteral );
        return result.toString();
    }

    public static void appendLiteral (
        @NotNull Appendable appendable,
        @NotNull CharReader charReader,
        @NotNull Map<Character,Character> escapeMap,
        @Nullable Set<Character> endCharsForUnquotedStringLiteral ) throws IOException, InvalidTextException {

        if ( charReader.isAtString ( MultilineStringLiteralUtil.MIN_DELIMITER ) ) {
            MultilineStringLiteralUtil.appendLiteral ( appendable, charReader, escapeMap );

        } else if ( charReader.isAtChar ( QuotedStringLiteralUtil.DELIMITER) ) {
            QuotedStringLiteralUtil.appendLiteral ( appendable,charReader, escapeMap );

        } else if ( charReader.isAtChar ( RawStringLiteralUtil.RAW_STRING_OUTER_DELIMITER ) ) {
            RawStringLiteralUtil.appendLiteral ( appendable, charReader );

        } else {
            UnquotedStringLiteralUtil.appendLiteral ( appendable, charReader, escapeMap, endCharsForUnquotedStringLiteral );
        }
    }
}
