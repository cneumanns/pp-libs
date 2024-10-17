package dev.pp.text.resource;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.os.OSIO;

import java.io.Writer;

public class Stdout_TextResource implements TextResource {


    public static final @NotNull Stdout_TextResource INSTANCE = new Stdout_TextResource();

    private Stdout_TextResource() {}


    public @NotNull Writer getResource() { return OSIO.standardOutputUTF8Writer(); }

    public @NotNull String getName() { return "STDOUT"; }

    public @Nullable String getTextLine ( long lineNumber ) { return null; }

    @Override
    public String toString() { return getName(); }
}
