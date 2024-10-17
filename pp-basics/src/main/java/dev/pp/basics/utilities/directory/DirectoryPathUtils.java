package dev.pp.basics.utilities.directory;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.file.FileCheckUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class DirectoryPathUtils {

    public static @NotNull Path makeAbsolute (
        @NotNull Path directoryPath,
        @NotNull Path rootDirectoryForRelativePath ) {

        if ( directoryPath.isAbsolute() ) {
            return directoryPath;
        } else {
            return rootDirectoryForRelativePath.resolve ( directoryPath );
        }
    }

    public static @NotNull List<Path> makeAbsolute (
        @NotNull List<Path> directoryPaths,
        @NotNull Path rootDirectoryForRelativePath ) {

        return directoryPaths.stream()
            .map ( p -> makeAbsolute ( p, rootDirectoryForRelativePath) )
            .toList();
    }

    public static @NotNull Path makeAbsolute (
        @NotNull Path directoryPath ) {

        return directoryPath.toAbsolutePath();
    }

    public static @NotNull List<Path> makeAbsolute (
        @NotNull List<Path> directoryPaths ) {

        return directoryPaths.stream()
            .map ( DirectoryPathUtils::makeAbsolute )
            .toList();
    }

    public static @NotNull Path makeAbsoluteAndNormalize (
        @NotNull Path directoryPath ) {

        return makeAbsolute ( directoryPath ).normalize();
    }

    public static @NotNull List<Path> makeAbsoluteAndNormalize (
        @NotNull List<Path> directoryPaths ) {

        return directoryPaths.stream()
            .map ( DirectoryPathUtils::makeAbsoluteAndNormalize )
            .toList();
    }

/*
    public static @NotNull Path toExistingOSPath (
        @NotNull Path directoryPath,
        @NotNull Path rootDirectoryForRelativePath ) throws IOException {

        @NotNull Path absolutePath = makeAbsolute ( directoryPath, rootDirectoryForRelativePath );
        return toExistingOSPath ( absolutePath );
    }

    public static @NotNull Path toExistingOSPath ( @NotNull Path directoryPath ) throws IOException {

        try {
            return directoryPath.toRealPath();
        } catch ( IOException e ) {
            // if file doesn't exist, 'toRealPath' throws IOException, not FileNotFoundException
            FileCheckUtils.checkIsExistingFile ( directoryPath );
            throw e;
        }
    }

 */
}
