package dev.pp.text.REMOVEobjectwriterOLD.impl;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.utilities.file.TextFileWriterUtil;
import dev.pp.text.REMOVEobjectwriterOLD.ObjectSerializer;
import dev.pp.text.REMOVEobjectwriterOLD.ObjectSerializerEventHandler;
import dev.pp.text.REMOVEobjectwriterOLD.ObjectWriterException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class ObjectToTextSerializerUtil {

    public static <T> void serialize ( @Nullable T object, @NotNull Writer writer ) throws IOException, ObjectWriterException {

        ObjectSerializerEventHandler delegate = new TextObjectWriterDelegate ( writer );
        ObjectSerializer serializer = new ObjectSerializer ( delegate );
        serializer.write ( object );
    }

    public static <T> void serializeToFile ( @Nullable T object, @NotNull Path filePath ) throws IOException, ObjectWriterException {

        try ( FileWriter writer = TextFileWriterUtil.createUTF8FileWriter ( filePath, true ) ) {
            serialize ( object, writer );
        }
    }

    public static <T> void serializeToStdOut ( @Nullable T object ) throws IOException, ObjectWriterException {

        serialize ( object, new OutputStreamWriter ( new BufferedOutputStream ( System.out ),
            StandardCharsets.UTF_8 ) );
    }

    public static <T> @Nullable String serializeAsString ( @Nullable T object ) throws ObjectWriterException {

        try ( StringWriter writer = new StringWriter() ) {
            serialize ( object, writer );
            return writer.toString();
        } catch ( IOException e ) {
            throw new RuntimeException ( e );
        }
    }
}
