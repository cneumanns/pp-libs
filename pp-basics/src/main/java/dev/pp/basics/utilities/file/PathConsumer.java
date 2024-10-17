package dev.pp.basics.utilities.file;

import dev.pp.basics.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

public interface PathConsumer {

    void consume ( @NotNull Path file ) throws IOException;
}
