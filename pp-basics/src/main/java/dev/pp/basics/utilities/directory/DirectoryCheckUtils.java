package dev.pp.basics.utilities.directory;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.file.FilePathUtils;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirectoryCheckUtils {

    public static void checkIsExistingDirectory ( @NotNull Path directoryPath ) throws FileNotFoundException {

        if ( ! Files.isDirectory ( directoryPath ) ) throw new FileNotFoundException (
            "Directory '" + DirectoryPathUtils.makeAbsoluteAndNormalize ( directoryPath ) + "' does not exist." );
    }
}
