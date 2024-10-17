package dev.pp.text.REMOVEobjectwriterOLD;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.utilities.file.TextFileWriterUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Deprecated
public interface GenericObjectWriter <T> {

    void write ( @Nullable T object, @NotNull Writer writer ) throws IOException, ObjectWriterException;

    default void writeToFile ( @Nullable T object, @NotNull Path filePath ) throws IOException, ObjectWriterException {

        try ( FileWriter writer = TextFileWriterUtil.createUTF8FileWriter ( filePath, true ) ) {
            write ( object, writer );
        }
    }

    default void writeToStdOut ( @Nullable T object ) throws IOException, ObjectWriterException {
        write ( object, new OutputStreamWriter ( new BufferedOutputStream ( System.out ),
            StandardCharsets.UTF_8 ) );
    }

    default @Nullable String writeAsString ( @Nullable T object ) throws ObjectWriterException {

        try ( StringWriter writer = new StringWriter() ) {
            write ( object, writer );
            return writer.toString();
        } catch ( IOException e ) {
            throw new RuntimeException ( e );
        }
    }

    // void flush();
}
