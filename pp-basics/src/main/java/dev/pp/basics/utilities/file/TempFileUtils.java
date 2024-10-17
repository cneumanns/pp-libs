package dev.pp.basics.utilities.file;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TempFileUtils {

    public static @NotNull Path createEmptyTempFile (
        @Nullable String fileName,
        @Nullable String fileNameExtension,
        boolean deleteOnExit ) throws IOException {

        Path path = Files.createTempFile (
            fileName,
            fileNameExtension == null ? null : "." + fileNameExtension );

        if ( deleteOnExit ) path.toFile().deleteOnExit();

        return path;
    }

    public static @NotNull Path createEmptyTempFile ( boolean deleteOnExit ) throws IOException {

        return createEmptyTempFile ( null, null, deleteOnExit );
    }

    public static @NotNull Path createNonEmptyTempTextFile (
        @NotNull String content,
        @Nullable String fileName,
        @Nullable String fileNameExtension,
        boolean deleteOnExit ) throws IOException {

        Path path = createEmptyTempFile ( fileName, fileNameExtension, deleteOnExit );

        try ( FileWriter writer = new FileWriter ( path.toFile(), StandardCharsets.UTF_8 ) ) {
            writer.append ( content );
            writer.flush();
        }

        return path;
    }

    public static @NotNull Path createNonEmptyTempTextFile (
        @NotNull String content,
        boolean deleteOnExit ) throws IOException {

        return createNonEmptyTempTextFile ( content, null, null, deleteOnExit );
    }
}
