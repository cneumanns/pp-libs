package dev.pp.text.reader.util;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.InvalidTextException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StringLiteralListReaderTest {

    @Test
    void readLiterals() throws Exception {

        @NotNull Map<Character,Character> escapeMap = QuotedStringLiteralUtil.DEFAULT_ESCAPE_MAP;
        @Nullable Set<Character> endCharsForUnquotedStringLiteral = UnquotedStringLiteralUtil.DEFAULT_END_CHARS;
        @NotNull Set<Character> listEndChars = Set.of ( ']' );

        String literals = "a bb ccc";
        List<String> result = StringLiteralListReader.readLiterals (
            literals, null, null,
            escapeMap, endCharsForUnquotedStringLiteral, false );
        assertEquals ( 3, result.size() );
        assertEquals ( "[a, bb, ccc]", result.toString() );

        // Four kinds of literals
        literals = """
                a "b" ~|c|~ \"""
                    line 1
                    line 2
                    \"""
                """;
        result = StringLiteralListReader.readLiterals (
            literals, null, null,
            escapeMap, endCharsForUnquotedStringLiteral, false );
        assertEquals ( 4, result.size() );
        assertEquals ( "[a, b, c, line 1\nline 2]", result.toString() );

        // Empty list
        literals = "";
        result = StringLiteralListReader.readLiterals (
            literals, ',', null,
            escapeMap, endCharsForUnquotedStringLiteral, false );
        assertNull ( result );

        // Comma Separator
        literals = "a,bb,ccc";
        result = StringLiteralListReader.readLiterals (
            literals, ',', null,
            escapeMap, endCharsForUnquotedStringLiteral, false );
        assertEquals ( 3, result.size() );
        assertEquals ( "[a, bb, ccc]", result.toString() );

        // Comma Separator
        literals = """
            a,bb, ccc ,4  ,
             5\t ,
               6
            """;
        result = StringLiteralListReader.readLiterals (
            literals, ',', null,
            escapeMap, endCharsForUnquotedStringLiteral, false );
        assertEquals ( 6, result.size() );
        assertEquals ( "[a, bb, ccc, 4, 5, 6]", result.toString() );

        // Trailing Comma Separator
        literals = "a,bb,ccc, ";
        result = StringLiteralListReader.readLiterals (
            literals, ',', null,
            escapeMap, endCharsForUnquotedStringLiteral, false );
        assertEquals ( 3, result.size() );
        assertEquals ( "[a, bb, ccc]", result.toString() );

        // List ends at ]
        literals = "a b c ] d e";
        result = StringLiteralListReader.readLiterals (
            literals, null, listEndChars,
            escapeMap, endCharsForUnquotedStringLiteral, true );
        assertEquals ( 3, result.size() );
        assertEquals ( "[a, b, c]", result.toString() );

        literals = "]";
        result = StringLiteralListReader.readLiterals (
            literals, null, listEndChars,
            escapeMap, endCharsForUnquotedStringLiteral, true );
        assertNull ( result );


        // Invalid

        // Separator missing
        final String literals_2 = """
            "a""b"
            """;
        assertThrows ( InvalidTextException.class, () -> StringLiteralListReader.readLiterals (
            literals_2, null, listEndChars,
            escapeMap, endCharsForUnquotedStringLiteral, true ) );

        // List end char missing
        final String literals_3 = "a b";
        assertThrows ( InvalidTextException.class, () -> StringLiteralListReader.readLiterals (
            literals_3, null, listEndChars,
            escapeMap, endCharsForUnquotedStringLiteral, true ) );

        // Invalid element
        final String literals_4 = "a \"b c";
        assertThrows ( InvalidTextException.class, () -> StringLiteralListReader.readLiterals (
            literals_3, null, listEndChars,
            escapeMap, endCharsForUnquotedStringLiteral, true ) );
    }
}
