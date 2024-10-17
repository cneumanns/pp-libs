package dev.pp.basics.utilities.os.process;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.os.OSIO;
import dev.pp.basics.utilities.string.StringConstants;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.concurrent.Executors;

public class OSProcessRedirector {

/*
    private static record InputStreamGobbler (
        @NotNull InputStream inputStream,
        OutputStream outputStream ) implements Runnable {

        @Override public void run() {

            try {
                inputStream.transferTo ( outputStream );
            } catch ( IOException e ) {
                throw new RuntimeException ( e );
            }
        }
    }
*/

/*
    // Thanks to https://www.baeldung.com/run-shell-command-in-java
    private static class InputStreamLineGobbler implements Runnable {

        private final @NotNull InputStream inputStream;
        private final @NotNull Consumer<String> lineConsumer;

        public InputStreamLineGobbler (
            final @NotNull InputStream inputStream,
            final @NotNull Consumer<String> lineConsumer ) {

            this.inputStream = inputStream;
            this.lineConsumer = lineConsumer;
        }

        public void consume() {
            new BufferedReader ( new InputStreamReader ( inputStream ) )
                .lines()
                .forEach ( lineConsumer );
        }

        @Override
        public void run() {
            consume();
        }
    }
*/

    public static void allFromThis ( @NotNull Process process, @NotNull Charset inCharset ) {

        stdInFromThisIn ( process, inCharset );
        stdOutToThisOut ( process );
        stdErrToThisErr ( process );
    }


    // stdin

    public static void stdInFromThisIn ( @NotNull Process process, @NotNull Charset charset ) {

        // InputStreamLineGobbler outGobbler = new InputStreamLineGobbler ( process.getInputStream(), System.out::println );
        // run ( outGobbler );

        redirectReaderToWriter (
            OSIO.standardInputReader ( charset ),
            process.outputWriter ( charset ) );
    }

    public static void stdInFromReader (
        @NotNull Process process,
        @NotNull Reader reader,
        @NotNull Charset charset ) {

        redirectReaderToWriter (
            new BufferedReader ( reader ),
            process.outputWriter ( charset ) );
    }

    public static void stdInFromFile (
        @NotNull Process process,
        @NotNull Path file,
        @NotNull Charset charset ) throws IOException {

        stdInFromReader ( process, new FileReader ( file.toFile(), charset ), charset );
    }

    public static void stdinFromString (
        @NotNull Process process,
        @NotNull String input,
        @NotNull Charset charset ) {

        stdInFromReader ( process, new StringReader ( input ), charset );
/*
        BufferedWriter writer = process.outputWriter ( charset );
        // writer.write ( input );
        // writer.flush();
        run ( () -> {
            try {
                writer.write ( input );
                writer.flush();
            } catch ( IOException e ) {
                throw new RuntimeException ( e );
            }
        });
*/
    }


    // stdout, stderr

    public static void stdOutToThisOut ( @NotNull Process process ) {

        // InputStreamLineGobbler outGobbler = new InputStreamLineGobbler ( process.getInputStream(), System.out::println );
        // run ( outGobbler );

        redirectReaderToWriter ( process.inputReader(), new PrintWriter ( System.out ) );
    }

    public static void stdErrToThisErr ( @NotNull Process process ) {

        // InputStreamLineGobbler errGobbler = new InputStreamLineGobbler ( process.getErrorStream(), System.err::println );
        // run ( errGobbler );

        redirectReaderToWriter ( process.errorReader(), new PrintWriter ( System.err ) );
    }

    public static void stdOutToWriter (
        @NotNull Process process,
        @NotNull Writer writer,
        @NotNull Charset charset ) throws IOException {

        redirectReaderToWriter ( process.inputReader ( charset ), writer );
    }

    public static void stdErrToWriter (
        @NotNull Process process,
        @NotNull Writer writer,
        @NotNull Charset charset ) throws IOException {

        redirectReaderToWriter ( process.errorReader ( charset ), writer );
    }

    public static void stdOutToFile (
        @NotNull Process process,
        @NotNull Path file,
        @NotNull Charset charset ) throws IOException {

        stdOutToWriter ( process, new FileWriter ( file.toFile(), charset ), charset );
    }

    public static void stdErrToFile (
        @NotNull Process process,
        @NotNull Path file,
        @NotNull Charset charset ) throws IOException {

        stdErrToWriter ( process, new FileWriter ( file.toFile(), charset ), charset );
    }

    public static void stdOutToStringWriter (
        @NotNull Process process,
        @NotNull StringWriter writer,
        @NotNull Charset charset ) {

        redirectReaderToWriter ( process.inputReader ( charset ), writer );
    }

    public static void stdErrToStringWriter (
        @NotNull Process process,
        @NotNull StringWriter writer,
        @NotNull Charset charset ) {

        redirectReaderToWriter ( process.errorReader ( charset ), writer );
    }

/*
    public static void stdOutToStringBuilder (
        @NotNull Process process,
        @NotNull StringBuilder sb,
        @NotNull Charset charset ) {

        redirectReaderToStringBuilder ( process.inputReader ( charset ), sb );
    }

    public static void stdErrToStringBuilder (
        @NotNull Process process,
        @NotNull StringBuilder sb,
        @NotNull Charset charset ) {

        redirectReaderToStringBuilder ( process.errorReader ( charset ), sb );
    }

    private static void redirectReaderToStringBuilder (
        @NotNull BufferedReader reader,
        @NotNull StringBuilder sb ) {

        run ( () -> reader.lines()
            .forEach ( line -> {
                sb.append ( line );
                sb.append ( StringConstants.OS_LINE_BREAK );
            } ) );
    }
*/
    private static void redirectReaderToWriter (
        @NotNull BufferedReader reader,
        @NotNull Writer writer ) {

        run ( () -> reader.lines()
            .forEach ( line -> {
                try {
                    writer.write ( line );
                    writer.write ( StringConstants.OS_LINE_BREAK );
                    writer.flush();
                } catch ( IOException e ) {
                    throw new RuntimeException ( e );
                }
            } ) );
    }

    private static void run ( Runnable runnable ) {

        Executors.newSingleThreadExecutor().submit ( runnable );
    }
}
