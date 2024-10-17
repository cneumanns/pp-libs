package dev.pp.text.REMOVEdatawalker.impl;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.REMOVEdatawalker.DataWalker;
import dev.pp.text.REMOVEdatawalker.DataWalkerEventHandler;
import dev.pp.text.REMOVEdatawalker.DataWalkerException;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class ObjectDataWalker implements DataWalker<Object> {

    // private final @NotNull ObjectDataWalkerConfig config;
    private final @NotNull RecordDataWalker recordDataWalker;
    private final @NotNull IterableDataWalker<Object> iterableDataWalker;
    private final @NotNull MapDataWalker<Object,Object> mapDataWalker;
    private final @Nullable Map<Class<?>, ObjectConverter<?,?>> objectConverters;


    public ObjectDataWalker ( @NotNull ObjectDataWalkerConfig config ) {
        // this.config = config;
        this.objectConverters = config.objectConverters();

        this.recordDataWalker = new RecordDataWalker ( this );
        this.iterableDataWalker = new IterableDataWalker<>( this );
        this.mapDataWalker = new MapDataWalker<> ( this );
    }

    public ObjectDataWalker() {
        this ( ObjectDataWalkerConfig.DEFAULT_CONFIG );
    }


    public void walk (
        @Nullable Object object,
        @NotNull DataWalkerEventHandler eventHandler )
        throws IOException, DataWalkerException {

        // TODO is this needed?
        // eventHandler.onStart();
        walkObject ( object, eventHandler );
        // TODO is this needed?
        // eventHandler.onEnd();
    }

    /* TODO register/unregister specific walkers for specific classes (customization)
    public <T> void registerWalker ( DataWalker<T> dataWalker, Class<?> forClass ) {
    }

    public void unRegisterWalker ( Class<?> forClass ) {
    }
     */

    private void walkObject (
        @Nullable Object object,
        @NotNull DataWalkerEventHandler eventHandler ) throws IOException, DataWalkerException {

        // TODO byte[]
        // TODO consider registered walkers
        // TODO consider GenericData

        if ( object instanceof DataWalkerCustomizer customizer ) {
            // object = customizer.toDataWalkerObject();
            // customizer.dataWalker().walk ( object );
            customizer.walkData ( eventHandler );
        }

        object = convertObject ( object );

        if ( object == null ) {
            eventHandler.onNull();
            return;
        }

        // Use 'switch' when Java >= 21 is used

        /* TODO
        if ( object instanceof DataWalker dataWalker ) {
            dataWalker.walk ( object, eventHandler );

        } else if ( object instanceof String string ) {
         */
        if ( object instanceof String string ) {
            eventHandler.onString ( string );

        } else if ( object instanceof Number number ) {
            eventHandler.onNumber ( number );

        } else if ( object instanceof Boolean aBoolean ) {
            eventHandler.onBoolean ( aBoolean );

        } else if ( object instanceof Map<?,?> map ) {
            mapDataWalker.walk ( map, eventHandler );

        } else if ( object instanceof Iterable<?> iterable ) {
            // walkIterator ( iterable.iterator(), eventHandler );
            iterableDataWalker.walk ( iterable, eventHandler );

        } else if ( object instanceof Iterator<?> iterator ) {
            iterableDataWalker.walkIterator ( iterator, eventHandler );

        } else if ( object instanceof Enum<?> anEnum ) {
            eventHandler.onString ( anEnum.toString () );

        } else if ( object instanceof Record record ) {
            recordDataWalker.walk ( record, eventHandler );

        } else {
            boolean success = recordDataWalker.walkBean ( object, eventHandler );
            if ( ! success ) {
                throw new RuntimeException ( "Object '" + object + "' cannot be walked. There is currently no support for walking objects of type '" + object.getClass() + "'." );
            }
        }
    }

    private @Nullable Object convertObject ( @Nullable Object object )
        throws IOException, DataWalkerException {

        if ( object == null ) return null;
        if ( objectConverters == null ) return object;
        ObjectConverter<?, ?> objectConverter = objectConverters.get ( object.getClass () );
        if ( objectConverter == null ) return object;

        return objectConverter.convert ( object );
    }
}
