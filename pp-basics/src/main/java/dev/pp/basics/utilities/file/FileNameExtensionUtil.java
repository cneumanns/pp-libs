package dev.pp.basics.utilities.file;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.nio.file.Path;

public class FileNameExtensionUtil {

    public static char NAME_EXTENSION_SEPARATOR = '.';
    // public static @NotNull String NAME_EXTENSION_SEPARATOR_STRING = ".";

    public static boolean hasExtension (
        @NotNull Path filePath,
        @Nullable String extension,
        boolean ignoreCase ) {

        String realExtension = getExtension ( filePath );
        if ( extension != null && realExtension != null ) {
            if ( ignoreCase ) {
                return realExtension.equalsIgnoreCase ( extension );
            } else {
                return realExtension.equals ( extension );
            }
        } else if ( extension == null && realExtension == null ) {
            return true;
        } else {
            return false;
        }
    }

    public static @Nullable String getExtension ( @NotNull Path filePath ) {
        return getExtension ( filePath.getFileName().toString() );
    }

    public static @Nullable String getExtension ( @NotNull String fileName ) {

        int separatorIndex = getSeparatorIndex ( fileName );
        return separatorIndex > 0 ? fileName.substring ( separatorIndex + 1 ) : null;
    }

    public static @NotNull String changeExtension ( @NotNull String fileName, @NotNull String newExtension ) {

        int separatorIndex = getSeparatorIndex ( fileName );
        if ( separatorIndex > 0 ) {
            return fileName.substring ( 0, separatorIndex ) + NAME_EXTENSION_SEPARATOR + newExtension;
        } else {
            return fileName + NAME_EXTENSION_SEPARATOR + newExtension;
        }
    }

    public static int getSeparatorIndex ( @NotNull String fileName ) {

        return fileName.lastIndexOf ( NAME_EXTENSION_SEPARATOR );
    }
}
