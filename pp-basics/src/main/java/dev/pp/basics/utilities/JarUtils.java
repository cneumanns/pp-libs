package dev.pp.basics.utilities;

import dev.pp.basics.annotations.NotNull;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class JarUtils {

    public static @NotNull URL getJarURLForClass ( @NotNull Class<?> classContainedInJar ) {

        return classContainedInJar
            .getProtectionDomain()
            .getCodeSource()
            .getLocation();
    }

    public static @NotNull URI getJarURIForClass ( @NotNull Class<?> classContainedInJar ) {

        try {
            return getJarURLForClass ( classContainedInJar ).toURI ();
        } catch ( URISyntaxException e ) {
            throw new RuntimeException ( e );
        }
    }

    public static @NotNull FileSystem getJarFileSystemForClass ( @NotNull Class<?> classContainedInJar ) throws IOException {

        // Path path = Paths.get ( getJarURIForClass ( classContainedInJar ) );
        // return FileSystems.newFileSystem ( path );

        // This does not work in GraalVM's native image
        return FileSystems.newFileSystem ( getJarURIForClass ( classContainedInJar ), null );
    }

    public static @NotNull Path getDirectoryInJarFileForClass (
        @NotNull Class<?> classContainedInJar,
        @NotNull Path directory ) throws IOException {

        return getJarFileSystemForClass ( classContainedInJar )
            .getPath ( directory.toString() );
    }
}
