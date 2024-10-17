package dev.pp.basics.utilities.REMOVEreflection;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaBeanUtil {

    public static @Nullable Map<String, Object> fields ( @NotNull Object bean )
        throws IllegalAccessException, InvocationTargetException {

        List<Method> getterMethods = getterMethodsOfBean ( bean );
        if ( getterMethods == null ) return null;

        Map<String, Object> result = new HashMap<>();
        for ( Method getMethod : getterMethods ) {
            String fieldName = getterMethodNameToFieldName ( getMethod );
            Object fieldValue;
            fieldValue = getMethod.invoke ( bean );
            result.put ( fieldName, fieldValue );
        }

        return result;
    }

    public static <T> T createBean (
        @NotNull Class<T> beanClass,
        @NotNull Map<String, Object> fieldValues ) {

        // TODO
        return null;
    }


    // Getter And Setter Methods

    private static @NotNull String getterMethodNameToFieldName ( @NotNull Method getMethod ) {

        // TODO convert first character to lowercase
        String name = getMethod.getName();
        if ( name.startsWith ( "get" ) ) {
            name = name.substring ( 3 );
        } else if ( name.startsWith ( "is" ) ) {
            name = name.substring ( 2 );
        } else {
            throw new IllegalArgumentException ( "Method '" + getMethod + "' is not a getter method." );
        }

        // Make first character lowercase
        name = name.substring ( 0, 1 ).toLowerCase() + name.substring ( 1 );

        return name;
    }

    private static @Nullable List<Method> getterMethodsOfBean ( Object bean ) {
        return getterMethodsOfClass ( bean.getClass() );
    }

    private static @Nullable List<Method> getterMethodsOfClass ( Class<?> clazz ) {

        List<Method> result = new ArrayList<>();
        for ( Method method : clazz.getMethods() ) {
            if ( isGetterMethod ( method ) ) {
                result.add ( method );
            }
        }

        return result.isEmpty() ? null : result;
    }

    private static @Nullable List<Method> setterMethodsOfClass ( Class<?> clazz ) {

        List<Method> result = new ArrayList<>();
        for ( Method method : clazz.getMethods() ) {
            if ( isSetterMethod ( method ) ) {
                result.add ( method );
            }
        }

        return result.isEmpty() ? null : result;
    }

    private static boolean isGetterMethod ( @NotNull Method method ) {

        if ( isNotSetterOrGetterMethod ( method ) ) return false;
        if ( ! isGetterMethodName ( method ) ) return false;
        if ( method.getParameterCount() != 0 ) return false;
        if ( method.getReturnType().equals ( Void.class ) ) return false;
        if ( method.getName().equals ( "getClass" ) ) return false;

        return true;
    }

    private static boolean isSetterMethod ( @NotNull Method method ) {

        if ( isNotSetterOrGetterMethod ( method ) ) return false;
        if ( ! isSetterMethodName ( method ) ) return false;
        if ( method.getParameterCount() != 1 ) return false;
        if ( ! method.getReturnType().equals ( Void.class ) ) return false;

        return true;
    }

    private static boolean isNotSetterOrGetterMethod ( @NotNull Method method ) {

        int modifiers = method.getModifiers();
        if ( ! Modifier.isPublic ( modifiers ) ) return true;
        if ( Modifier.isStatic ( modifiers ) ) return true;
        if ( method.getExceptionTypes().length > 0 ) return true;
        if ( method.isSynthetic() || method.isBridge() ) return true;

        return false;
    }

    private static boolean isGetterMethodName ( @NotNull Method method ) {

        String name = method.getName();
        if ( name.startsWith ( "get" ) && name.length() > 3 ) {
            return true;
        } else if ( name.startsWith ( "is" ) && name.length() > 2 ) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isSetterMethodName ( @NotNull Method method ) {

        String name = method.getName();
        return name.startsWith ( "set" ) && name.length() > 3;
    }


    // Constructors

    private static boolean hasNoArgsConstructor ( Class<?> clazz ) {

        try {
            noArgsConstructor ( clazz );
            return true;
        } catch ( NoSuchMethodException e ) {
            return false;
        }
    }

    private static @NotNull <T> Constructor<T> noArgsConstructor ( Class<T> clazz )
        throws NoSuchMethodException {

        return clazz.getConstructor();
    }
}
