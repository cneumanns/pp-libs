package dev.pp.text.REMOVEdatawalker.impl;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.util.Map;

public record ObjectDataWalkerConfig (
    // @NotNull DataWalker<Map<String,Object>> recordDataWalker,
    // @NotNull DataWalker<Iterable<Object>> iterableDataWalker,
    @Nullable Map<Class<?>, ObjectConverter<?,?>> objectConverters
    ) {

    // public static @NotNull DataWalker<Map<String,Object>> DEFAULT_RECORD_DATA_WALKER = new RecordDataWalker<>();
    // public static @NotNull DataWalker<Iterable<Object>> DEFAULT_ITERABLE_DATA_WALKER = new IterableDataWalker<>();
    public static @Nullable Map<Class<?>, ObjectConverter<?,?>> DEFAULT_OBJECT_CONVERTERS = null;
    public static @NotNull ObjectDataWalkerConfig DEFAULT_CONFIG = new ObjectDataWalkerConfig (
        // DEFAULT_RECORD_DATA_WALKER, DEFAULT_ITERABLE_DATA_WALKER, DEFAULT_OBJECT_CONVERTERS );
        DEFAULT_OBJECT_CONVERTERS );


/*
    public ObjectDataWalkerConfig ( @Nullable Map<Class<?>, ObjectConverter<?,?>> objectConverters ) {
        this ( DEFAULT_RECORD_DATA_WALKER, DEFAULT_ITERABLE_DATA_WALKER, objectConverters );
    }

 */
}
