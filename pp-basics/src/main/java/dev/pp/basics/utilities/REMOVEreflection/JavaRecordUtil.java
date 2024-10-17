package dev.pp.basics.utilities.REMOVEreflection;

import dev.pp.basics.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.*;

public class JavaRecordUtil {

    public static @NotNull Map<String, Object> fields ( @NotNull Record record )
        throws IllegalAccessException, InvocationTargetException {

        Map<String,Object> fields = new LinkedHashMap<>();

        RecordComponent[] recordComponents = record.getClass().getRecordComponents();
        for ( RecordComponent recordComponent : recordComponents ) {
            Method accessorMethod = recordComponent.getAccessor();
            String fieldName = accessorMethod.getName();
            Object fieldValue = accessorMethod.invoke ( record );
            fields.put ( fieldName, fieldValue );
        }

        return fields;
    }

    public static <T extends Record> @NotNull T createRecord (
        @NotNull Class<? extends Record> recordClass,
        @NotNull List<Object> fieldValues )
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        Constructor<? extends Record> constructor = canonicalConstructor ( recordClass );
        @SuppressWarnings ( "unchecked" )
        T result = (T) constructor.newInstance ( fieldValues.toArray() );
        return result;
    }

    public static boolean isRecordClass ( @NotNull Class<? extends Record> clazz ) {
        return clazz.isRecord();
    }

    private static Constructor<? extends Record> canonicalConstructor ( Class<? extends Record> clazz )
        throws NoSuchMethodException {

        RecordComponent[] recordComponents = clazz.getRecordComponents();
        Class<?>[] fieldClasses = new Class<?>[recordComponents.length];
        for ( int i = 0; i < recordComponents.length; i++ ) {
            fieldClasses[i] = recordComponents[i].getType();
        }
        return clazz.getDeclaredConstructor ( fieldClasses );
    }
}
