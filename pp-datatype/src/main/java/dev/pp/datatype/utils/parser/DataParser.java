package dev.pp.datatype.utils.parser;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.location.TextLocation;
import dev.pp.text.token.TextToken;

public interface DataParser<T> {

    default @NotNull T parse ( @NotNull TextToken token ) throws DataParserException {
        return parse ( token.getText(), token.getLocation() );
    }

    @NotNull T parse ( @NotNull String string, @Nullable TextLocation location ) throws DataParserException;
}
