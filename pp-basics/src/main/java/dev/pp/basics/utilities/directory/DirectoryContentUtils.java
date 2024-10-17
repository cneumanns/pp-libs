package dev.pp.basics.utilities.directory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.file.PathConsumer;

public class DirectoryContentUtils {

    // public static final String DIRECTORY_NAME_EXCLUDE_PREFIX = "---";
    // public static final String FILE_NAME_EXCLUDE_PREFIX = "---";

    public static void forEachDirectoryOrFileInTree (
        @NotNull Path rootDirectory,
        @Nullable PathConsumer directoryConsumer,
        @Nullable PathConsumer fileConsumer ) throws IOException {

        DirectoryCheckUtils.checkIsExistingDirectory ( rootDirectory );

        Files.walkFileTree ( rootDirectory, EnumSet.of ( FileVisitOption.FOLLOW_LINKS ), Integer.MAX_VALUE,
            new SimpleFileVisitor<> () {

                @Override
                public FileVisitResult preVisitDirectory ( Path directoryPath, BasicFileAttributes attributes ) throws IOException {
                    if ( directoryConsumer != null ) directoryConsumer.consume ( directoryPath );
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile ( Path filePath, BasicFileAttributes attributes ) throws IOException {
                    if ( fileConsumer != null ) fileConsumer.consume ( filePath );
                    return FileVisitResult.CONTINUE;
                }
            });
    }

    public static void forEachDirectoryInTree (
        @NotNull Path rootDirectory,
        @NotNull PathConsumer directoryConsumer ) throws IOException {

        forEachDirectoryOrFileInTree ( rootDirectory, directoryConsumer, null );
    }

    public static void forEachFileInTree (
        @NotNull Path rootDirectory,
        @Nullable PathConsumer fileConsumer ) throws IOException {

        forEachDirectoryOrFileInTree ( rootDirectory, null, fileConsumer );
    }

    public static void forEachFileInDirectory (
        @NotNull Path directory,
        @NotNull PathConsumer fileConsumer ) throws IOException {

        @Nullable List<Path> files = filesInDirectory ( directory );

        if ( files == null ) return;

        for ( Path file : files ) {
            fileConsumer.consume ( file );
        }
    }

    public static @Nullable List<Path> filesInTree ( @NotNull Path rootDirectory ) throws IOException {

        List<Path> files = new ArrayList<>();
        forEachFileInTree ( rootDirectory, files::add );
        return files.isEmpty() ? null : files;
    }

    public static @Nullable List<Path> filesInTree (
        @NotNull Path rootDirectory,
        @NotNull Predicate<Path> filePredicate ) throws IOException {

        List<Path> files = new ArrayList<>();
        forEachFileInTree ( rootDirectory, path -> {
            if ( filePredicate.test ( path ) ) {
                files.add ( path );
            }
        });
        return files.isEmpty() ? null : files;
    }

    public static @Nullable List<Path> filesInDirectory ( @NotNull Path directory ) throws IOException {

        List<Path> files = new ArrayList<>();
        Files.list ( directory )
            .filter ( Files::isRegularFile )
            .forEach ( files::add );
        return files.isEmpty() ? null : files;
    }
}
