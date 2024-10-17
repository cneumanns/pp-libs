package dev.pp.text.reader.util;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.InvalidTextException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CharEscapeUtilTest {

    @Test
    void unescapeText() throws Exception {

        expectTextResult ( "foo", "foo", null, null );
        expectTextResult ( "foo", "foo\"bar", null, Set.of ( '\"' ) );

        @NotNull Map<Character,Character> escapeMap = Map.of (
            '\\', '\\',
            'n', '\n',
            'r', '\r',
            '[', '[',
            'a', 'A'
        );

        expectTextResult ( "1\n2\r\n3P4", "1\\n2\\r\\n3\\u{50}4\"", escapeMap, Set.of ( '\"' ) );
    }

    @Test
    void standardCharEscapes() throws Exception {

        @NotNull Map<Character,Character> escapeMap = Map.of (
            '\\', '\\',
            'n', '\n',
            'r', '\r',
            '[', '[',
            'a', 'A'
        );

        expectSequenceResult ( "\\", "\\\\", escapeMap );
        expectSequenceResult ( "\n", "\\n", escapeMap );
        expectSequenceResult ( "\r", "\\r", escapeMap );
        expectSequenceResult ( "[", "\\[", escapeMap );
        expectSequenceResult ( "A", "\\a", escapeMap );

        // Invalid Sequences

        expectSequenceException ( "\\n", null );
        expectSequenceException ( "\\b", escapeMap );
        expectSequenceException ( "\\ab", escapeMap );
        expectSequenceException ( "\\", escapeMap );
        expectSequenceException ( "", escapeMap );
    }

    @Test
    void simpleUnicodeEscapes() throws Exception {

        expectSequenceResult ("P", "\\u0050", null );
        expectSequenceResult ("\u00aF", "\\u00aF", null );
        // expectSequenceResult ("¯", "\\u00aF", null );
/*
        assertEquals ("1H2", readText ( "1\\u00482" ) );
        assertEquals ("\nH\r\n2", readText ( "\\n\\u0048\\r\\n2" ) );
        assertEquals ("Hello", readText ( "\\u0048\\u0065ll\\u006f" ) );
*/

        // Invalid Escapes

        expectSequenceException ( "\\u005z", null ); // z is invalid
        expectSequenceException ( "\\u005", null ); // only 3 hex chars
        expectSequenceException ( "\\u", null ); // no hex chars
    }

    @Test
    void BracketedUnicodeEscapes () throws Exception {

        expectSequenceResult ("P", "\\u{50}", null );
        expectSequenceResult ("P", "\\u{0050}", null );
        expectSequenceResult ("P", "\\u{000050}", null );

        // Java surrogate pair, see https://stackoverflow.com/questions/5903008/what-is-a-surrogate-pair-in-java
        // assertEquals ( "😃", readText ( "\\u{1F600}" ) );
        expectSequenceResult ( "\uD83D\uDE00", "\\u{1F600}", null ); // smiley U+1F600

        // Multiple Characters
        expectSequenceResult ( "PPP", "\\u{50 0050 000050}", null );
        expectSequenceResult ( "PQR", """
            \\u{ 50 0051
              \t   000052 }""", null );

/*
        assertEquals ( "1H3", readText ( "1\\u{48}3" ) );
        assertEquals ( "\nH\r\n2", readText ( "\\n\\u{0048}\\r\\n2" ) );
        assertEquals ( "Hello", readText ( "\\u{48}\\u{0065}ll\\u{06f}" ) );

        // assertEquals ( "1😃3", readText ( "1\\u{1F600}3" ) );
        assertEquals ( "1\uD83D\uDE003", readText ( "1\\u{1F600}3" ) ); // smiley U+1F600
*/

        // Invalid Escapes
        expectSequenceException ( "\\u{00az}", null ); // z is invalid
        expectSequenceException ( "\\u{11FFFF}", null ); // max. value is 10FFFF
        expectSequenceException ( "\\u{1234567}", null ); // max. 6 chars
        expectSequenceException ( "\\u{@}", null );
        expectSequenceException ( "\\u{}", null ); // empty
        expectSequenceException ( "\\u{", null );
        expectSequenceException ( "\\u}", null );
        expectSequenceException ( "\\uu", null );
        expectSequenceException ( "\\", null );
        expectSequenceException ( "\\u{50, 51}", null );
    }

    private void expectTextResult (
        @NotNull String expectedResult,
        @NotNull String escapedText,
        @Nullable Map<Character, Character> escapeMap,
        @Nullable Set<Character> endChars ) throws IOException, InvalidTextException {

        assertEquals ( expectedResult, CharEscapeUtil.unescapeText (
            escapedText, escapeMap, endChars, null, true ) );
    }

    private void expectSequenceResult (
        @NotNull String expectedResult,
        @NotNull String escapeSequence,
        @Nullable Map<Character, Character> escapeMap ) throws IOException, InvalidTextException {

        assertEquals ( expectedResult, unescape ( escapeSequence, escapeMap ) );
    }


    private void expectSequenceException (
        @NotNull String escapeSequence,
        @Nullable Map<Character, Character> escapeMap ) throws IOException, InvalidTextException {

        InvalidTextException exception = assertThrows ( InvalidTextException.class,
            () -> unescape ( escapeSequence, escapeMap ) );
        System.err.println ( exception.toString () );
    }

    private String unescape (
        @NotNull String escapeSequence,
        @Nullable Map<Character, Character> escapeMap ) throws IOException, InvalidTextException {

        return CharEscapeUtil.unescapeSequence ( escapeSequence, escapeMap, true );
    }
}
