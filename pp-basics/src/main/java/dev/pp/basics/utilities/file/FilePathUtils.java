package dev.pp.basics.utilities.file;

import dev.pp.basics.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

public class FilePathUtils {

    public static @NotNull Path makeAbsolute (
        @NotNull Path filePath,
        @NotNull Path rootDirectoryForRelativePath ) {

        if ( filePath.isAbsolute() ) {
            return filePath;
        } else {
            return rootDirectoryForRelativePath.resolve ( filePath );
        }
    }

    public static @NotNull Path makeAbsolute (
        @NotNull Path filePath ) {

        return filePath.toAbsolutePath();
    }

    public static @NotNull Path makeAbsoluteAndNormalize (
        @NotNull Path filePath ) {

        return makeAbsolute ( filePath ).normalize();
    }

/*
    public static @NotNull File filePathToFile (
        @NotNull String filePath,
        @NotNull File rootDirectoryForRelativeFile ) {

        return filePathToFile ( Path.of ( filePath ), rootDirectoryForRelativeFile );
    }
*/

    public static @NotNull Path toExistingOSPath (
        @NotNull Path filePath,
        @NotNull Path rootDirectoryForRelativeFilePath ) throws IOException {

        @NotNull Path absolutePath = makeAbsolute ( filePath, rootDirectoryForRelativeFilePath );
        return toExistingOSPath ( absolutePath );
    }

    public static @NotNull Path toExistingOSPath ( @NotNull Path filePath ) throws IOException {

        try {
            return filePath.toRealPath();
        } catch ( IOException e ) {
            // if file doesn't exist, 'toRealPath' throws IOException, not FileNotFoundException
            FileCheckUtils.checkIsExistingFile ( filePath );
            throw e;
        }
    }

/*
    public static Path getAbsoluteOSPath ( Path file ) {

        try {
            return file.toRealPath();
        } catch ( IOException e ) {
            return file.toAbsolutePath().normalize();
        }
    }

 */

/*
    @Deprecated
    public static String getAbsoluteOSPath ( File file ) {

        try {
            return file.getCanonicalPath();
        } catch ( IOException e ) {
            return file.getAbsolutePath();
        }
    }
 */
}
