package dev.pp.basics.utilities.networking.socket;

import dev.pp.basics.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ServerRequestsHandler {

    void handleRequests ( @NotNull InputStream inputStream, @NotNull OutputStream outputStream ) throws IOException;
}
