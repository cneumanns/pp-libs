package dev.pp.datatype.utils.writer;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.StringConstants;

import java.io.IOException;
import java.io.Writer;

public interface DataWriter<T> {

    void write ( @Nullable T object, @NotNull Writer writer, @NotNull String nullString ) throws IOException;

    default void write ( @Nullable T object, @NotNull Writer writer ) throws IOException {

        write ( object, writer, StringConstants.NULL_AS_STRING );
    }
}
