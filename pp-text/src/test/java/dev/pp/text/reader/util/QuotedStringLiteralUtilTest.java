package dev.pp.text.reader.util;

import dev.pp.basics.annotations.NotNull;
import dev.pp.text.inspection.InvalidTextException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class QuotedStringLiteralUtilTest {

    @Test
    void literalToString() throws Exception {

        expectResult ( "foo", """
            "foo"
            """ );
        // Escape chars
        expectResult ( "\"", """
            "\\""
            """ );
        expectResult ( "PPfoo\"\r\n\t \\ bar", """
            "\\u{50}\\u0050foo\\"\\r\\n\\t \\\\ bar"
            """ );
        // Empty
        expectResult ( "", """
            ""
            """ );

        // Invalid
        // Missing end quote
        expectException ( "\"foo" );
        // Missing start quote
        expectException ( "foo\"" );
        // Trailing text
        expectException ( "\"foo\"TRAILING_TEXT" );
        // Invalid char escape
        expectException ( "\"1\\23\"" );
        // Invalid char \n
        expectException ( "\"1\n2\"" );
    }

    private void expectResult (
        @NotNull String expectedResult,
        @NotNull String quotedStringLiteral ) throws IOException, InvalidTextException {

        assertEquals (
            expectedResult,
            QuotedStringLiteralUtil.literalToString (
                quotedStringLiteral, QuotedStringLiteralUtil.DEFAULT_ESCAPE_MAP, true ) );
    }

    private void expectException (
        @NotNull String quotedStringLiteral ) throws IOException, InvalidTextException {

        InvalidTextException exception = assertThrows ( InvalidTextException.class,
            () -> QuotedStringLiteralUtil.literalToString (
                quotedStringLiteral, QuotedStringLiteralUtil.DEFAULT_ESCAPE_MAP, false ) );
        System.err.println ( exception.toString () );
    }
}
