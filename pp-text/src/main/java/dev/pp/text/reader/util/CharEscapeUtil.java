package dev.pp.text.reader.util;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.InvalidTextException;
import dev.pp.text.location.TextLocation;
import dev.pp.text.reader.CharReader;
import dev.pp.text.reader.CharReaderImpl;
import dev.pp.text.resource.String_TextResource;
import dev.pp.text.token.TextToken;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Set;

/*
 * Escape sequences of the format \{letter} are supported.
 * Unicode escape sequences of the format \\uhhhh, u\{hhhhhh} and \\u{h hh hh hhhhhh} are also supported.
 */

public class CharEscapeUtil {

    public static final char ESCAPE_CHARACTER = '\\';
    public static final char UNICODE_ESCAPE_LETTER = 'u';
    public static final char UNICODE_ESCAPE_OPENING_BRACKET = '{';
    public static final char UNICODE_ESCAPE_CLOSING_BRACKET = '}';
    public static final int UNICODE_MAX_HEX_VALUE = 0x10FFFF;
    private static final @NotNull String ESCAPE_ERROR_ID = "INVALID_CHARACTER_ESCAPE_SEQUENCE";
    private static final @NotNull String INVALID_CHAR_ERROR_ID = "INVALID_CHARACTER";


    public static @NotNull String unescapeText (
        @NotNull String text,
        @Nullable Map<Character,Character> escapeMap,
        @Nullable Set<Character> endChars,
        @Nullable Set<Character> invalidChars,
        boolean allowUnicodeEscapes ) throws IOException, InvalidTextException {

        try ( StringReader stringReader = new StringReader ( text ) ) {
            CharReader charReader = CharReaderImpl.createAndAdvance (
                stringReader, new String_TextResource ( text ), null, null );
            return unescapeText ( charReader, escapeMap, endChars, invalidChars, allowUnicodeEscapes );
            /*
            if ( charReader.isNotAtEnd() ) {
                throw new TextErrorException (
                    "No more text allowed.",
                    ERROR_ID,
                    charReader.currentCharToken() );
            }
             */
        }
    }

    public static @NotNull String unescapeText (
        @NotNull CharReader charReader,
        @Nullable Map<Character,Character> escapeMap,
        @Nullable Set<Character> endChars,
        @Nullable Set<Character> invalidChars,
        boolean allowUnicodeEscapes ) throws IOException, InvalidTextException {

        StringBuilder result = new StringBuilder();
        unescapeTextAndAppend ( charReader, escapeMap, endChars, invalidChars, allowUnicodeEscapes, result );
        return result.toString();
    }

    public static void unescapeTextAndAppend (
        @NotNull CharReader charReader,
        @Nullable Map<Character,Character> escapeMap,
        @Nullable Set<Character> endChars,
        @Nullable Set<Character> invalidChars,
        boolean allowUnicodeEscapes,
        @NotNull Appendable appendable ) throws IOException, InvalidTextException {

        while ( charReader.isNotAtEnd() ) {
            final char currentChar = charReader.currentChar();

            if ( endChars != null && endChars.contains ( currentChar ) ) {
                return;
            }

            if ( invalidChars != null && invalidChars.contains ( currentChar ) ) {
                throw new InvalidTextException (
                    "Invalid character. Character '" + currentChar + "' is not allowed.",
                    INVALID_CHAR_ERROR_ID,
                    charReader.currentCharToken() );
            }

            if ( currentChar == ESCAPE_CHARACTER ) {
                unescapeSequenceAndAppend ( charReader, escapeMap, allowUnicodeEscapes, appendable );
            } else {
                appendable.append ( currentChar );
                /* TODO?
                if ( ! normalizeLineBreaksToLinefeed ||
                    currentChar != CharConstants.WINDOWS_LINE_BREAK_START ) {
                    appendable.append ( currentChar );
                }
                 */
                charReader.advance();
            }
        }
    }

    public static @NotNull String unescapeSequence (
        @NotNull String escapeSequence,
        @Nullable Map<Character,Character> escapeMap,
        boolean allowUnicodeEscapes ) throws IOException, InvalidTextException {

        try ( StringReader stringReader = new StringReader ( escapeSequence ) ) {
            CharReader charReader = CharReaderImpl.createAndAdvance (
                stringReader, new String_TextResource ( escapeSequence ), null, null );
            String result = unescapeSequence ( charReader, escapeMap, allowUnicodeEscapes  );
            if ( charReader.isNotAtEnd() ) {
                throw new InvalidTextException (
                    "No more text allowed.",
                    INVALID_CHAR_ERROR_ID,
                    charReader.currentCharToken() );
            }
            return result;
        }
    }

    public static @NotNull String unescapeSequence (
        @NotNull CharReader charReader,
        @Nullable Map<Character,Character> escapeMap,
        boolean allowUnicodeEscapes ) throws IOException, InvalidTextException {

        StringBuilder result = new StringBuilder();
        unescapeSequenceAndAppend ( charReader, escapeMap, allowUnicodeEscapes, result );
        return result.toString();
    }

    public static void unescapeSequenceAndAppend (
        @NotNull CharReader charReader,
        @Nullable Map<Character,Character> escapeMap,
        boolean allowUnicodeEscapes,
        @NotNull Appendable appendable ) throws IOException, InvalidTextException {

        // must be positioned at the escape character \
        if ( ! charReader.skipChar ( ESCAPE_CHARACTER ) ) {
            throw new InvalidTextException (
                "Not positioned at '" + ESCAPE_CHARACTER + "'.",
                ESCAPE_ERROR_ID,
                charReader.currentCharToken() );
        }

        if ( charReader.isAtEnd() ) {
            throw new InvalidTextException (
                "Expecting another character after the escape character '" + ESCAPE_CHARACTER +
                    "' at the end of the document.",
                "MISSING_ESCAPED_CHARACTER",
                charReader.currentCharToken () );
        }

        final char charAfterEscapeCharacter = charReader.currentChar();

        if ( charAfterEscapeCharacter == UNICODE_ESCAPE_LETTER ) {
            if ( allowUnicodeEscapes ) {
                appendUnicodeEscapeSequences ( charReader, appendable );
            } else {
                throw new InvalidTextException (
                    "Unicode escape sequences are not allowed in this context.",
                    "UNICODE_ESCAPES_INVALID",
                    charReader.currentCharToken() );
            }
            return;
        }

        @Nullable Character replacement = escapeMap == null ? null : escapeMap.get ( charAfterEscapeCharacter );
        if ( replacement != null ) {
            appendable.append ( replacement );
            charReader.advance();
        } else {
            throw new InvalidTextException (
                "Invalid character escape sequence \"" + ESCAPE_CHARACTER + charAfterEscapeCharacter + "\".",
                ESCAPE_ERROR_ID,
                charReader.currentCharToken() );
        }
    }

    private static void appendUnicodeEscapeSequences (
        @NotNull CharReader charReader,
        @NotNull Appendable appendable ) throws IOException, InvalidTextException {

        // now positioned at the u of \\uhhhh or \\u{hhhhhh}
        charReader.advance();

        if ( ! charReader.isAtChar ( UNICODE_ESCAPE_OPENING_BRACKET ) ) {
            appendSimpleUnicodeEscapeSequence ( charReader, appendable );
        } else {
            appendBracketedUnicodeEscapeSequences ( charReader, appendable );
        }
    }

    private static void appendSimpleUnicodeEscapeSequence (
        @NotNull CharReader charReader,
        @NotNull Appendable appendable ) throws IOException, InvalidTextException {

        // now positioned at the first h of \\uhhhh
        TextLocation firstHLocation = charReader.currentLocation();

        StringBuilder hexDigits = new StringBuilder();
        for ( int i = 1; i <= 4; i++ ) {
            appendOneHexDigit ( charReader, hexDigits );
        }
        appendHexDigits ( hexDigits, appendable, firstHLocation );
    }

    private static void appendBracketedUnicodeEscapeSequences (
        @NotNull CharReader charReader,
        @NotNull Appendable appendable ) throws IOException, InvalidTextException {

        // now positioned at the { of \\u{hhhhhh} or \\u{ hhhhhh hh h }
        TextLocation openingBracketLocation = charReader.currentLocation();
        charReader.advance();

        charReader.skipSpacesAndTabsAndLineBreaks ();

        while ( true ) {
            if ( charReader.isAtEnd() ) {
                throw new InvalidTextException (
                    "Missing '" + UNICODE_ESCAPE_CLOSING_BRACKET + "' to close the Unicode escape sequence.",
                    ESCAPE_ERROR_ID,
                    new TextToken ( UNICODE_ESCAPE_OPENING_BRACKET, openingBracketLocation ) );
            }
            appendOneBracketedUnicodeEscapeSequence ( charReader, appendable );
            charReader.skipSpacesAndTabsAndLineBreaks ();
            if ( charReader.skipChar ( UNICODE_ESCAPE_CLOSING_BRACKET ) ) {
                return;
            }
        }
    }

    private static void appendOneBracketedUnicodeEscapeSequence (
        @NotNull CharReader charReader,
        @NotNull Appendable appendable ) throws IOException, InvalidTextException {

        // now positioned at the first h of any h sequence in \\u{hhhhhh hh h }
        TextLocation firstHLocation = charReader.currentLocation();

        StringBuilder hexDigits = new StringBuilder();
        while ( charReader.isNotAtEnd() &&
            ! charReader.isAtChar ( UNICODE_ESCAPE_CLOSING_BRACKET ) &&
            ! charReader.isAtSpaceOrTabOrLineBreak () ) {

            if ( hexDigits.length () >= 6 ) {
                throw new InvalidTextException (
                    "Expecting not more than 6 hex digits to define a Unicode escape sequence.",
                    ESCAPE_ERROR_ID,
                    charReader.currentCharToken() );
            }

            appendOneHexDigit ( charReader, hexDigits );
        }

        if ( ! hexDigits.isEmpty() ) {
            appendHexDigits ( hexDigits, appendable, firstHLocation );
        } else {
            throw new InvalidTextException (
                "Expecting a valid sequence of hexadecimal characters (e.g. 1a0f).",
                ESCAPE_ERROR_ID,
                charReader.currentCharToken() );
        }
    }

    private static void appendOneHexDigit (
        @NotNull CharReader charReader,
        @NotNull StringBuilder hexDigits ) throws IOException, InvalidTextException {

        char currentChar = charReader.currentChar();
        if ( isHexDigit ( currentChar ) ) {
            hexDigits.append ( currentChar );
            charReader.advance();
        } else {
            throw new InvalidTextException (
                "Invalid hexadecimal character '" + currentChar + "'. Only 0..9, a..f, and A..F are allowed.",
                ESCAPE_ERROR_ID,
                charReader.currentCharToken() );
        }
    }

    private static boolean isHexDigit ( char c ) {

        return ( c >= '0' && c <= '9' )
            || ( c >= 'a' && c <= 'f' )
            || ( c >= 'A' && c <= 'F' );
    }

    private static void appendHexDigits (
        @NotNull StringBuilder hexDigits,
        @NotNull Appendable appendable,
        @NotNull TextLocation firstHLocation ) throws IOException, InvalidTextException {

        int codePoint = Integer.parseInt ( hexDigits.toString(), 16 );
        if ( codePoint <= UNICODE_MAX_HEX_VALUE ) {
            StringBuilder sb = new StringBuilder();
            sb.appendCodePoint ( codePoint );
            appendable.append ( sb );
        } else {
            throw new InvalidTextException (
                "Invalid Unicode escape sequence '" + hexDigits + "'. A Unicode escape sequence cannot be greater than 10FFF.",
                ESCAPE_ERROR_ID,
                new TextToken ( hexDigits.toString(), firstHLocation ) );
        }
    }
}
