package dev.pp.datatype.newversion;

import dev.pp.basics.annotations.NotNull;
import dev.pp.text.inspection.InvalidDataException;
import dev.pp.text.reader.CharReader;

import java.io.IOException;

public interface ValueReader_NEW<T> {

    @NotNull T read ( @NotNull CharReader charReader ) throws IOException, InvalidDataException;
}
