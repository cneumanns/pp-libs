package dev.pp.text.utilities.text;

import dev.pp.basics.utilities.string.StringConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextIndentTest {

    @Test
    public void testRemoveIndentAtStart() {

        assertEquals ( "foo", TextIndent.removeLeadingIndent ( "foo" ) );
        assertEquals ( "foo", TextIndent.removeLeadingIndent ( " foo" ) );
        assertEquals ( "foo", TextIndent.removeLeadingIndent ( " \t foo" ) );
        assertEquals ( "a", TextIndent.removeLeadingIndent ( " a" ) );
        assertEquals ( "a ", TextIndent.removeLeadingIndent ( " a " ) );
        assertNull ( TextIndent.removeLeadingIndent ( " " ) );
        assertNull ( TextIndent.removeLeadingIndent ( " \t " ) );
    }

    @Test
    public void testGetSmallestNonIndentIndex() {

        String lines = String.join ( StringConstants.OS_LINE_BREAK,
            "   line 1",
            " line 2",
            "   line 3" );
        assertEquals ( 1, TextIndent.getSmallestNonIndentIndex ( lines, false ) );


        lines = String.join ( StringConstants.OS_LINE_BREAK,
            "   line 1",
            "  ",
            "   line 3" );
        assertEquals ( 2, TextIndent.getSmallestNonIndentIndex ( lines, false ) );
    }

    @Test
    public void testRemoveSmallestIndent() {

        String lines = String.join ( StringConstants.OS_LINE_BREAK,
            "   line 1",
            " line 2",
            "   line 3" );
        String expected = String.join ( StringConstants.OS_LINE_BREAK,
            "  line 1",
            "line 2",
            "  line 3" );
        assertEquals ( expected, TextIndent.removeSmallestIndent ( lines, false ) );

        lines = String.join ( StringConstants.OS_LINE_BREAK,
            " line 1",
            "line 2",
            "  line 3" );
        expected = String.join ( StringConstants.OS_LINE_BREAK,
            " line 1",
            "line 2",
            "  line 3" );
        assertEquals ( expected, TextIndent.removeSmallestIndent ( lines, false ) );

        lines = String.join ( StringConstants.OS_LINE_BREAK,
            "   line 1",
            "  ",
            "    line 3" );
        expected = String.join ( StringConstants.OS_LINE_BREAK,
            " line 1",
            "",
            "  line 3" );
        assertEquals ( expected, TextIndent.removeSmallestIndent ( lines, false ) );

        // ignore empty lines

        lines = String.join ( StringConstants.OS_LINE_BREAK,
            "  line 1",
            "",
            "    line 3" );
        expected = String.join ( StringConstants.OS_LINE_BREAK,
            "line 1",
            "",
            "  line 3" );
        assertEquals ( expected, TextIndent.removeSmallestIndent ( lines, true ) );

        expected = String.join ( StringConstants.OS_LINE_BREAK,
            "  line 1",
            "",
            "    line 3" );
        assertEquals ( expected, TextIndent.removeSmallestIndent ( lines, false ) );
    }
}
