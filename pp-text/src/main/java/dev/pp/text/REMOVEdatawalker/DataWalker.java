package dev.pp.text.REMOVEdatawalker;

import dev.pp.basics.annotations.NotNull;

import java.io.IOException;

public interface DataWalker <T> {

    void walk ( T object, @NotNull DataWalkerEventHandler eventHandler )
        throws IOException, DataWalkerException;
}
