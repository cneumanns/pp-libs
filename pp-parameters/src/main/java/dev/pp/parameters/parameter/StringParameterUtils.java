package dev.pp.parameters.parameter;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

public class StringParameterUtils {

    public static @NotNull Parameter<String> emptyValueToNull ( @NotNull Parameter<String> stringParameter ) {

        @Nullable String value = stringParameter.getValue();
        if ( value != null && value.isEmpty() ) {
            return new Parameter<>(
                stringParameter.getName(),
                null,
                stringParameter.getSpec(),
                stringParameter.getNameLocation(),
                stringParameter.getValueLocation() );
        } else {
            return stringParameter;
        }
    }
}
