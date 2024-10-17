package dev.pp.basics.utilities.os.process;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class OSProcess {

    public static Process create (
        @NotNull List<String> commandTokens,
        @Nullable Path workingDirectory,
        @Nullable Map<String,String> extraEnvironment ) throws IOException {

        return create ( commandTokens.toArray ( new String[0] ), workingDirectory, extraEnvironment );
    }

    public static Process create ( @NotNull List<String> commandTokens ) throws IOException {

        return create ( commandTokens, null, null );
    }

    public static Process create (
        @NotNull String[] commandTokens,
        @Nullable Path workingDirectory,
        @Nullable Map<String,String> extraEnvironment ) throws IOException {

        ProcessBuilder builder = new ProcessBuilder( commandTokens );

        if ( workingDirectory != null ) {
            builder.directory ( workingDirectory.toFile() );
        }

        if ( extraEnvironment != null ) {
            builder.environment().putAll ( extraEnvironment );
        }

        return builder.start();
    }

    public static Process create ( @NotNull String[] commandTokens ) throws IOException {

        return create ( commandTokens, null, null );
    }

    public static Process create ( @NotNull String OSCommand ) throws IOException {

        // return new ProcessBuilder ( OSCommandAndArgs ).start();
        return Runtime.getRuntime().exec ( OSCommand );
    }
}
