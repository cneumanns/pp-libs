package dev.pp.text.resource;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.os.OSIO;

import java.io.Writer;

public class Stderr_TextResource implements TextResource {


    public static final @NotNull Stderr_TextResource INSTANCE = new Stderr_TextResource ();

    private Stderr_TextResource () {}


    public @NotNull Writer getResource() { return OSIO.standardErrorUTF8Writer(); }

    public @NotNull String getName() { return "STDERR"; }

    public @Nullable String getTextLine ( long lineNumber ) { return null; }

    @Override
    public String toString() { return getName(); }
}
