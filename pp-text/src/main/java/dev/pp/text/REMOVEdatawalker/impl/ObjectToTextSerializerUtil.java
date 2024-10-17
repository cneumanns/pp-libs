package dev.pp.text.REMOVEdatawalker.impl;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.utilities.file.TextFileWriterUtil;
import dev.pp.text.REMOVEdatawalker.DataWalkerException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class ObjectToTextSerializerUtil {

    public static <T> void serialize (
        @Nullable T object,
        @NotNull Writer writer,
        @NotNull ObjectDataWalkerConfig config ) throws IOException, DataWalkerException {

        TextWriterObjectDataWalkerEventHandler eventHandler = new TextWriterObjectDataWalkerEventHandler ( writer );
        ObjectDataWalker walker = new ObjectDataWalker ( config );
        walker.walk ( object, eventHandler );
    }

    public static <T> void serializeToFile (
        @Nullable T object,
        @NotNull Path filePath,
        @NotNull ObjectDataWalkerConfig config ) throws IOException, DataWalkerException {

        try ( FileWriter writer = TextFileWriterUtil.createUTF8FileWriter ( filePath, true ) ) {
            serialize ( object, writer, config );
        }
    }

    public static <T> void serializeToStdOut (
        @Nullable T object,
        @NotNull ObjectDataWalkerConfig config ) throws IOException, DataWalkerException {

        serialize ( object, new OutputStreamWriter ( new BufferedOutputStream ( System.out ),
            StandardCharsets.UTF_8 ), config );
    }

    public static <T> @Nullable String serializeAsString (
        @Nullable T object,
        @NotNull ObjectDataWalkerConfig config ) throws DataWalkerException {

        try ( StringWriter writer = new StringWriter() ) {
            serialize ( object, writer, config );
            return writer.toString();
        } catch ( IOException e ) {
            throw new RuntimeException ( e );
        }
    }
}
