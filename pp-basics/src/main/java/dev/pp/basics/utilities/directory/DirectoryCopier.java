package dev.pp.basics.utilities.directory;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.file.FileCopier;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public class DirectoryCopier {

    /*
    public static void copyDirectory (
        @NotNull Path sourceDirectory,
        @NotNull Path targetDirectory ) throws IOException {
    }
    */

    public static void copyDirectoryContent (
        @NotNull Path sourceDirectory,
        @NotNull Path targetDirectory,
        @Nullable Predicate<Path> includedFileFilter,
        @Nullable CopyOption... options ) throws IOException {

        DirectoryContentUtils.forEachDirectoryOrFileInTree (
            sourceDirectory,
            sourceChildDirectory -> {
                Path relativeChildDirectory = sourceDirectory.relativize ( sourceChildDirectory );
                Path targetChildDirectory = Path.of ( targetDirectory.toString(), relativeChildDirectory.toString() );
                DirectoryCreator.createWithParentsIfNotExists ( targetChildDirectory );
            },
            sourceFile -> {
                if ( includedFileFilter == null || includedFileFilter.test ( sourceFile ) ) {
                    Path relativeFilePath = sourceDirectory.relativize ( sourceFile );
                    Path targetFile = Path.of ( targetDirectory.toString (), relativeFilePath.toString () );
                    FileCopier.copyFile ( sourceFile, targetFile, options );
                }
            } );
    }

    public static void copyDirectoriesAndFiles (
        @NotNull List<Path> directoriesAndFiles,
        @NotNull Path targetDirectory,
        @Nullable CopyOption... options ) throws IOException {

        for ( Path directoryOrFile : directoriesAndFiles ) {
            if ( Files.isDirectory ( directoryOrFile ) ) {
                copyDirectoryContent ( directoryOrFile, targetDirectory, null, options );
            } else {
                FileCopier.copyFileToExistingDirectory ( directoryOrFile, targetDirectory, options );
            }
        }
    }
}
