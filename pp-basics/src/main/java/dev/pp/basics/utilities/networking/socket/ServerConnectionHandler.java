package dev.pp.basics.utilities.networking.socket;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.SimpleLogger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerConnectionHandler implements Runnable {

    public static void startInNewThread (
        @NotNull Socket clientSocket,
        @NotNull ServerRequestsHandler requestHandler,
        boolean useVirtualThread ) {

        ServerConnectionHandler connectionHandler = new ServerConnectionHandler ( clientSocket, requestHandler );
        SocketUtils.runInNewThread ( connectionHandler, useVirtualThread );
    }


    private final Socket clientSocket;
    private final ServerRequestsHandler requestHandler;


    private ServerConnectionHandler (
        @NotNull Socket clientSocket,
        @NotNull ServerRequestsHandler requestHandler ) {

        this.clientSocket = clientSocket;
        this.requestHandler = requestHandler;
    }


    public void run() {

        try ( InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream() ) {

            requestHandler.handleRequests ( inputStream, outputStream );
            clientSocket.close();

            SimpleLogger.debug ( "Server: connection with client closed." );

        } catch ( IOException e ) {
            // TODO
            throw new RuntimeException ( e );
        }
    }
}
