package dev.pp.text.reader.stack;

import dev.pp.basics.annotations.NotNull;
import dev.pp.text.reader.CharReader;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public interface CharReaderWithInserts extends CharReader {

    // @NotNull CharReader getCurrentReader();

    void insert ( @NotNull CharReader iterator );

    @Deprecated // reader must be closed after reading
    void insert ( @NotNull Path path ) throws IOException;

    // @Deprecated // reader must be closed after reading
    // void insert ( @NotNull URL url ) throws IOException;

    void insert ( @NotNull String string );
}
