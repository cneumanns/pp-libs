package dev.pp.basics.utilities.directory;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FilesFinder {

    public static @Nullable List<Path> filesInTree (
        @NotNull Path rootDirectory,
        @NotNull Predicate<Path> filePredicate ) throws IOException {

        List<Path> filesFound = new ArrayList<>();
        DirectoryContentUtils.forEachFileInTree ( rootDirectory, file -> {
            if ( filePredicate.test ( file ) )
                filesFound.add ( file );
        });

        return filesFound.isEmpty() ? null : filesFound;
    }

    public static @Nullable List<Path> filesWithExactNameInTree (
        @NotNull Path rootDirectory,
        @NotNull String fileName,
        boolean ignoreCase ) throws IOException {

        Predicate<Path> predicate = ignoreCase ?
            file -> file.getFileName().toString().equalsIgnoreCase ( fileName ) :
            file -> file.getFileName().toString().equals ( fileName );

        return filesInTree ( rootDirectory, predicate );
    }
}
