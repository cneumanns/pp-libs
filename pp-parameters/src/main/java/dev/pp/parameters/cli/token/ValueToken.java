package dev.pp.parameters.cli.token;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.location.TextLocation;

public class ValueToken extends NameOrValueToken {

    /**
     * The position if no name is specified (0=first position)
     */
    private final @Nullable Integer position;
    public @Nullable Integer getPosition() { return position; }

    public ValueToken ( @NotNull String text, @Nullable TextLocation location, @Nullable Integer position ) {
        super ( text, location );
        this.position = position;
    }
}
