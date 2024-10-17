package dev.pp.text.resource.reader;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.StringUtils;
import dev.pp.text.inspection.InvalidTextException;
import dev.pp.text.utilities.file.TextFileReaderUtil;
import dev.pp.basics.utilities.os.OSIO;
import dev.pp.text.resource.File_TextResource;
import dev.pp.text.resource.Stdin_TextResource;
import dev.pp.text.resource.String_TextResource;
import dev.pp.text.resource.TextResource;

import java.io.*;
import java.nio.file.Path;

// public class TextResourceReader implements Closeable {
public class TextResourceReader implements AutoCloseable {


    // In a CLI application, it is common to use the hyphen (-)
    // to specify that input should be read from STDIN
    public static @NotNull String STDIN_CLI_ARGUMENT_VALUE = "-";
    public static @NotNull String STDIN_CLI_ARGUMENT_VALUE_ALTERNATIVE = "STDIN";


    private final @NotNull Reader reader;
    @NotNull public Reader getReader() { return reader; }

    private final @Nullable TextResource textResource;
    @Nullable public TextResource getTextResource () { return textResource; }

    private final boolean isStdinReader;
    public boolean isStdinReader() { return isStdinReader; }


    public TextResourceReader (
        @NotNull Reader reader,
        @Nullable TextResource textResource,
        boolean isStdinReader ) {

        this.reader = reader;
        this.textResource = textResource;
        this.isStdinReader = isStdinReader;
    }

    public TextResourceReader ( @NotNull Path filePath ) throws IOException {
        this (
            TextFileReaderUtil.createUTF8FileReader ( filePath ),
            new File_TextResource ( filePath ),
            false );
    }

    public TextResourceReader ( @NotNull String string ) {
        this (
            new StringReader ( string ),
            new String_TextResource ( string ),
            false );
    }

    public static @NotNull TextResourceReader STDIN_READER = new TextResourceReader (
        OSIO.standardInputUTF8Reader(),
        Stdin_TextResource.INSTANCE,
        true );

    public static @NotNull TextResourceReader createUTF8FileOrStdinReader ( @Nullable Path filePath ) throws IOException {

        if ( isStdinFilePath ( filePath ) ) {
            return STDIN_READER;
        } else {
            return new TextResourceReader ( filePath );
        }
    }

    public static boolean isStdinFilePath ( @Nullable Path filePath ) {

        if ( filePath == null ) return true;

        String s = filePath.toString();
        return s.equals ( STDIN_CLI_ARGUMENT_VALUE ) ||
            s.equals ( STDIN_CLI_ARGUMENT_VALUE_ALTERNATIVE );

    }


    public @NotNull String readAllAndThrowIfEmpty()
        throws IOException, InvalidTextException {

        String result = readAll();
        if ( result != null ) {
            return result;
        } else {
            String errorMessage = "Invalid empty text";
            String resourceName = resourceName();
            if ( resourceName != null ) {
                errorMessage = errorMessage + " in resource: " + resourceName;
            }
            throw new InvalidTextException (
                errorMessage, "INVALID_EMPTY_TEXT", null );
        }
    }

    public @Nullable String readAll() throws IOException {
        return StringUtils.stringFromReader ( reader );
    }

    public @Nullable Path resourceAsFilePath() {
        return textResource == null ? null : textResource.getResourceAsFilePath();
    }

    public @Nullable String resourceName() {
        return textResource == null ? null : textResource.getName();
    }

    public void close() throws IOException {

        // don't close STDIN
        if ( ! isStdinReader ) {
            reader.close ();
        }
    }

    @Override
    public String toString() { return textResource == null ? "TextResourceReader" : textResource.toString(); }
}
