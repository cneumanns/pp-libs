package dev.pp.basics.utilities.string;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.util.List;

public class StringReplacer {

    public static @NotNull String replaceAll (
        @NotNull String string,
        @NotNull String toReplace,
        @NotNull String replaceBy,
        @Nullable Integer expectedReplaceCount ) {

        assert ! string.isEmpty();
        // assert ! toReplace.isEmpty();

        @Nullable List<Integer> indexes = StringFinder.findAllSubStringIndexes ( string, toReplace );

        if ( expectedReplaceCount != null ) {
            int replaceCount = indexes == null ? 0 : indexes.size();
            if ( expectedReplaceCount != replaceCount ) throw new RuntimeException (
                "Expected replacements: " + expectedReplaceCount + ". Actual replacements: " + replaceCount + "." );
        }

        if ( indexes == null ) return string;

        int toReplaceLength = toReplace.length();
        StringBuilder result = new StringBuilder();
        int previousFoundEndIndex = -1;
        for ( int foundStartIndex : indexes ) {
            if ( foundStartIndex > previousFoundEndIndex + 1 )
                result.append ( string.substring ( previousFoundEndIndex + 1, foundStartIndex ) );
            result.append ( replaceBy );
            previousFoundEndIndex = foundStartIndex + toReplaceLength - 1;
        }

        if ( previousFoundEndIndex > 0 ) result.append ( string.substring ( previousFoundEndIndex + 1 ) );

        return result.toString();
    }

/*
    public static @NotNull String replaceAll (
        @NotNull String string,
        @NotNull String toReplace,
        @NotNull String replaceBy,
        @Nullable Integer expectedReplaceCount ) {

        assert ! string.isEmpty();
        assert ! toReplace.isEmpty();

        int toReplaceLength = toReplace.length();

        StringBuilder result = new StringBuilder();
        int replaceCount = 0;
        int startIndex = 0;
        int previousFoundEndIndex = -1;
        for (;;) {
            int foundIndex = string.indexOf ( toReplace, previousFoundEndIndex + 1 );
            if ( foundIndex < 0 ) break;
            result.append ( string.substring ( previousFoundEndIndex + 1, foundIndex - 1 ) );
            result.append ( replaceBy );
            previousFoundEndIndex = foundIndex + toReplaceLength;
            replaceCount++;
        }

        result.append (  )

        // string.replace ( toReplace, replaceBy );

        return result.toString();
    }

    public static @NotNull String replaceAll (
        @NotNull String string,
        @NotNull Pattern toReplace,
        @NotNull String replaceBy,
        @Nullable Integer expectedReplaceCount ) {
                        return new fa_string ( i_to_replace.getJavaPattern().matcher ( a_java_value )
                        .replaceAll ( i_replace_by.getJavaString() ) );
*/
}
