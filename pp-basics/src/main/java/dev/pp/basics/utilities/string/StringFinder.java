package dev.pp.basics.utilities.string;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.character.CharPredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class StringFinder {

    public static int findFirstCharacter ( @NotNull String string, @NotNull CharPredicate predicate ) {

        for ( int i = 0; i < string.length(); i++ ) {
            char c = string.charAt ( i );
            if ( predicate.accept ( c ) ) {
                return i;
            }
        }
        return -1;
    }

    public static void findAllSubStrings (
        @NotNull String string,
        @NotNull String subString,
        Consumer<Integer> handler ) {

        int stringLength = string.length();
        if ( stringLength == 0 ) return;

        int subStringLength = subString.length();
        assert subStringLength > 0;

        int previousFoundEndIndex = -1;
        for (;;) {
            int foundIndex = string.indexOf ( subString, previousFoundEndIndex + 1 );
            if ( foundIndex < 0 ) return;
            handler.accept ( foundIndex );
            previousFoundEndIndex = foundIndex + subStringLength - 1;
            if ( previousFoundEndIndex == stringLength - 1 ) return;
        }
    }

    public static @Nullable List<Integer> findAllSubStringIndexes (
        @NotNull String string,
        @NotNull String subString ) {

        List<Integer> indexes = new ArrayList<>();
        findAllSubStrings ( string, subString, indexes::add );

        return indexes.isEmpty() ? null : indexes;
    }

    public static int countSubString (
        @NotNull String string,
        @NotNull String subString ) {

        List<Integer> indexes = findAllSubStringIndexes ( string, subString );
        return indexes == null ? 0 : indexes.size();
    }

    public static void findAllRegexes (
        @NotNull String string,
        @NotNull Pattern toFind,
        @NotNull Consumer<MatchResult> handler ) {

        if ( string.length() == 0 ) return;

        toFind.matcher ( string ).results().forEach ( handler );
    }

    public static @Nullable List<MatchResult> findAllMatches (
        @NotNull String string,
        @NotNull Pattern toFind ) {

        List<MatchResult> matches = new ArrayList<>();
        findAllRegexes ( string, toFind, matches::add );

        return matches.isEmpty() ? null : matches;
    }

    public static int countMatches (
        @NotNull String string,
        @NotNull Pattern toFind ) {

        List<MatchResult> matches = findAllMatches ( string, toFind );
        return matches == null ? 0 : matches.size();
    }
}
