package dev.pp.text.utilities.text;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.string.StringConstants;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextLinesTest {

    @Test
    public void textToLines() {

        @NotNull List<String> lines = TextLines.textToLines ( "line 1\nline 2\r\nline 3" );
        assertEquals ( 3, lines.size() );
        assertEquals ( "line 1", lines.get(0) );
        assertEquals ( "line 2", lines.get(1) );
        assertEquals ( "line 3", lines.get(2) );

        lines = TextLines.textToLines ( "line 1\n\nline 3\n" );
        assertEquals ( 4, lines.size() );
        assertEquals ( "line 1", lines.get(0) );
        assertEquals ( "", lines.get(1) );
        assertEquals ( "line 3", lines.get(2) );
        assertEquals ( "", lines.get(3) );

        lines = TextLines.textToLines ( "" );
        assertEquals ( 1, lines.size() );
        assertEquals ( "", lines.get(0) );
    }

    @Test
    public void getNthLine() throws IOException {

        String text = "line 1\nline 2\r\nline 3";
        assertEquals ( "line 1", TextLines.getNthLine ( text, 1 ) );
        assertEquals ( "line 2", TextLines.getNthLine ( text, 2 ) );
        assertEquals ( "line 3", TextLines.getNthLine ( text, 3 ) );

        text = "line 1\n\nline 3";
        assertEquals ( "line 1", TextLines.getNthLine ( text, 1 ) );
        assertEquals ( "", TextLines.getNthLine ( text, 2 ) );
        assertEquals ( "line 3", TextLines.getNthLine ( text, 3 ) );

        text = "line 1\n ";
        assertEquals ( "line 1", TextLines.getNthLine ( text, 1 ) );
        assertEquals ( " ", TextLines.getNthLine ( text, 2 ) );

        text = "line 1\n";
        assertEquals ( "line 1", TextLines.getNthLine ( text, 1 ) );
        assertEquals ( "", TextLines.getNthLine ( text, 2 ) );

        // assertThrows ( AssertionError.class, () -> TextLines.getNthLine ( "1\n2", 3 ) );
        assertThrows ( IllegalArgumentException.class, () -> TextLines.getNthLine ( "", 2 ) );
        assertThrows ( IllegalArgumentException.class, () -> TextLines.getNthLine ( "1\n2", 4 ) );
    }

    @Test
    public void testTextToTextWithMaxLineLength() {

        // without new line in input

        assertEquals ( "123 56 89", TextLines.textToTextWithMaxLineLength ( "123 56 89", 10 ) );
        assertEquals ( "123 56 89", TextLines.textToTextWithMaxLineLength ( "123 56 89", 9 ) );

        String expected = String.join ( StringConstants.OS_LINE_BREAK,
            "123 56 ",
            "89" );
        assertEquals ( expected, TextLines.textToTextWithMaxLineLength ( "123 56 89", 8 ) );
        assertEquals ( expected, TextLines.textToTextWithMaxLineLength ( "123 56 89", 7 ) );

        expected = String.join ( StringConstants.OS_LINE_BREAK,
            "123 56",
            " 89" );
        assertEquals ( expected, TextLines.textToTextWithMaxLineLength ( "123 56 89", 6 ) );

        expected = String.join ( StringConstants.OS_LINE_BREAK,
            "123 ",
            "56 89" );
        assertEquals ( expected, TextLines.textToTextWithMaxLineLength ( "123 56 89", 5 ) );
        expected = String.join ( StringConstants.OS_LINE_BREAK,
            "123 ",
            "56 ",
            "89" );
        assertEquals ( expected, TextLines.textToTextWithMaxLineLength ( "123 56 89", 4 ) );

        expected = String.join ( StringConstants.OS_LINE_BREAK,
            "123",
            " 56",
            " 89" );
        assertEquals ( expected, TextLines.textToTextWithMaxLineLength ( "123 56 89", 3 ) );

        expected = String.join ( StringConstants.OS_LINE_BREAK,
            "12",
            "3 ",
            "56",
            " 8",
            "9" );
        assertEquals ( expected, TextLines.textToTextWithMaxLineLength ( "123 56 89", 2 ) );

        expected = String.join ( StringConstants.OS_LINE_BREAK,
            "1",
            "2",
            "3",
            " ",
            "5",
            "6",
            " ",
            "8",
            "9" );
        assertEquals ( expected, TextLines.textToTextWithMaxLineLength ( "123 56 89", 1 ) );

        // with new line in input

        String input = String.join ( StringConstants.OS_LINE_BREAK,
            "123 56 89",
            "line 2",
            "line3",
            "line44444",
            "A long line (too long)" );
        expected = String.join ( StringConstants.OS_LINE_BREAK,
            "123 56",
            " 89",
            "line 2",
            "line3",
            "line44",
            "444",
            "A long",
            " line ",
            "(too ",
            "long)" );
        assertEquals ( expected, TextLines.textToTextWithMaxLineLength ( input, 6 ) );
    }
}
