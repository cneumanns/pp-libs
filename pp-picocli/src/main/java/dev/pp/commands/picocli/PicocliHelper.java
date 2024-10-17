package dev.pp.commands.picocli;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.MutableOrImmutableParameterSpecs;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.Map;

public class PicocliHelper {

    public static @Nullable Map<String,String> parseResultToStringMap (
        @NotNull CommandLine.ParseResult parseResult,
        @NotNull MutableOrImmutableParameterSpecs<?> parameterSpecs ) {

        Map<String,String> map = new HashMap<> ();

        for ( ParameterSpec<?> parameterSpec : parameterSpecs.listSortedByIndex () ) {
            @NotNull String name = parameterSpec.getName();

            @Nullable Integer positionalIndex = parameterSpec.getPosition ();
            if ( positionalIndex == null ) {
                addOptionToStringMap ( name, parseResult, map );
            } else {
                addParameterToStringMap ( name, positionalIndex, parseResult, map );
            }
        }

        return map.isEmpty() ? null : map;
    }

    private static void addOptionToStringMap (
        @NotNull String optionName,
        @NotNull CommandLine.ParseResult parseResult,
        @NotNull Map<String,String> map ) {

        // String value = parseResult.matchedOptionValue ( optionName, null );
        // if ( value != null ) {
        //     map.put ( optionName, value );
        // }
        if ( ! parseResult.hasMatchedOption ( optionName ) ) return;

        map.put ( optionName, parseResult.matchedOptionValue ( optionName, null ) );
    }

    private static void addParameterToStringMap (
        @NotNull String parameterName,
        int position,
        @NotNull CommandLine.ParseResult parseResult,
        @NotNull Map<String,String> map ) {

        // String value = parseResult.matchedPositionalValue ( position, null );
        // if ( value != null ) {
        //     map.put ( parameterName, value );
        // }

        if ( ! parseResult.hasMatchedPositional ( position) ) return;

        map.put ( parameterName, parseResult.matchedPositionalValue ( position, null ) );
    }
}
