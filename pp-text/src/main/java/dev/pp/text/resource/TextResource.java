package dev.pp.text.resource;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;

public interface TextResource {

    @Nullable Object getResource();

    default @Nullable Path getResourceAsFilePath () {

        @Nullable Object resource = getResource();
        if ( resource instanceof Path path ) {
            return path;
        } else if ( resource instanceof File file ) {
            return file.toPath();
        } else {
            return null;
        }
    }

    @NotNull String getName();

    @Nullable String getTextLine ( long lineNumber ) throws Exception;


    /*
    default @Nullable String getTextLineOrNull ( long lineNumber ) {

        try {
            return getTextLine ( lineNumber );
        } catch ( Exception e ) {
            return null;
        }
    }

    default @NotNull String getTextLineWithMarker ( long lineNumber, int markerPosition, @NotNull String marker )
        throws Exception {

        String line = getTextLine ( lineNumber );
        return StringUtilities.insertMarker ( line, markerPosition, marker );
    }
    */
}
