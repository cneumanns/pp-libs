package dev.pp.text.resource.writer;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.utilities.file.TextFileWriterUtil;
import dev.pp.basics.utilities.os.OSIO;
import dev.pp.text.resource.*;

import java.io.*;
import java.nio.file.Path;

public class TextResourceWriter implements Closeable {


    // In a CLI application, the standard way to specify that output should be
    // written to STDOUT is by not providing a specific output file argument,
    // as most CLI tools default to STDOUT when no output file is given.
    // However, when an argument is required to explicitly denote STDOUT,
    // the hyphen (-) is commonly used, just as it is for STDIN.
    public static @NotNull String STDOUT_CLI_ARGUMENT_VALUE = "-";
    public static @NotNull String STDOUT_CLI_ARGUMENT_VALUE_ALTERNATIVE = "STDOUT";


    private final @NotNull Writer writer;
    @NotNull public Writer getWriter() { return writer; }

    private final @Nullable TextResource textResource;
    @Nullable public TextResource getTextResource() { return textResource; }

    private final boolean isStdoutWriter;
    public boolean isStdoutWriter() { return isStdoutWriter; }

    private final boolean isStderrWriter;
    public boolean isStderrWriter() { return isStderrWriter; }


    public TextResourceWriter (
        @NotNull Writer writer,
        @Nullable TextResource textResource,
        boolean isStdoutWriter,
        boolean isStderrWriter ) {

        assert ! ( isStdoutWriter && isStderrWriter );

        this.writer = writer;
        this.textResource = textResource;
        this.isStdoutWriter = isStdoutWriter;
        this.isStderrWriter = isStderrWriter;
    }

    public TextResourceWriter (
        @NotNull Writer writer,
        @Nullable TextResource textResource ) {

        this ( writer, textResource, false, false );
    }

    public TextResourceWriter (
        @NotNull Path filePath,
        boolean createDirIfNotExists ) throws IOException {

        this (
            TextFileWriterUtil.createUTF8FileWriter ( filePath, createDirIfNotExists ),
            new File_TextResource ( filePath ) );
    }

    /*
    public static @NotNull TextResourceWriter forString() {
        return new TextResourceWriter ( new StringWriter(), new String_TextResource ( "" ) );
    }
     */

    public static @NotNull TextResourceWriter STDOUT_WRITER = new TextResourceWriter (
        OSIO.standardOutputUTF8Writer(),
        Stdout_TextResource.INSTANCE,
        true, false );

    public static @NotNull TextResourceWriter STDERR_WRITER = new TextResourceWriter (
        OSIO.standardErrorUTF8Writer(),
        Stderr_TextResource.INSTANCE,
        false,true );

    public static @NotNull TextResourceWriter createUTF8FileOrStdoutWriter (
        @Nullable Path filePath,
        boolean createDirIfNotExists ) throws IOException {

        if ( isStdoutFilePath ( filePath ) ) {
            return TextResourceWriter.STDOUT_WRITER;
        } else {
            return new TextResourceWriter ( filePath, createDirIfNotExists );
        }
    }

    public static boolean isStdoutFilePath ( @Nullable Path filePath ) {

        if ( filePath == null ) return true;

        String s = filePath.toString();
        return s.equals ( STDOUT_CLI_ARGUMENT_VALUE ) ||
            s.equals ( STDOUT_CLI_ARGUMENT_VALUE_ALTERNATIVE );
    }


    public @Nullable Path getResourceAsFilePath() {
        return textResource == null ? null : textResource.getResourceAsFilePath();
    }

    public boolean isStdoutOrStderrWriter() { return isStdoutWriter || isStderrWriter; }

    public void close() throws IOException {

        // don't close STDOUT and STDERR
        if ( ! isStdoutWriter && ! isStderrWriter ) {
            writer.close();
        } else {
            writer.flush();;
        }
    }

    @Override
    public String toString() { return textResource == null ? "TextResourceWriter" : textResource.toString(); }
}
