package dev.pp.text.REMOVEdatawalker.impl;

import dev.pp.text.REMOVEdatawalker.DataWalkerEventHandler;

public interface DataWalkerCustomizer { // <T> {

    // @Nullable T toDataWalkerObject();

    // @NotNull DataWalker<T> dataWalker();
    void walkData ( DataWalkerEventHandler eventHandler );
}
