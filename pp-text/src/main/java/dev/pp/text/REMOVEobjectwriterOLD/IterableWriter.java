package dev.pp.text.REMOVEobjectwriterOLD;

import dev.pp.basics.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;

@Deprecated
public class IterableWriter <E> implements GenericObjectWriter<Iterable<E>> {

    public void write ( @NotNull Iterable<E> iterable, @NotNull Writer writer ) throws IOException {
    }
}
