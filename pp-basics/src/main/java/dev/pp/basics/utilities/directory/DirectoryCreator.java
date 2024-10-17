package dev.pp.basics.utilities.directory;

import dev.pp.basics.annotations.NotNull;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirectoryCreator {

    public static void create ( @NotNull Path directory ) throws IOException {

        Files.createDirectory ( directory );
    }

    public static void createIfNotExists ( @NotNull Path directory ) throws IOException {

        if ( ! Files.exists ( directory ) ) {
            Files.createDirectory ( directory );
        }
    }

    public static void createWithParents (
        @NotNull Path directory ) throws IOException {

        if ( directory.toFile().exists() )
            throw new FileAlreadyExistsException ( directory.toFile().getAbsolutePath() + " exists already" );

        Files.createDirectories ( directory );
    }

    public static void createWithParentsIfNotExists (
        @NotNull Path directory ) throws IOException {

        Files.createDirectories ( directory );
    }
}
