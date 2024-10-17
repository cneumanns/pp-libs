package dev.pp.text.REMOVEobjectwriterOLD;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

public class ObjectWriterException extends Exception {

    public ObjectWriterException ( @NotNull String message, @Nullable Throwable cause ) {
        super ( message, cause );
    }

    public ObjectWriterException ( @NotNull String message ) {
        super ( message );
    }
}
