package dev.pp.text.REMOVEobjectreader;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

public class ObjectReader {

    private final @NotNull ObjectReaderDelegate delegate;


    public ObjectReader ( @NotNull ObjectReaderDelegate delegate ) {
        this.delegate = delegate;
    }


    public @Nullable <T> T readObject ( Class<T> objectClass ) {
        return null;
    }
}
