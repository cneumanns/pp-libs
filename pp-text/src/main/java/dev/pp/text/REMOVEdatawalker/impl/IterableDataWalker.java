package dev.pp.text.REMOVEdatawalker.impl;

import dev.pp.basics.annotations.NotNull;
import dev.pp.text.REMOVEdatawalker.DataWalker;
import dev.pp.text.REMOVEdatawalker.DataWalkerEventHandler;
import dev.pp.text.REMOVEdatawalker.DataWalkerException;

import java.io.IOException;
import java.util.Iterator;

public class IterableDataWalker <E> implements DataWalker<Iterable<? extends E>> {

    private final @NotNull DataWalker<? super E> elementDataWalker;


    public IterableDataWalker ( @NotNull DataWalker<? super E> elementDataWalker ) {
        this.elementDataWalker = elementDataWalker;
    }


    public void walk ( @NotNull Iterable<? extends E> iterable, @NotNull DataWalkerEventHandler eventHandler )
        throws IOException, DataWalkerException {

        eventHandler.onIterableStart();

        Iterator<? extends E> iterator = iterable.iterator();
        boolean isFirstElement = true;
        while ( iterator.hasNext() ) {
            E element = iterator.next();
            if ( ! isFirstElement ) {
                eventHandler.onBetweenIterableElements();
            }
            eventHandler.onIterableElementStart ( element );
            elementDataWalker.walk ( element, eventHandler );
            eventHandler.onIterableElementEnd();
            isFirstElement = false;
        }

        eventHandler.onIterableEnd();
    }

    public void walkIterator ( @NotNull Iterator<? extends E> iterator, @NotNull DataWalkerEventHandler eventHandler )
        throws IOException, DataWalkerException {

        @SuppressWarnings ( "unchecked" )
        Iterator<E> iterator_ = (Iterator<E>) iterator;
        walk ( () -> iterator_, eventHandler );
    }
}
