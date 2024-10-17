package dev.pp.parameters.parameterspecs;

import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameters.Parameters;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.datatype.utils.validator.DataValidator;

import java.util.*;

public class MutableParameterSpecs<V> extends MutableOrImmutableParameterSpecs<V> {

    public MutableParameterSpecs ( @Nullable DataValidator<Parameters<V>> validator ) {
        super ( validator );
    }

    public MutableParameterSpecs () {
        this ( null );
    }


    public void setValidator ( @Nullable DataValidator<Parameters<V>> validator ) {
        this.validator = validator;
    }

    public MutableParameterSpecs<V> add ( @NotNull ParameterSpec<V> parameterSpec ) {

        // name must be unique
        for ( String name : parameterSpec.allNames () ) {
            checkNotExists ( name );
            allNamesMap.put ( name, parameterSpec );
        }

        // positional index must be unique
        @Nullable Integer positionalIndex = parameterSpec.getPosition ();
        if ( positionalIndex != null) {
            for ( ParameterSpec<V> existingParameterSpec : list ) {
                @Nullable Integer existingPositionalIndex = existingParameterSpec.getPosition ();
                if ( existingPositionalIndex != null &&
                    existingPositionalIndex.intValue() == positionalIndex.intValue() ) {
                    throw new IllegalArgumentException (
                        "Positional index '" + positionalIndex + "' exists already for parameter '" + existingParameterSpec + "'." );
                }
            }
        }

        list.add ( parameterSpec );

        return this;
    }

    public MutableParameterSpecs<V> addAll ( @NotNull ParameterSpecs<V> parameterSpecs ) {

        for ( ParameterSpec<V> parameterSpec : parameterSpecs.list () ) {
            add ( parameterSpec );
        }

        return this;
    }

    // Conversions

    public @Nullable ParameterSpecs<V> makeImmutableOrNull() {

        if ( list.isEmpty() ) return null;

        list = Collections.unmodifiableList ( list );
        allNamesMap = Collections.unmodifiableMap ( allNamesMap );
        return new ParameterSpecs<>( list, allNamesMap, validator );
    }

    public @NotNull ParameterSpecs<V> makeImmutable() {

        @Nullable ParameterSpecs<V> parameterSpecs = makeImmutableOrNull();
        return checkNotNull ( parameterSpecs );
    }

    public @Nullable ParameterSpecs<V> copyToImmutableOrNull() {

        if ( list.isEmpty () ) return null;

        List<ParameterSpec<V>> listCopy = List.copyOf ( list );
        Map<String, ParameterSpec<V>> allNamesMapCopy = Map.copyOf ( allNamesMap );
        return new ParameterSpecs<>( listCopy, allNamesMapCopy, validator );
    }

    public @Nullable ParameterSpecs<V> copyToImmutable() {

        @Nullable ParameterSpecs<V> parameterSpecs = copyToImmutableOrNull();
        return checkNotNull ( parameterSpecs );
    }


    private @NotNull ParameterSpecs<V> checkNotNull ( @Nullable ParameterSpecs<V> parameterSpecs ) {

        if ( parameterSpecs == null ) {
            throw new IllegalArgumentException ( "There are no parameter specs." );
        } else {
            return parameterSpecs;
        }
    }
}
