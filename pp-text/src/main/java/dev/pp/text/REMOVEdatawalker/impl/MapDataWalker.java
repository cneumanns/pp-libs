package dev.pp.text.REMOVEdatawalker.impl;

import dev.pp.basics.annotations.NotNull;
import dev.pp.text.REMOVEdatawalker.DataWalker;
import dev.pp.text.REMOVEdatawalker.DataWalkerEventHandler;
import dev.pp.text.REMOVEdatawalker.DataWalkerException;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

public class MapDataWalker<K,V> implements DataWalker<Map<? extends K, ? extends V>> {


    public static final @NotNull Function<Object,String> DEFAULT_KEY_TO_STRING_CONVERTER = Object::toString;


    private final @NotNull DataWalker<? super V> valueDataWalker;
    private final @NotNull Function<? super K, String> keyToStringConverter;


    public MapDataWalker (
        @NotNull DataWalker<? super V> valueDataWalker,
        @NotNull Function<? super K, String> keyToStringConverter ) {

        this.valueDataWalker = valueDataWalker;
        this.keyToStringConverter = keyToStringConverter;
    }

    public MapDataWalker ( @NotNull DataWalker<? super V> valueDataWalker ) {
        this ( valueDataWalker, DEFAULT_KEY_TO_STRING_CONVERTER );
    }


    public void walk (
        @NotNull Map<? extends K, ? extends V> map,
        @NotNull DataWalkerEventHandler eventHandler )
        throws IOException, DataWalkerException {

        eventHandler.onRecordStart();

        boolean isFirstElement = true;
        for ( Map.Entry<? extends K, ? extends V> entry : map.entrySet() ) {
            if ( ! isFirstElement ) {
                eventHandler.onBetweenRecordFields();
            }
            walkEntry ( entry, eventHandler );
            isFirstElement = false;
        }

        eventHandler.onRecordEnd();
    }

    private void walkEntry (
        @NotNull Map.Entry<? extends K, ? extends V> entry,
        @NotNull DataWalkerEventHandler eventHandler ) throws IOException, DataWalkerException {

        String keyAsString = keyToStringConverter.apply ( entry.getKey() );
        V value = entry.getValue();

        eventHandler.onRecordFieldStart ( keyAsString, value );
        eventHandler.onBetweenNameValueOfRecordField();
        valueDataWalker.walk ( value, eventHandler );
        eventHandler.onRecordFieldEnd();
    }
}
