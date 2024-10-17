package dev.pp.text.reader.util;

import dev.pp.basics.annotations.NotNull;
import dev.pp.text.inspection.InvalidTextException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MultilineStringLiteralUtilTest {

    @Test
    void multilineStringLiteralToString() throws Exception {

        String literal = """
            \"""
            i = 1
            \"""
            """;
        expectResult ( "i = 1", literal );

        literal = """
            \"""
            \"""
            """;
        expectResult ( "", literal );

        // Indent
        literal = """
                        \"""
                    i = 1
                        j = 2

                \"""
            """.trim();
        String expected = """
                i = 1
                    j = 2
            """;
        // remove last line break
        // expected = expected.substring ( 0, expected.length() - 1 );
        expectResult ( expected, literal );

        // Empty lines are ignored
        literal = """
            \"""
                line 1

                line 3

            \"""
            """;
        expectResult ( """
                line 1

                line 3
            """, literal );

        // Five " to allow embedding """
        literal = """
            \"\"\"""
            \"\"\"i = 1\"\"\"
            \"\"\"""
            """;
        expectResult ( "\"\"\"i = 1\"\"\"", literal );

        // Escapes disabled ( """, raw text)
        literal = """
            \"""
            foo\\s\\\\bar\\u0050
            \"""
            """;
        expectResult ( "foo\\s\\\\bar\\u0050", literal );

        // Escapes enabled ("""e)
        literal = """
            \"""e
            foo \\\\bar\\u0050\\u{50}
            \"""
            """;
        expectResult ( "foo \\barPP", literal );

        // Invalid

        // No text allowed after starting """
        literal = """
            \"""q
            i = 1
            \"""
            """;
        expectException ( literal );

        // Starts with 3 ", ends with 4 "
        literal = """
            \"""
            i = 1
            \"\"""
            """;
        expectException ( literal );

        // Indent of lines can't be smaller than indent of end delimiter line
        literal = """
            \"""
            i = 1
                \"""
            """;
        expectException ( literal );
    }


    private void expectResult (
        @NotNull String expectedResult,
        @NotNull String multilineStringLiteral ) throws IOException, InvalidTextException {

        assertEquals (
            expectedResult,
            MultilineStringLiteralUtil.literalToString (
                multilineStringLiteral, MultilineStringLiteralUtil.DEFAULT_ESCAPE_MAP, true ) );
    }

    private void expectException (
        @NotNull String multilineStringLiteral ) throws IOException, InvalidTextException {

        InvalidTextException exception = assertThrows ( InvalidTextException.class,
            () -> MultilineStringLiteralUtil.literalToString (
                multilineStringLiteral, MultilineStringLiteralUtil.DEFAULT_ESCAPE_MAP, true ) );
        System.err.println ( exception.toString () );
    }

/*
    @Test
    void testReadBlockWithDelimiter() throws Exception {

        String code = """
            [code
               ~~~
               i = 1;
               ~~~"
            ]""";
        String expectedResult = "i = 1;";
        String result = readCodeNode ( code );
        assertEquals ( expectedResult, result );

        // Unix new lines are used, even on Windows
        code = """
            [code
               ~~~
                  i = 1;
                     j = 2;
               ~~~~~~
            ]""";
        // /*
        expectedResult = String.join ( StringConstants.UNIX_NEW_LINE,
            "   i = 1;",
            "      j = 2;" );
        //
    expectedResult = """
                 i = 1;
                    j = 2;
              """;
    expectedResult = StringLineBreakUtil.removeOptionalLineBreakAtEnd ( expectedResult );
    result = readCodeNode ( code );
    assertEquals ( expectedResult, result );
    // assertEquals ( StringLineBreakUtil.replaceLineBreaksWithEscapeCharacters ( expectedResult ),
    //    StringLineBreakUtil.replaceLineBreaksWithEscapeCharacters ( result ) );

    code = """
            [code
            ====
               i = 1;

            j = 2;
            ========]""";
    expectedResult = """
               i = 1;

            j = 2;""";
    result = readCodeNode ( code );
    assertEquals ( expectedResult, result );

    code = """
            [code ====
               i = 1;

            j = 2;

            ========]""";
    expectedResult = """
               i = 1;

            j = 2;
            """;
    result = readCodeNode ( code );
    assertEquals ( expectedResult, result );

    code = """
            [code

                ====
                i = 1;

                  j=2;
                ====

            ]""";
    expectedResult = """
            i = 1;

              j=2;""";
    result = readCodeNode ( code );
    assertEquals ( expectedResult, result );

    code = """
            [code
               ====
               ========]""";
    assertNull ( readCodeNode ( code ) );

    String code_2 = """
            [code
               ~~~
               i = 1;
               ~~
            ]""";
    assertThrows ( MalformedPdmlException.class, () -> readCodeNode ( code_2 ) );

        // /*
        String code_3 = """
            [code
               ~~
               i = 1;
               ~~~
            ]""";
        assertThrows ( MalformedPdmlException.class, () -> readCodeNode ( code_3 ) );

        String code_4 = """
            [code
               +++
               i = 1;
               +++
            ]""";
        assertThrows ( MalformedPdmlException.class, () -> readCodeNode ( code_4 ) );
        //
}

private static String readCodeNode ( String code ) throws IOException, PdmlException {

    PdmlReader pdmlReader = new PdmlReader ( new StringReader ( code ) );
    pdmlReader.skipSpacesAndTabs();
    pdmlReader.skipChar ( '[' );
    TextToken nameToken = pdmlReader.readNodeNameToken ();
    assert nameToken != null;
    NodeName nodeName = NodeName.create ( nameToken );
    pdmlReader.readNodeNameSeparator ();

    PdmlTextBlockType textBlockType = new PdmlTextBlockType ();
    return textBlockType.readPdmlObjectNative ( pdmlReader, nodeName );
}

 */
}
