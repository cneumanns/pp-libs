package dev.pp.basics.utilities.file;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.DebugUtils;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileCopier {

    public static void copyFile (
        @NotNull Path sourceFile,
        @NotNull Path targetFile,
        @Nullable CopyOption... options ) throws IOException {

        // DebugUtils.writeNameValue ( "sourceFile", sourceFile );
        // DebugUtils.writeNameValue ( "targetFile", targetFile );

        // if ( Files.isSameFile ( sourceFile, targetFile ) ) {
        if ( sourceFile.equals ( targetFile ) ) {
            throw new IllegalArgumentException (
                "Source file " + sourceFile + " cannot be copied to target file " + targetFile + ", because both denote the same file." );
        }

        Files.copy ( sourceFile, targetFile, options );
    }

    public static void copyFile (
        @NotNull Path sourceFile,
        @NotNull Path targetFile,
        boolean replaceExistingFile,
        boolean copyAttributes,
        boolean noFollowLinks ) throws IOException {

        copyFile (
            sourceFile,
            targetFile,
            fileCopyOptions ( replaceExistingFile, copyAttributes, noFollowLinks ) );
    }

    public static void copyFileToExistingDirectory (
        @NotNull Path sourceFile,
        @NotNull Path targetDirectory,
        @Nullable CopyOption... options ) throws IOException {

        copyFile (
            sourceFile,
            targetDirectory.resolve ( sourceFile.getFileName() ),
            options );
    }

    public static CopyOption[] fileCopyOptions (
        boolean replaceExistingFile,
        boolean copyAttributes,
        boolean noFollowLinks ) {

        List<CopyOption> options = new ArrayList<> ();
        if ( replaceExistingFile ) options.add ( StandardCopyOption.REPLACE_EXISTING );
        if ( copyAttributes ) options.add ( StandardCopyOption.COPY_ATTRIBUTES );
        if ( noFollowLinks ) options.add ( LinkOption.NOFOLLOW_LINKS );

        return options.isEmpty () ? null : options.toArray ( new CopyOption[ 0 ] );
    }
}
