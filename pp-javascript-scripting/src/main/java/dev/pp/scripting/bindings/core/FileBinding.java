package dev.pp.scripting.bindings.core;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.utilities.file.TextFileWriterUtil;
import dev.pp.scripting.bindings.ScriptingBinding;
import dev.pp.text.utilities.file.TextFileReaderUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Functions to work with files.
 * Text files are supposed to be encoded in UTF8
 */

public class FileBinding implements ScriptingBinding {

    public FileBinding() {}


    public @NotNull String bindingName () { return "fileUtils"; }

    /**
     * Write UTF8-encoded text into a file.
     *
     * If the file exists already, it is overwritten.
     * If the file doesn't exist yet, it is created.
     * @param path The absolute or relative-to-the-working-directory path of the file.
     * @param text The text to be stored in the file.
     * @throws IOException Thrown if an error occurs while writing to the file.
     */
    public void writeText ( @NotNull String path, @NotNull String text ) throws IOException {

        TextFileWriterUtil.writeTextToUTF8File ( text, Path.of ( path ), true );
    }

    /**
     * Read the text stored in a UTF8-encoded text file.
     * @param path The absolute or relative-to-the-working-directory path of the file to read.
     * @return The text stored in the file, or <code>null</code> if the file is empty.
     * @throws IOException Thrown if an error occurs while reading the file (e.g. file does not exist).
     */
    public @Nullable String readText ( @NotNull String path ) throws IOException {

        return TextFileReaderUtil.readTextFromUTF8File ( Path.of ( path ) );
    }

    /**
     * Read the lines stored in a UTF8-encoded text file.
     * @param path The absolute or relative-to-the-working-directory path of the file to read.
     * @return The list of lines stored in the text file, or <code>null</code> if the file is empty.
     * Empty lines are represented as empty strings in the list.
     * @throws IOException Thrown if an error occurs while reading the file (e.g. file does not exist).
     */
    public @Nullable List<String> readLines ( @NotNull String path ) throws IOException {

        return TextFileReaderUtil.readTextLinesFromUTF8File ( Path.of ( path ) );
    }

    /**
     * Check if a file exists
     * @param path The absolute or relative-to-the-working-directory path of the file to check.
     * @return 'true' if the file exists, 'false' if the file does not exist.
     * @throws IOException Thrown if an error occurs while acessing the file.
     */
    public boolean exists ( @NotNull String path ) throws IOException {

        return new File ( path ).exists();
    }

    /* TODO
        appendText
        delete

        workingDir
        setWorkingDir
     */
}
