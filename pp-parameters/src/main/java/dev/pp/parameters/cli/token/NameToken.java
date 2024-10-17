package dev.pp.parameters.cli.token;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.location.TextLocation;

public class NameToken extends NameOrValueToken {

    /*
    private final boolean isImplicitlyDefined;
    public boolean isImplicitlyDefined() { return isImplicitlyDefined; }
    */

    public NameToken ( @NotNull String text, @Nullable TextLocation location ) {
        super ( text, location );
    }
}
