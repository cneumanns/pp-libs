package dev.pp.parameters.parameters;

import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.parameters.parameter.Parameter;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.token.TextToken;

import java.util.*;
import java.util.function.Consumer;

public abstract class MutableOrImmutableParameters<V> {


    protected @NotNull Map<String, Parameter<V>> map;

    protected final @Nullable TextToken startToken;
    public @Nullable TextToken getStartToken() { return startToken; }


    protected MutableOrImmutableParameters ( @NotNull Map<String, Parameter<V>> map, @Nullable TextToken startToken ) {
        this.map = map;
        this.startToken = startToken;
    }

    protected MutableOrImmutableParameters ( boolean maintainOrder, @Nullable TextToken startToken ) {

        if ( maintainOrder ) {
            this.map = new LinkedHashMap<>();
        } else {
            this.map = new HashMap<>();
        }

        this.startToken = startToken;
    }


    // Queries

    public boolean isEmpty() { return map.isEmpty(); }

    public boolean isNotEmpty() { return ! map.isEmpty(); }

    public long count() { return map.size(); }

    public void forEach ( Consumer<Parameter<V>> consumer ) {
        for ( Parameter<V> parameter : map.values() ) {
            consumer.accept ( parameter );
        }
    }

    public void forEachName ( Consumer<String> consumer ) {
        for ( String name : map.keySet() ) {
            consumer.accept ( name );
        }
    }

    public void forEachValue ( Consumer<V> consumer ) {
        for ( Parameter<V> parameter : map.values() ) {
            consumer.accept ( parameter.getValue() );
        }
    }

    public void forEachValueAsString ( Consumer<String> consumer ) {
        for ( Parameter<V> parameter : map.values() ) {
            consumer.accept ( parameter.valueAsNonNullString () );
        }
    }

    public @NotNull Iterator<Parameter<V>> iterator() { return map.values().iterator(); }

    public @NotNull Iterator<String> namesIterator() { return map.keySet().iterator(); }

    public @NotNull Iterator<V> valuesIterator() { return map.values().stream().map ( Parameter::getValue ).iterator(); }

    public @Nullable List<Parameter<V>> list() { return map.isEmpty() ? null : List.copyOf ( map.values() ); }

    public @Nullable Map<String, Parameter<V>> map() { return map.isEmpty() ? null : Map.copyOf ( map ); }

    public @Nullable Set<String> names() { return map.isEmpty() ? null : Set.copyOf ( map.keySet() ); }

    public @Nullable List<V> values() { return map.isEmpty() ? null : map.values().stream().map ( Parameter::getValue ).toList(); }

    /* TODO
        listSortedByName()
        listSortedBySpecOrder()
        TODO sortedNames()
     */


    // Contains

    public boolean containsName ( @NotNull String name ) {
        return map.containsKey ( name );
    }

    public boolean containsParameterName ( @NotNull Parameter<V> parameter ) {
        return containsName ( parameter.getName() );
    }

    public boolean containsSpecName ( @NotNull ParameterSpec<V> parameterSpec ) {
        return containsAnyName ( parameterSpec.allNames () );
    }

    public boolean containsAnyName ( @NotNull Collection<String> names ) {

        for ( String name : names ) {
            if ( containsName ( name ) ) return true;
        }
        return false;
    }

    public boolean containsAllNames ( @NotNull Collection<String> names ) {

        for ( String name : names ) {
            if ( ! containsName ( name ) ) return false;
        }
        return true;
    }

    public boolean containsParameterRef ( @NotNull Parameter<V> parameter ) {

        for ( Parameter<V> containedParameter : map.values() ) {
            if ( containedParameter == parameter ) return true;
        }
        return false;
    }


    // Get Parameter

    public @NotNull Parameter<V> parameter ( @NotNull String name ) {
        checkNameExists ( name );
        return map.get ( name );
    }

    public @NotNull Parameter<V> parameter ( @NotNull ParameterSpec<?> spec ) {
        return parameterForAnyName ( spec.allNames () );
    }

    public @Nullable Parameter<V> parameterOrNull ( @NotNull String name ) {
        return map.get ( name );
    }

    public @Nullable Parameter<V> parameterOrNull ( @NotNull ParameterSpec<?> spec ) {
        return parameterForAnyNameOrNull ( spec.allNames () );
    }

    public @NotNull Parameter<V> parameterOrDefault ( @NotNull String name, @NotNull Parameter<V> defaultParameter ) {

        Parameter<V> parameter = parameterOrNull ( name );
        return parameter != null ? parameter : defaultParameter;
    }

    public @NotNull Parameter<V> parameterOrDefault ( @NotNull ParameterSpec<?> spec, @NotNull Parameter<V> defaultParameter ) {

        Parameter<V> parameter = parameterOrNull ( spec );
        return parameter != null ? parameter : defaultParameter;
    }

    public @NotNull Parameter<V> parameterForAnyName ( @NotNull Set<String> names ) {

        Parameter<V> parameter = parameterForAnyNameOrNull ( names );

        if ( parameter == null ) {
            throw new IllegalArgumentException (
                "A parameter for any of the following names does not exist in the list: " + names + "." );
        } else {
            return parameter;
        }
    }

    public @Nullable Parameter<V> parameterForAnyNameOrNull ( @NotNull Set<String> names ) {

        for ( String name : names ) {
            if ( containsName ( name ) ) return map.get ( name );
        }
        return null;
    }


    // Get Nullable Value

    public @Nullable V value ( @NotNull String name ) {
        return parameter ( name ).getValue();
    }

    public @Nullable V value ( @NotNull ParameterSpec<V> spec ) {
        return parameter ( spec ).getValue();
    }

    public @Nullable V valueForAnyName ( @NotNull Set<String> names ) {
        return parameterForAnyName ( names ).getValue();
    }

    @SuppressWarnings ( "unchecked" )
    // not ok for Parameters<?>
    // public @Nullable <T extends V> T castedValue ( @NotNull String name ) {
    public @Nullable <T> T castedValue ( @NotNull String name ) {
        return (T) value ( name );
    }

    @SuppressWarnings ( "unchecked" )
    // public @Nullable <T extends V> T castedValue ( @NotNull ParameterSpec<?> spec ) {
    public @Nullable <T> T castedValue ( @NotNull ParameterSpec<?> spec ) {
        return (T) valueForAnyName ( spec.allNames () );
    }

    public @Nullable V valueOrDefault ( @NotNull String name, @Nullable V defaultValue ) {
        return containsName ( name ) ? value ( name ) : defaultValue;
    }

    public @Nullable V valueOrDefault ( @NotNull ParameterSpec<V> spec, @Nullable V defaultValue ) {

        Parameter<V> parameter = parameterForAnyNameOrNull ( spec.allNames () );
        return parameter != null ? parameter.getValue() : defaultValue;
    }

    @SuppressWarnings ( "unchecked" )
    /*
    public @Nullable <T extends V> T castedValueOrDefault ( @NotNull String name, @Nullable T defaultValue ) {
        return (T) valueOrDefault ( name, defaultValue );
    }
     */
    public @Nullable <T> T castedValueOrDefault ( @NotNull String name, @Nullable T defaultValue ) {

        Parameter<V> parameter = parameterOrNull ( name );
        return parameter != null ? (T) parameter.getValue() : defaultValue;
    }

    @SuppressWarnings ( "unchecked" )
    public @Nullable <T> T castedValueOrDefault ( @NotNull ParameterSpec<?> spec, @Nullable T defaultValue ) {

        Parameter<V> parameter = parameterForAnyNameOrNull ( spec.allNames () );
        return parameter != null ? (T) parameter.getValue() : defaultValue;
    }


    // Get Non-Null Value

    public @NotNull V nonNullValue ( @NotNull String name ) {
        return checkNotNull ( name, value ( name ) );
    }

    public @NotNull V nonNullValue ( @NotNull ParameterSpec<V> spec ) {
        return checkNotNull ( spec, value ( spec ) );
    }

    public @NotNull <T> T nonNullCastedValue ( @NotNull String name ) {
        return checkNotNull ( name, castedValue ( name ) );
    }

    public @NotNull <T> T nonNullCastedValue ( @NotNull ParameterSpec<?> spec ) {
        return checkNotNull ( spec, castedValue ( spec ) );
    }

    public @NotNull V nonNullValueOrDefault ( @NotNull String name, @NotNull V defaultValue ) {
        return checkNotNull ( name, valueOrDefault ( name, defaultValue ) );
    }

    public @NotNull V nonNullValueOrDefault ( @NotNull ParameterSpec<V> spec, @NotNull V defaultValue ) {
        return checkNotNull ( spec, valueOrDefault ( spec, defaultValue ) );
    }

    public @NotNull <T> T nonNullCastedValueOrDefault ( @NotNull String name, @NotNull T defaultValue ) {
        return checkNotNull ( name, castedValueOrDefault ( name, defaultValue ) );
    }

    public @NotNull <T> T nonNullCastedValueOrDefault ( @NotNull ParameterSpec<?> spec, @NotNull T defaultValue ) {
        return checkNotNull ( spec, castedValueOrDefault ( spec, defaultValue ) );
    }


    public @NotNull ParameterValueGetter valueGetter() { return new ParameterValueGetter ( this ); }


    // Get Tokens

    public @Nullable TextToken nameToken ( @NotNull String name ) {
        return parameter ( name ).nameToken();
    }

    public @Nullable TextToken valueToken ( @NotNull String name ) {
        return parameter ( name ).valueToken();
    }


    // Conversions

    public @NotNull Map<String, V> toMap() {

        // Map<String, V> result = new HashMap<>();
        Map<String, V> result = new LinkedHashMap<>();
        for ( Parameter<V> parameter : map.values() ) {
            result.put ( parameter.getName(), parameter.getValue() );
        }
        return result;
    }

    public @NotNull Map<String, String> toStringMap() {

        // Map<String, String> result = new HashMap<>();
        Map<String, String> result = new LinkedHashMap<>();
        for ( Parameter<V> parameter : map.values() ) {
            result.put ( parameter.getName(), parameter.valueAsNonNullString () );
        }
        return result;
    }

    public @Nullable Parameters<String> toStringParameters() {

        MutableParameters<String> result = new MutableParameters<>();
        for ( Parameter<V> parameter : map.values() ) {
            result.add ( parameter.getName(), parameter.valueAsNonNullString (),
            null,
            parameter.getNameLocation(), parameter.getValueLocation() );
        }
        return result.makeImmutableOrNull();
    }

    @Override
    public String toString() { return count() + " parameter(s)"; }

    public String toLongString() {

        Collection<Parameter<V>> parameters = map.values();
        if ( parameters.isEmpty() ) return "No parameters";

        StringBuilder sb = new StringBuilder();
        for ( Parameter<V> parameter : parameters ) {
            sb.append ( parameter.getName() );
            sb.append ( " = " );
            sb.append ( parameter.valueAsNonNullString () );
            sb.append ( StringConstants.OS_LINE_BREAK );
        }
        return sb.toString();
    }


    // Private helpers

    protected void checkNameExists ( @NotNull String name ) {
        if ( ! containsName ( name ) )
            throw new IllegalArgumentException ( "Parameter '" + name + "' does not exist in the list." );
    }

    protected void checkNameDoesNotExist ( @NotNull String name ) {
        if ( containsName ( name ) )
            throw new IllegalArgumentException ( "Parameter '" + name + "' exists already." );
    }

    private @NotNull <T> T checkNotNull ( @NotNull String name, @Nullable T value ) {

        if ( value == null )
            throw new IllegalArgumentException ( "'" + name + "' is null, but supposed to be non-null.");
        return value;
    }

    private @NotNull <T> T checkNotNull ( @NotNull ParameterSpec<?> spec, @Nullable T value ) {
        return checkNotNull ( spec.getName(), value );
    }
}
