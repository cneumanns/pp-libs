package dev.pp.datatype.newversion;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.InvalidDataException;
import dev.pp.text.location.TextLocation;
import dev.pp.text.token.TextToken;

public interface ValueParser_NEW<T> {

    @NotNull T parse ( @NotNull String string, @Nullable TextLocation location ) throws InvalidDataException;

    default @Nullable T parse ( @NotNull TextToken textToken ) throws InvalidDataException {
        return parse ( textToken.getText(), textToken.getLocation() );
    }
}
