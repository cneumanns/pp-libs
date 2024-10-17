package dev.pp.text.REMOVEdatawalker.impl;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.REMOVEdatawalker.DataWalkerException;

import java.io.IOException;

// TODO?
/*
public interface ObjectConverter <I,O> {

    @Nullable O convert ( @NotNull I toConvert ) throws IOException, DataWalkerException;
}
 */

public interface ObjectConverter <I,O> {

    @Nullable Object convert ( @NotNull Object toConvert ) throws IOException, DataWalkerException;
}
