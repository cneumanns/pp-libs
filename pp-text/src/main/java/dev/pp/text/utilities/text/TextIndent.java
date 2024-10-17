package dev.pp.text.utilities.text;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.character.CharChecks;
import dev.pp.basics.utilities.string.StringFinder;
import dev.pp.text.reader.CharReader;
import dev.pp.text.reader.CharReaderImpl;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

public class TextIndent {

    public static @Nullable String removeSmallestIndent ( @NotNull String lines, boolean ignoreEmptyLines ) {

        // Can't be used because the line breaks must be preserved
        // return TextLines.linesToText ( removeSmallestIndent ( TextLines.textToLines ( lines ), ignoreEmptyLines ) );

        int smallestNonIndentIndex = getSmallestNonIndentIndex ( lines, ignoreEmptyLines );
        return removeIndent ( lines, smallestNonIndentIndex );
    }

    public static @Nullable String removeIndent ( @NotNull String lines, int smallestNonIndentIndex ) {

        StringBuilder sb = new StringBuilder();
        try ( Reader reader = new StringReader ( lines ) ) {
            CharReader charReader = CharReaderImpl.createAndAdvance ( reader, null, null, null );
            while ( charReader.isNotAtEnd() ) {
                String line = charReader.readRemainingLine ( true );
                assert line != null;
                if ( line.length() > smallestNonIndentIndex ) {
                    line = removeLeadingIndent ( line, smallestNonIndentIndex );
                }
                sb.append ( line );
            }
        } catch ( IOException e ) {
            throw new RuntimeException ( e );
        }

        return sb.isEmpty() ? null : sb.toString();
    }

    public static @NotNull List<String> removeSmallestIndent ( @NotNull List<String> lines, boolean ignoreEmptyLines ) {

        int smallestNonIndentIndex = getSmallestNonIndentIndex ( lines, ignoreEmptyLines );

        if ( smallestNonIndentIndex > 0 ) {
            return lines.stream()
                .map ( line -> {
                    if ( line.length() > smallestNonIndentIndex ) {
                        return removeLeadingIndent ( line, smallestNonIndentIndex );
                    } else {
                        return "";
                    }
                })
                .collect ( Collectors.toList() );
        } else {
            return lines;
        }
    }

    public static int getSmallestNonIndentIndex ( @NotNull String lines, boolean ignoreEmptyLines ) {

        return getSmallestNonIndentIndex ( TextLines.textToLines ( lines ), ignoreEmptyLines );
    }

    public static int getSmallestNonIndentIndex ( @NotNull List<String> lines, boolean ignoreEmptyLines ) {

        int result = Integer.MAX_VALUE;
        for ( String line : lines ) {
            if ( line == null || line.isEmpty() ) {
                if ( ignoreEmptyLines ) {
                    continue;
                } else {
                    return 0;
                }
            }
            int index = leadingNonIndentIndex ( line );
            if ( index == 0 ) return 0;
            if ( index == -1 ) index = line.length();
            if ( index < result ) result = index;
        }
        return result;
    }

    public static @Nullable String removeLeadingIndent ( @NotNull String string ) {

        return removeLeadingIndent ( string, leadingNonIndentIndex ( string ) );
    }

    private static @Nullable String removeLeadingIndent (
        @NotNull String string,
        int nonIndentIndex ) {

        assert string.length() > nonIndentIndex;
        // assert nonIndentIndex >= 0;

        if ( nonIndentIndex == -1 ) {
            return null;
        } else if ( nonIndentIndex == 0 ) {
            return string;
        } else {
            return string.substring ( nonIndentIndex );
        }
        /*
        if ( nonIndentIndex == 0 ) {
            return string;
        } else {
            return string.substring ( nonIndentIndex );
        }
         */
    }

    private static int leadingNonIndentIndex ( @NotNull String string ) {
        return StringFinder.findFirstCharacter ( string, CharChecks::isNotSpaceOrTab );
    }
}
