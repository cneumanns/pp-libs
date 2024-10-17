package dev.pp.datatype.utils.validator;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.location.TextLocation;
import dev.pp.text.token.TextToken;

public interface DataValidator<T> {

    void validate ( @NotNull T object, @Nullable TextToken token ) throws DataValidatorException;

    default boolean isValid ( @NotNull T object ) {

        try {
            validate ( object, null );
            return true;
        } catch ( DataValidatorException e ) {
            return false;
        }
    }
}
