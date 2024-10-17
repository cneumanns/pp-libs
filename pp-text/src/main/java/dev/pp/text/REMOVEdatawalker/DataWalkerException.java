package dev.pp.text.REMOVEdatawalker;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

public class DataWalkerException extends Exception {

    public DataWalkerException ( @NotNull String message, @Nullable Throwable cause ) {
        super ( message, cause );
    }

    public DataWalkerException ( @NotNull String message ) {
        super ( message );
    }
}
