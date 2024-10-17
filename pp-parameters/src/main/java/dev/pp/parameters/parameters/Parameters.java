package dev.pp.parameters.parameters;

import dev.pp.parameters.parameter.Parameter;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.token.TextToken;

import java.util.LinkedHashMap;
import java.util.Map;

public class Parameters<V> extends MutableOrImmutableParameters<V> {

    public Parameters ( @NotNull Map<String, Parameter<V>> map, @Nullable TextToken startToken ) {
        super ( map, startToken );
    }

    public static Parameters<String> createFromStringMap (
        @NotNull Map<String, String> stringMap, @Nullable TextToken startToken ) {

        Map<String, Parameter<String>> map = new LinkedHashMap<>();
        for ( Map.Entry<String, String> entry : stringMap.entrySet() ) {
            String name = entry.getKey();
            map.put ( name, new Parameter<> ( name, entry.getValue() ) );
        }

        return new Parameters<> ( map, startToken );
    }
}
