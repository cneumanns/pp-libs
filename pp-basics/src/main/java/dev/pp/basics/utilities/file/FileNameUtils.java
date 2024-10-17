package dev.pp.basics.utilities.file;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.nio.file.Path;

public class FileNameUtils {

    public static @NotNull String getNameAsString ( @NotNull Path filePath ) {
        return filePath.getFileName().toString();
    }

    public static @Nullable String getNameWithoutExtensionAsString ( @NotNull Path filePath ) {

        String name = getNameAsString ( filePath );
        int separatorIndex = FileNameExtensionUtil.getSeparatorIndex ( name );
        return switch ( separatorIndex ) {
            case -1 -> name;
            case 0 -> null;
            default -> name.substring ( 0, separatorIndex );
        };
    }
}
