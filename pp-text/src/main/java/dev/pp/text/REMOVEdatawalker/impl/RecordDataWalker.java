package dev.pp.text.REMOVEdatawalker.impl;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.REMOVEreflection.JavaBeanUtil;
import dev.pp.basics.utilities.REMOVEreflection.JavaRecordUtil;
import dev.pp.text.REMOVEdatawalker.DataWalker;
import dev.pp.text.REMOVEdatawalker.DataWalkerEventHandler;
import dev.pp.text.REMOVEdatawalker.DataWalkerException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class RecordDataWalker implements DataWalker<Record> {


    private final @NotNull DataWalker<Object> fieldValueDataWalker;


    public RecordDataWalker ( @NotNull DataWalker<Object> fieldValueDataWalker ) {
        this.fieldValueDataWalker = fieldValueDataWalker;
    }


    public void walk (
        @NotNull Record record,
        @NotNull DataWalkerEventHandler eventHandler ) throws IOException, DataWalkerException {

        @NotNull Map<String,Object> fields;
        try {
            fields = JavaRecordUtil.fields ( record );
        } catch ( IllegalAccessException | InvocationTargetException e ) {
            // TODO improve message
            throw new DataWalkerException ( e.getMessage(), e );
        }

        walkFields ( fields, eventHandler );
    }

    public boolean walkBean (
        @NotNull Object bean,
        @NotNull DataWalkerEventHandler eventHandler ) throws IOException, DataWalkerException {

        @Nullable Map<String,Object> fields;
        try {
            fields = JavaBeanUtil.fields ( bean );
        } catch ( IllegalAccessException | InvocationTargetException e ) {
            // TODO improve message
            throw new DataWalkerException ( e.getMessage(), e );
        }
        if ( fields == null ) {
            return false;
        }

        walkFields ( fields, eventHandler );
        return true;
    }


    private void walkFields (
        @NotNull Map<String, ?> fields,
        @NotNull DataWalkerEventHandler eventHandler )
        throws IOException, DataWalkerException {

        eventHandler.onRecordStart();

        boolean isFirstElement = true;
        for ( Map.Entry<String, ?> entry : fields.entrySet() ) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            if ( ! isFirstElement ) {
                eventHandler.onBetweenRecordFields();
            }
            walkField ( fieldName, fieldValue, eventHandler );
            isFirstElement = false;
        }

        eventHandler.onRecordEnd();
    }

    private void walkField (
        @NotNull String fieldName,
        @Nullable Object fieldValue,
        @NotNull DataWalkerEventHandler eventHandler ) throws IOException, DataWalkerException {

        eventHandler.onRecordFieldStart ( fieldName, fieldValue );
        eventHandler.onBetweenNameValueOfRecordField();
        fieldValueDataWalker.walk ( fieldValue, eventHandler );
        eventHandler.onRecordFieldEnd();
    }
}
