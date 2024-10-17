package dev.pp.basics.utilities.networking.socket;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.SimpleLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements Runnable { // }, Closeable {


    public static SocketServer runInNewThread (
        int port,
        @NotNull ServerRequestsHandler requestHandler ) {

        return runInNewThread ( port, requestHandler, true );
    }

    public static SocketServer runInNewThread (
        int port,
        @NotNull ServerRequestsHandler requestHandler,
        boolean useVirtualThread ) {

        SocketServer server = new SocketServer ( port, requestHandler, useVirtualThread );
        SocketUtils.runInNewThread ( server, useVirtualThread );
        return server;
    }


    private final int port;
    // private ServerSocket serverSocket;
    private final @NotNull ServerRequestsHandler requestHandler;
    private final boolean useVirtualThread;


    public SocketServer (
        int port,
        @NotNull ServerRequestsHandler requestHandler,
        boolean useVirtualThread ) {

        this.port = port;
        this.requestHandler = requestHandler;
        this.useVirtualThread = useVirtualThread;
    }


    public void run() {

        SimpleLogger.debug ( "Server: start listening on port " + port );

        try ( ServerSocket serverSocket = new ServerSocket ( port ) ) {
            // this.serverSocket = serverSocket_;
            while ( true ) {
                SimpleLogger.debug ( "Server: waiting for connection" );
                Socket clientSocket = serverSocket.accept();
                SimpleLogger.debug ( "Server: client connection accepted" );
                ServerConnectionHandler.startInNewThread ( clientSocket, requestHandler, useVirtualThread );
            }
        } catch ( IOException e ) {
            // TODO
            throw new RuntimeException ( e );
        }

    }

/*
    public void close() throws IOException {
        serverSocket.close();
    }
 */
}
