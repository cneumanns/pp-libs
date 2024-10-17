package dev.pp.datatype.newversion;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.InvalidDataException;
import dev.pp.text.token.TextToken;

public interface ValueValidator_NEW<T> {

    @Nullable InvalidDataException check ( @NotNull T value, @Nullable TextToken token );

    default void validate ( @NotNull T value, @Nullable TextToken token ) throws InvalidDataException {

        InvalidDataException exception = check ( value, token );
        if ( exception != null ) {
            throw exception;
        }
    }

    default boolean isValid ( @NotNull T value ) {
        return check ( value, null ) == null;
    }
}
