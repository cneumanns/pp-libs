package dev.pp.parameters.parameters;

import dev.pp.parameters.parameter.Parameter;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.text.location.TextLocation;
import dev.pp.text.token.TextToken;

import java.util.*;

public class MutableParameters<V> extends MutableOrImmutableParameters<V> {

    private boolean maintainOrder;

    public MutableParameters ( boolean maintainOrder, @Nullable TextToken startToken ) {
        super ( maintainOrder, startToken );
        this.maintainOrder = maintainOrder;
    }

    public MutableParameters ( @Nullable TextToken startToken ) {
        this ( true, startToken );
    }

    public MutableParameters() {
        this ( true, null );
    }


    // Add

    public MutableParameters<V> add ( @NotNull Parameter<V> parameter ) {
        String name = parameter.getName();
        checkNameDoesNotExist ( name );
        map.put ( name, parameter );

        return this;
    }

    public MutableParameters<V> add (
        @NotNull String name,
        @Nullable V value,
        @Nullable ParameterSpec<V> spec,
        @Nullable TextLocation nameLocation,
        @Nullable TextLocation valueLocation ) {

        add ( new Parameter<> ( name, value, spec, nameLocation, valueLocation ) );

        return this;
    }

    public MutableParameters<V> add ( @NotNull String name, @Nullable V value ) {
        add ( new Parameter<> ( name, value ) );

        return this;
    }

    public MutableParameters<V> addAll ( @NotNull Parameters<V> parameters ) {
        parameters.forEach ( this::add );

        return this;
    }

    public MutableParameters<V> addOrReplace ( @NotNull Parameter<V> parameter ) {
        map.put ( parameter.getName(), parameter );

        return this;
    }

    public MutableParameters<V> addOrReplace ( @NotNull String name, @Nullable V value ) {
        addOrReplace ( new Parameter<> ( name, value ) );

        return this;
    }


    // Remove

    public MutableParameters<V> remove ( @NotNull String name ) {
        checkNameExists ( name );
        map.remove ( name );

        return this;
    }

    public boolean removeIfExists ( @NotNull String name ) {

        if ( containsName ( name ) ) {
            map.remove ( name );
            return true;
        } else {
            return false;
        }
    }

    public MutableParameters<V> removeAll() {
        map.clear();

        return this;
    }


    // Replace

    public MutableParameters<V> replace ( @NotNull String name, @NotNull Parameter<V> parameter ) {
        checkNameExists ( name );
        if ( ! name.equals ( parameter.getName() ) )
            throw new IllegalArgumentException ( "The parameter name and the name to replace are different ('" + parameter.getName() + "' # '" + name + "').");
        map.put ( name, parameter );

        return this;
    }

    /* TODO
    public void replace ( @NotNull String name, @Nullable V value )
    public void replaceIfExists ( @NotNull String name, @NotNull Parameter<V> parameter )
    public void replaceIfExists ( @NotNull String name, @Nullable V value )
    public void replaceAll ( @Nullable V value )
     */


    // Conversions

    public @Nullable Parameters<V> makeImmutableOrNull() {

        if ( isEmpty() ) return null;

        map = Collections.unmodifiableMap ( map );
        return new Parameters<> ( map, startToken );
    }

    public @NotNull Parameters<V> makeImmutable() {

        @Nullable Parameters<V> parameters = makeImmutableOrNull();
        return checkNotNull ( parameters );
    }

    public @Nullable Parameters<V> copyToImmutableOrNull() {

        if ( isEmpty() ) return null;

        return new Parameters<> ( Map.copyOf ( map ), startToken );
    }

    public @NotNull Parameters<V> copyToImmutable() {

        @Nullable Parameters<V> parameters = copyToImmutableOrNull();
        return checkNotNull ( parameters );
    }

    private @NotNull Parameters<V> checkNotNull ( @Nullable Parameters<V> parameters ) {

        if ( parameters == null ) {
            throw new IllegalArgumentException ( "There are no parameters." );
        } else {
            return parameters;
        }
    }
}
