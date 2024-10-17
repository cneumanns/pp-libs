package dev.pp.text.reader.util;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.character.CharConstants;
import dev.pp.basics.utilities.string.StringBuilderUtils;
import dev.pp.basics.utilities.string.StringLineBreakUtil;
import dev.pp.text.inspection.InvalidTextException;
import dev.pp.text.location.TextLocation;
import dev.pp.text.reader.CharReader;
import dev.pp.text.reader.CharReaderImpl;
import dev.pp.text.resource.String_TextResource;
import dev.pp.text.token.TextToken;
import dev.pp.text.utilities.text.TextIndent;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

public class MultilineStringLiteralUtil {

    public static final char DELIMITER_CHAR = '"';
    public static final int MIN_DELIMITER_CHARS = 3;
    public static final @NotNull String MIN_DELIMITER =
        String.valueOf ( DELIMITER_CHAR ).repeat ( MIN_DELIMITER_CHARS );

    public static final @NotNull Map<Character,Character> DEFAULT_ESCAPE_MAP =
        Map.of (
            '\\', '\\',
            't', '\t',
            'r', '\r',
            'n', '\n'
        );
    public static final char ALLOW_ESCAPE_SEQUENCES_FLAG = 'e';
    private static final @NotNull String ERROR_ID = "INVALID_MULTILINE_STRING_LITERAL";

    public static @NotNull String literalToString (
        @NotNull String multilineStringLiteral,
        @Nullable Map<Character,Character> escapeMap,
        boolean ignoreTrailingText ) throws IOException, InvalidTextException {

        try ( StringReader stringReader = new StringReader ( multilineStringLiteral ) ) {
            CharReader charReader = CharReaderImpl.createAndAdvance (
                stringReader, new String_TextResource ( multilineStringLiteral ), null, null );
            String result = readLiteral ( charReader, escapeMap );
            if ( ! ignoreTrailingText && charReader.isNotAtEnd() ) {
                throw new InvalidTextException (
                    "No more text allowed.",
                    ERROR_ID,
                    charReader.currentCharToken() );
            }
            return result;
        }
    }

    public static @NotNull String readLiteral (
        @NotNull CharReader charReader,
        @Nullable Map<Character,Character> escapeMap ) throws IOException, InvalidTextException {

        StringBuilder result = new StringBuilder();
        appendLiteral ( result, charReader, escapeMap );
        return result.toString();
    }

    public static void appendLiteral (
        @NotNull Appendable appendable,
        @NotNull CharReader charReader,
        @Nullable Map<Character, Character> escapeMap ) throws IOException, InvalidTextException {

        @Nullable TextLocation startLocation = charReader.currentLocation();

        // read """
        String delimiter = charReader.readWhileAtChar ( DELIMITER_CHAR );
        if ( delimiter == null || delimiter.length() < MIN_DELIMITER_CHARS ) {
            throw new InvalidTextException (
                "Expecting " + MIN_DELIMITER + " to start a multiline string literal",
                ERROR_ID,
                charReader.currentCharToken() );
        }

        @Nullable TextToken startToken = new TextToken ( delimiter, startLocation );

        // e.g. """e
        boolean allowEscapeSequences = charReader.skipChar ( ALLOW_ESCAPE_SEQUENCES_FLAG );

        charReader.skipSpacesAndTabs();
        if ( ! charReader.skipLineBreak() ) {
            throw new InvalidTextException (
                "Expecting end of line, but invalid character '" + charReader.currentChar() + "' encountered.",
                ERROR_ID,
                charReader.currentCharToken() );
        }

        // Now positioned right after the start delimiter line (= start of content)
        StringBuilder contentBuilder = new StringBuilder();
        Integer firstNonIndentIndex = null;
        while ( true ) {

            if ( charReader.isAtEnd() ) throw new InvalidTextException (
                "Missing " + delimiter + " on a separate subsequent line to end the multiline string literal.",
                ERROR_ID,
                startToken );

            String indent = charReader.readSpacesAndTabs();
            if ( charReader.skipString ( delimiter ) ) {
                if ( indent != null ) {
                    firstNonIndentIndex = indent.length();
                }
                break;
            }

            StringBuilderUtils.appendIfNotNull ( contentBuilder, indent );
            StringBuilderUtils.appendIfNotNull (
                contentBuilder, charReader.readRemainingLine ( false ) );
            if ( charReader.skipLineBreak() ) {
                // normalize line breaks to \n (\r\n -> \n)
                contentBuilder.append ( CharConstants.UNIX_NEW_LINE );
            }
        }

        // Now positioned right after the end delimiter

        // end delimiter can be longer than start delimiter
        // charReader.readWhileAtChar ( DELIMITER_CHAR );
        if ( charReader.isAtChar ( DELIMITER_CHAR ) ) {
            throw new InvalidTextException (
                "End delimiter must match start delimiter " + delimiter + ". More than " + delimiter.length() + " characters are not allowed.",
                ERROR_ID,
                charReader.currentCharToken() );
        }

        if ( contentBuilder.isEmpty() ) {
            // TODO? throw, because empty string not allowed? Maybe add param.
            return;
        }

        @Nullable String result = contentBuilder.toString();
        if ( firstNonIndentIndex != null ) {

            int smallestNonIndentIndex = TextIndent.getSmallestNonIndentIndex (
                result, true );
            if ( smallestNonIndentIndex < firstNonIndentIndex ) {
                throw new InvalidTextException (
                    "The indent of any line cannot be smaller than the indent of the end delimiter line.",
                    ERROR_ID,
                    startToken );
            }

            result = TextIndent.removeIndent ( result, firstNonIndentIndex );
        }
        if ( result != null ) {
            // Remove line break before end delimiter
            result = StringLineBreakUtil.removeOptionalLineBreakAtEnd ( result );
        }
        if ( result == null ) {
            // TODO? throw, because empty string not allowed? Maybe add param.
            return;
        }
        if ( allowEscapeSequences ) {
            result = CharEscapeUtil.unescapeText ( result, escapeMap, null, null, true );
        }
        appendable.append ( result );
    }
}
