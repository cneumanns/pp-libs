package dev.pp.text.REMOVEobjectwriterOLD;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.REMOVEreflection.JavaBeanUtil;
import dev.pp.basics.utilities.REMOVEreflection.JavaRecordUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

// TODO? ObjectDataWalker
// TODO? ObjectDataTraverser
// public class ObjectWriter {
public class ObjectSerializer {

    private final @NotNull ObjectSerializerEventHandler delegate;


    public ObjectSerializer ( @NotNull ObjectSerializerEventHandler delegate ) {
        this.delegate = delegate;
    }


    public void write ( @Nullable Object object ) throws IOException, ObjectWriterException {

        delegate.writeStart();
        writeObject ( object );
        delegate.writeEnd();
    }

    // public void flush() { delegate.flush(); };

    private void writeObject ( @Nullable Object object ) throws IOException, ObjectWriterException {

        // TODO byte[]

        if ( object == null ) {
            delegate.writeNull();

        } else {
            // Use 'switch' when Java >= 21 is used
            if ( object instanceof String string ) {
                delegate.writeString ( string );

            } else if ( object instanceof Number number ) {
                delegate.writeNumber ( number );

            } else if ( object instanceof Boolean aBoolean ) {
                delegate.writeBoolean ( aBoolean );

            } else if ( object instanceof Map<?,?> map ) {
                writeMap ( map );

            } else if ( object instanceof Iterable<?> iterable ) {
                writeIterator ( iterable.iterator() );

            } else if ( object instanceof Iterator<?> iterator ) {
                writeIterator ( iterator );

            } else if ( object instanceof Enum<?> anEnum ) {
                delegate.writeString ( anEnum.toString () );

            } else if ( object instanceof Record record ) {
                writeRecord ( record );

            } else {
                boolean success = writeBean ( object );
                if ( ! success ) {
                    throw new RuntimeException ( "Object '" + object + "' cannot be written. There is currently no support to write objects of type '" + object.getClass() + "'." );
                }
            }
        }
    }

    private boolean writeBean ( @NotNull Object bean ) throws IOException, ObjectWriterException {

        @Nullable Map<String,Object> fields;
        try {
            fields = JavaBeanUtil.fields ( bean );
        } catch ( IllegalAccessException | InvocationTargetException e ) {
            // TODO improve message
            throw new ObjectWriterException ( e.getMessage(), e );
        }
        if ( fields == null ) {
            return false;
        }

        writeMap ( fields );
        return true;
    }

    private void writeRecord ( @NotNull Record record ) throws IOException, ObjectWriterException {

        @NotNull Map<String,Object> fields;
        try {
            fields = JavaRecordUtil.fields ( record );
        } catch ( IllegalAccessException | InvocationTargetException e ) {
            // TODO improve message
            throw new ObjectWriterException ( e.getMessage(), e );
        }

        writeMap ( fields );
    }

    private void writeMap ( @NotNull Map<?,?> map ) throws IOException, ObjectWriterException {

        delegate.writeRecordStart();

        boolean isFirstElement = true;
        for ( Map.Entry<?,?> entry : map.entrySet() ) {
            String fieldName = entry.getKey().toString();
            Object fieldValue = entry.getValue();
            if ( ! isFirstElement ) {
                delegate.writeRecordFieldSeparator();
            }
            writeRecordField ( fieldName, fieldValue );
            isFirstElement = false;
        }

        delegate.writeRecordEnd();
    }

    private void writeRecordField (
        @NotNull String fieldName,
        @Nullable Object fieldValue ) throws IOException, ObjectWriterException {

        delegate.writeRecordFieldStart ( fieldName, fieldValue );
        delegate.writeRecordFieldValueSeparator();
        write ( fieldValue );
        delegate.writeRecordFieldEnd();
    }

    private void writeIterator ( @NotNull Iterator<?> iterator ) throws IOException, ObjectWriterException {

        delegate.writeListStart();

        boolean isFirstElement = true;
        while ( iterator.hasNext() ) {
            Object element = iterator.next();
            if ( ! isFirstElement ) {
                delegate.writeListElementSeparator();
            }
            delegate.writeListElementStart ( element );
            write ( element );
            delegate.writeListElementEnd();
            isFirstElement = false;
        }

        delegate.writeListEnd();
    }
}
