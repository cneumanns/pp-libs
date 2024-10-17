package dev.pp.basics.utilities.file;

import dev.pp.basics.annotations.NotNull;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileCheckUtils {

    /*
    public static boolean hasExtension ( @NotNull File file, @NotNull String fileNameExtension ) {
        assert ! fileNameExtension.isEmpty();

        return file.getName().endsWith ( "." + fileNameExtension );

    }
    */

    public static void checkIsExistingFile ( @NotNull Path filePath ) throws FileNotFoundException {

        checkIsNotDirectory ( filePath );
        // checkIsExistingFileOrDirectory ( file );
        if ( ! Files.exists ( filePath ) ) throw new FileNotFoundException (
            "File '" + FilePathUtils.makeAbsoluteAndNormalize ( filePath ) + "' does not exist." );
    }

    /*
    public static void checkIsExistingFileOrDirectory ( @NotNull File file ) throws FileNotFoundException {

        if ( ! file.exists() ) throw new FileNotFoundException (
            "File or directory '" + absolutePath ( file ) + "' does not exist." );
    }
    */

    public static void checkIsNotDirectory ( @NotNull Path path ) throws FileNotFoundException {

        if ( Files.isDirectory ( path ) ) throw new FileNotFoundException (
            "'" + FilePathUtils.makeAbsoluteAndNormalize ( path ) + "' is a directory. But a file is required." );
    }

/*
    public static void checkIsNotDirectory ( @NotNull File file ) throws IOException {

        if ( file.isDirectory() ) {
            throw new IOException ( fileIsDirectoryErrorMessage ( file ) ) {
                public String toString() {
                    return getMessage();
                }
            };
        }
    }

    private static String fileIsDirectoryErrorMessage ( @NotNull File file ) {
        return "'" + FilePathUtils.getAbsoluteOSPath( file ) + "' is a directory. But a file is required."; }

 */
}
