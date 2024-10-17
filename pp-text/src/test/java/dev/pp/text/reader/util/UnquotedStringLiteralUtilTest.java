package dev.pp.text.reader.util;

import dev.pp.basics.annotations.NotNull;
import dev.pp.text.inspection.InvalidTextException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UnquotedStringLiteralUtilTest {

    @Test
    void literalToString() throws Exception {

        expectResult ( "foo", "foo" );
        // Escape chars
        expectResult ( " ", "\\s" );
        expectResult ( "PPfoo\r\n\t \\ bar", "\\u0050\\u{50}foo\\r\\n\\t\\s\\\\ bar" );
        // Line break
        expectResult ( "1\n2\n3\\n4", "1\\n2\\n3\\\\n4" );
        // Empty
        expectResult ( "", "" );
        assertEquals (
            "foo",
            UnquotedStringLiteralUtil.literalToString (
                "foo|", UnquotedStringLiteralUtil.DEFAULT_ESCAPE_MAP, Set.of ( '|' ), true ) );

        // Invalid

        // Trailing text
        expectException ( "foo|TRAILING_TEXT" );
        // Invalid char escape
        expectException ( "1\\23" );
    }

    private void expectResult (
        @NotNull String expectedResult,
        @NotNull String unquotedStringLiteral ) throws IOException, InvalidTextException {

        assertEquals (
            expectedResult,
            UnquotedStringLiteralUtil.literalToString (
                unquotedStringLiteral,
                UnquotedStringLiteralUtil.DEFAULT_ESCAPE_MAP, null, false ) );
    }

    private void expectException (
        @NotNull String unquotedStringLiteral ) throws IOException, InvalidTextException {

        InvalidTextException exception = assertThrows ( InvalidTextException.class,
            () -> UnquotedStringLiteralUtil.literalToString (
                unquotedStringLiteral,
                UnquotedStringLiteralUtil.DEFAULT_ESCAPE_MAP, Set.of ( '|' ), false ) );
        System.err.println ( exception.toString () );
    }
}
