package dev.pp.text.reader.util;

import dev.pp.basics.annotations.NotNull;
import dev.pp.text.inspection.InvalidTextException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RawStringLiteralUtilTest {

    @Test
    void literalToString() throws Exception {

        expectResult ( "foo", "~|foo|~" );
        expectResult ( "foo\\r\\n\\t\\s\\ \\u0050bar", "~|foo\\r\\n\\t\\s\\ \\u0050bar|~" );
        expectResult ( """
            foo
            bar""", """
            ~|foo
            bar|~""" );
        expectResult ( "", "~||~" );
        expectResult ( "|", "~|||~" );
        expectResult ( "~||~", "~~|~||~|~~" );
        assertEquals (
            "foo",
            RawStringLiteralUtil.literalToString ( "~|foo|~TRAILING_TEXT", true ) );

        expectException ( "|foo|" );
        expectException ( "~foo~" );
        expectException ( "~|foo" );
        expectException ( "~|foo|" );
        expectException ( "~~|foo|~" );
        expectException ( "~|foo|~TRAILING_TEXT" );
        expectException ( "~|foo|~T" );
    }

    private void expectResult (
        @NotNull String expectedResult,
        @NotNull String rawStringLiteral ) throws IOException, InvalidTextException {

        assertEquals (
            expectedResult,
            RawStringLiteralUtil.literalToString ( rawStringLiteral, false ) );
    }

    private void expectException (
        @NotNull String rawStringLiteral ) throws IOException, InvalidTextException {

        InvalidTextException exception = assertThrows ( InvalidTextException.class,
            () -> RawStringLiteralUtil.literalToString ( rawStringLiteral, false ) );
        System.err.println ( exception.toString () );
    }
}
