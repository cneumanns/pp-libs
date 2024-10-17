package dev.pp.parameters.parameterspecs;

import dev.pp.parameters.parameters.Parameters;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.datatype.utils.validator.DataValidator;

import java.util.List;
import java.util.Map;

public class ParameterSpecs<V> extends MutableOrImmutableParameterSpecs<V> {

    public ParameterSpecs (
        @NotNull List<ParameterSpec<V>> list,
        @NotNull Map<String, ParameterSpec<V>> allNamesMap,
        @Nullable DataValidator<Parameters<V>> validator ) {

        super ( list, allNamesMap, validator );
    }
}
