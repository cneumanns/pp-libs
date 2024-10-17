package dev.pp.text.resource;

import java.io.IOException;
import java.net.URL;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import dev.pp.text.utilities.url.URLUtils;

public class URL_TextResource implements TextResource {

    private final @NotNull URL URL;


    public URL_TextResource ( @NotNull URL URL ) {
        this.URL = URL;
    }


    public @Nullable Object getResource() { return URL; }

    public @NotNull String getName() { return URL.toString(); }

    public @NotNull String getTextLine ( long lineNumber ) throws IOException {
        return URLUtils.getNthLineInURL ( URL, lineNumber );
    }

    @Override
    public String toString() { return "URL: " + URL; }
}
