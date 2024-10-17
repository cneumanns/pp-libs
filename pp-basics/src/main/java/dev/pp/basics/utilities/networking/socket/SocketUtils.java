package dev.pp.basics.utilities.networking.socket;

import dev.pp.basics.annotations.NotNull;

import java.util.concurrent.Executors;

public class SocketUtils {
    public static void runInNewThread ( @NotNull Runnable runnable, boolean useVirtualThread ) {

        /* TODO Use as soon pp-libs uses Java version 21
        if ( useVirtualThread ) {
            Thread.ofVirtual().start ( runnable );
        } else {
            Thread.ofPlatform().start ( runnable );
        }
         */

        if ( useVirtualThread ) {
            throw new RuntimeException ( "Virtual threads are not yet supported in this Java version used. Please use Java version 21 or higher." );
        } else {
            new Thread ( runnable ).start();
        }
    }
}
