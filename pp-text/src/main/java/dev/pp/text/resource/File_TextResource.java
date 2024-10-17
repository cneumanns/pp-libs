package dev.pp.text.resource;

import java.io.IOException;
import java.nio.file.Path;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.file.FilePathUtils;
import dev.pp.text.utilities.file.TextFileUtils;

public class File_TextResource implements TextResource {

    private final @NotNull Path filePath;
    public @NotNull Path getPath() { return filePath; }


    public File_TextResource ( @NotNull Path filePath ) {
        this.filePath = filePath;
    }


    public @NotNull Path getResource() { return filePath; }

    public @NotNull String getName() { return FilePathUtils.makeAbsoluteAndNormalize ( filePath ).toString(); }

    public @NotNull String getTextLine ( long lineNumber ) throws IOException {
        return TextFileUtils.getNthLineInFile ( filePath, lineNumber );
    }

    @Override
    public String toString() { return "file " + getName(); }
}
