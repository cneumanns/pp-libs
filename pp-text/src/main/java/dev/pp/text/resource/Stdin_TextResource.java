package dev.pp.text.resource;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.os.OSIO;

import java.io.Reader;

public class Stdin_TextResource implements TextResource {


    public static final @NotNull Stdin_TextResource INSTANCE = new Stdin_TextResource();

    private Stdin_TextResource() {}


    public @NotNull Reader getResource() { return OSIO.standardInputUTF8Reader(); }

    public @NotNull String getName() { return "STDIN"; }

    public @Nullable String getTextLine ( long lineNumber ) { return null; }

    @Override
    public String toString() { return getName(); }
}
