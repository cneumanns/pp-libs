package dev.pp.text.utilities.file;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.directory.DirectoryCreator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextFileWriterUtil {

    public static @NotNull FileWriter createUTF8FileWriter (
        @NotNull Path filePath,
        boolean createDirIfNotExists ) throws IOException {

        createDirIfNotExists ( createDirIfNotExists, filePath );
        return new FileWriter ( filePath.toFile(), StandardCharsets.UTF_8 );
    }

    /*
    public static @NotNull FileWriter createDirAndGetUTF8FileWriter ( @NotNull Path filePath ) throws IOException {

        DirectoryCreator.createWithParentsIfNotExists ( filePath.getParent() );
        return getUTF8FileWriter ( filePath );
    }
     */

    public static void writeTextToUTF8File (
        @NotNull String text,
        @NotNull Path filePath,
        boolean createDirIfNotExists ) throws IOException {

        createDirIfNotExists ( createDirIfNotExists, filePath );
        Files.writeString ( filePath, text, StandardCharsets.UTF_8 );
    }


    private static void createDirIfNotExists (
        boolean createDirIfNotExists, @NotNull Path filePath ) throws IOException {

        if ( createDirIfNotExists ) {
            @Nullable Path directoryPath = filePath.toAbsolutePath().getParent();
            if ( directoryPath != null ) {
                DirectoryCreator.createWithParentsIfNotExists ( directoryPath );
            }
        }
    }

/*
    public static void writeLineOrThrow ( @NotNull Writer writer, @Nullable String line ) {

        try {
            writeLine ( writer, line );
        } catch ( IOException e ) {
            throw new RuntimeException ( e );
        }
    }

    public static void writeLine ( @NotNull Writer writer, @Nullable String line ) throws IOException {

        if ( line != null ) writer.write ( line );
        writeLineBreak ( writer );
    }

    public static void writeLineBreak ( @NotNull Writer writer ) throws IOException {

        writer.write ( StringConstants.OS_LINE_BREAK );
    }

    public static void closeIfFileWriter ( @Nullable Writer writer ) throws IOException {

        if ( writer instanceof FileWriter ) {
            writer.close ();
        }
    }
 */
}
