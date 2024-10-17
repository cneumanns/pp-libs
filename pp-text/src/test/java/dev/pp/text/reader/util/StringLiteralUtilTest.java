package dev.pp.text.reader.util;

import dev.pp.basics.annotations.NotNull;
import dev.pp.text.inspection.InvalidTextException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class StringLiteralUtilTest {

    @Test
    void literalToString() throws Exception {

        expectResult ( "foo", "\"foo\"" );
        expectResult ( "foo", """
            \"""
            foo
            \"\"\"""" );
        expectResult ( "foo", "~|foo|~" );
        expectResult ( "foo", "foo" );
    }

    private void expectResult (
        @NotNull String expectedResult,
        @NotNull String stringLiteral ) throws IOException, InvalidTextException {

        assertEquals (
            expectedResult,
            StringLiteralUtil.literalToString (
                stringLiteral, QuotedStringLiteralUtil.DEFAULT_ESCAPE_MAP, null, false ) );
    }
}
