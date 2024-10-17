package dev.pp.text.resource;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.StringTruncator;
import dev.pp.text.utilities.text.TextLines;

import java.io.IOException;

public class String_TextResource implements TextResource {

    private final @NotNull String string;


    public String_TextResource ( @NotNull String string ) {
        this.string = string;
    }


    public @Nullable Object getResource() { return string; }

    public @NotNull String getName() { return "string"; }

    public @NotNull String getTextLine ( long lineNumber ) throws IOException {
        return TextLines.getNthLine ( string, lineNumber );
    }

    @Override
    public String toString() { return "String: " + StringTruncator.truncateWithEllipses ( string ); }
}
