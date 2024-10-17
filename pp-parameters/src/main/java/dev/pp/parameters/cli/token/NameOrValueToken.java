package dev.pp.parameters.cli.token;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.location.TextLocation;
import dev.pp.text.token.TextToken;

import java.util.List;

public abstract class NameOrValueToken extends TextToken {

    public static @NotNull String listToString ( @NotNull List<NameOrValueToken> list ) {

        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for ( NameOrValueToken token : list ) {
            if ( ! isFirst ) sb.append ( ", " );
            sb.append ( token instanceof NameToken ? "n:" : "v:" );
            sb.append ( token.getText() );
            isFirst = false;
        }
        return sb.toString();
    }

    protected NameOrValueToken ( @NotNull String text, @Nullable TextLocation location ) {
        super ( text, location );
    }
}
