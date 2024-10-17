package dev.pp.basics.utilities;

import dev.pp.basics.annotations.NotNull;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class URLUtils {

    public static @NotNull URL createForPath ( @NotNull Path path ) {
        try {
            return path.toUri().toURL();
        } catch ( MalformedURLException e ) {
            throw new RuntimeException ( e );
        }
    }

    public static @NotNull Reader getUTF8URLReader ( @NotNull String URLString ) throws IOException {

        return getUTF8URLReader ( new URL ( URLString ) );
    }

    public static @NotNull Reader getUTF8URLReader ( @NotNull URL URL ) throws IOException {

        return new InputStreamReader ( URL.openStream(), StandardCharsets.UTF_8 );
    }

    public static @NotNull String readUTF8Text ( @NotNull String URLString ) throws IOException {

        return readUTF8Text ( new URL ( URLString ) );
    }

    public static @NotNull String readUTF8Text ( @NotNull URL URL ) throws IOException {

        try ( InputStream is = URL.openStream() ) {
            return new String ( is.readAllBytes(), StandardCharsets.UTF_8 );
        }
    }
}
