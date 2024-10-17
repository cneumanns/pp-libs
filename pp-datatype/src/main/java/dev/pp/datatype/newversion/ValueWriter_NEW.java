package dev.pp.datatype.newversion;

import dev.pp.basics.annotations.NotNull;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public interface ValueWriter_NEW<T> {

    void write ( @NotNull T value, @NotNull Writer writer ) throws IOException;

    default @NotNull String valueToString ( @NotNull T value ) {

        try ( StringWriter stringWriter = new StringWriter() ) {
            write ( value, stringWriter );
            return stringWriter.toString ();
        } catch ( IOException e ) {
            throw new RuntimeException ( e );
        }
    }
}
