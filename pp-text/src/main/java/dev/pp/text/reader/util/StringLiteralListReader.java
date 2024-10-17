package dev.pp.text.reader.util;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.InvalidTextException;
import dev.pp.text.reader.CharReader;
import dev.pp.text.reader.CharReaderImpl;
import dev.pp.text.resource.String_TextResource;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StringLiteralListReader {

    private static final @NotNull String ERROR_ID = "INVALID_STRING_LITERAL_LIST";

    public static @Nullable List<String> readLiterals (
        @NotNull String stringLiterals,
        @Nullable Character elementSeparator,
        @Nullable Set<Character> listEndChars,
        @NotNull Map<Character,Character> escapeMap,
        @Nullable Set<Character> endCharsForUnquotedStringLiteral,
        boolean ignoreTrailingText ) throws IOException, InvalidTextException {

        if ( stringLiterals.isEmpty() ) {
            return null;
        }

        try ( StringReader stringReader = new StringReader ( stringLiterals ) ) {
            CharReader charReader = CharReaderImpl.createAndAdvance (
                stringReader, new String_TextResource ( stringLiterals ), null, null );
            List<String> result = readLiterals (
                charReader, elementSeparator, listEndChars, escapeMap, endCharsForUnquotedStringLiteral );
            if ( ! ignoreTrailingText && charReader.isNotAtEnd() ) {
                throw new InvalidTextException (
                    "No more text allowed.",
                    ERROR_ID,
                    charReader.currentCharToken() );
            }
            return result;
        }
    }

    public static @Nullable List<String> readLiterals (
        @NotNull CharReader charReader,
        @Nullable Character elementSeparator,
        @Nullable Set<Character> listEndChars,
        @NotNull Map<Character,Character> escapeMap,
        @Nullable Set<Character> endCharsForUnquotedStringLiteral ) throws IOException, InvalidTextException {

        List<String> result = new ArrayList<>();
        appendStrings (
            result, charReader, elementSeparator, listEndChars, escapeMap, endCharsForUnquotedStringLiteral );
        return result.isEmpty() ? null : result;
    }

    public static void appendStrings (
        @NotNull List<String> list,
        @NotNull CharReader charReader,
        @Nullable Character elementSeparator,
        @Nullable Set<Character> listEndChars,
        @NotNull Map<Character,Character> escapeMap,
        @Nullable Set<Character> endCharsForUnquotedStringLiteral ) throws IOException, InvalidTextException {

        // TODO?
        // charReader.skipSpacesAndTabsAndLineBreaks();

        while ( ! isAtEnd ( charReader, listEndChars ) ) {

            String element = StringLiteralUtil.readLiteral ( charReader, escapeMap, endCharsForUnquotedStringLiteral );
            list.add ( element );

            if ( isAtEnd ( charReader, listEndChars ) ) {
                return;
            }

            requireElementSeparator ( charReader, elementSeparator, listEndChars );
        }
    }

    private static boolean isAtEnd (
        @NotNull CharReader charReader,
        @Nullable Set<Character> listEndChars ) throws IOException, InvalidTextException {

        if ( charReader.isAtEnd () ) {
            if ( listEndChars == null ) {
                return true;
            } else {
                throw new InvalidTextException (
                    "Missing any of the following characters to end the list: " + listEndChars,
                    ERROR_ID,
                    charReader.currentCharToken () );
            }
        } else if ( listEndChars != null && listEndChars.contains ( charReader.currentChar () ) ) {
            return true;
        } else {
            return false;
        }
    }

    private static void requireElementSeparator (
        @NotNull CharReader charReader,
        @Nullable Character elementSeparator,
        @Nullable Set<Character> listEndChars ) throws IOException, InvalidTextException {

        if ( elementSeparator == null ) {
            if ( ! charReader.skipSpacesAndTabsAndLineBreaks() ) {
                throw new InvalidTextException (
                    "Element separator (space or tab or line break) required.",
                    ERROR_ID,
                    charReader.currentCharToken() );
            };
        } else {
            charReader.skipSpacesAndTabsAndLineBreaks();
            if ( ! charReader.skipChar ( elementSeparator )
                && ! isAtEnd ( charReader, listEndChars ) ) {
                throw new InvalidTextException (
                    "Element separator '" + elementSeparator + "' required.",
                    ERROR_ID,
                    charReader.currentCharToken() );
            }
            charReader.skipSpacesAndTabsAndLineBreaks();
        }
    }
}
