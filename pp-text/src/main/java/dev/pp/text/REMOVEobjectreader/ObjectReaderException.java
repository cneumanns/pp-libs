package dev.pp.text.REMOVEobjectreader;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

public class ObjectReaderException extends Exception {

    public ObjectReaderException ( @NotNull String message, @Nullable Throwable cause ) {
        super ( message, cause );
    }

    public ObjectReaderException ( @NotNull String message ) {
        super ( message );
    }
}
