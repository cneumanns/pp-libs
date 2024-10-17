package dev.pp.text.utilities.file;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TextFileReaderUtil {

    public static @NotNull FileReader createUTF8FileReader ( @NotNull Path filePath ) throws IOException {
        return new FileReader ( filePath.toFile(), StandardCharsets.UTF_8 );
    }

    public static @Nullable String readTextFromUTF8File ( @NotNull Path filePath ) throws IOException {

        String result = Files.readString ( filePath );
        return result.isEmpty() ? null : result;
    }

    public static @Nullable List<String> readTextLinesFromUTF8File ( @NotNull Path filePath ) throws IOException {

        List<String> lines = Files.readAllLines ( filePath, StandardCharsets.UTF_8 );
        return lines.isEmpty() ? null : lines;
    }

    /*
    public static void closeIfFileReader ( @Nullable Reader reader ) throws IOException {

        if ( reader instanceof FileReader ) {
            reader.close();
        }
    }

    public static void closeIfFileReaderThrowingRuntimeException ( @Nullable Reader reader ) {

        try {
            closeIfFileReader ( reader );
        } catch ( IOException e ) {
            throw new RuntimeException ( e );
        }
    }
     */
}
