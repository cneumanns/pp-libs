package dev.pp.parameters.parameters;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameter.Parameter;
import dev.pp.parameters.parameterspec.ParameterSpec;

public class ParameterValueGetter {

    private final @NotNull MutableOrImmutableParameters<?> parameters;

    public @NotNull MutableOrImmutableParameters<?> getParameters () { return parameters; }


    public ParameterValueGetter ( @NotNull MutableOrImmutableParameters<?> parameters ) {
        this.parameters = parameters;
    }


    // Nullable

    @SuppressWarnings ( "unchecked" )
    public <T> @Nullable T nullable ( @NotNull String name ) {
        return (T) parameters.value ( name );
    }

    @SuppressWarnings ( "unchecked" )
    public <T> @Nullable T nullable ( @NotNull ParameterSpec<T> parameterSpec ) {
        Parameter<?> parameter = parameters.parameterForAnyNameOrNull ( parameterSpec.allNames () );
        return parameter == null ? null : (T) parameter.getValue();
    }

    public <T> @Nullable T nullableOrDefault ( @NotNull String name, @Nullable T defaultValue ) {

        if ( parameters.containsName ( name ) ) {
            return nullable ( name );
        } else {
            return defaultValue;
        }
    }

    @SuppressWarnings ( "unchecked" )
    public <T> @Nullable T nullableOrDefault ( @NotNull ParameterSpec<T> parameterSpec, @Nullable T defaultValue ) {
        Parameter<?> parameter = parameters.parameterForAnyNameOrNull ( parameterSpec.allNames () );
        return parameter == null ? defaultValue : (T) parameter.getValue();
    }


    // Not Null

    public <T> @NotNull T nonNull ( @NotNull String name ) {
        return checkNotNull ( name, nullable ( name ) );
    }

    public <T> @NotNull T nonNull ( @NotNull ParameterSpec<T> parameterSpec ) {
        return checkNotNull ( parameterSpec, nullable ( parameterSpec ) );
    }

    public <T> @NotNull T nonNullOrDefault ( @NotNull String name, @NotNull T defaultValue ) {
        return checkNotNull ( name, nullableOrDefault ( name, defaultValue ) );
    }

    public <T> @NotNull T nonNullOrDefault ( @NotNull ParameterSpec<T> parameterSpec, @NotNull T defaultValue ) {
        return checkNotNull ( parameterSpec, nullableOrDefault ( parameterSpec, defaultValue ) );
    }


    // Private Helpers

    private @NotNull <T> T checkNotNull ( @NotNull String name, @Nullable T value ) {

        if ( value == null ) {
            throw new IllegalArgumentException ( "'" + name + "' is null, but supposed to be non-null." );
        } else {
            return value;
        }
    }

    private @NotNull <T> T checkNotNull ( @NotNull ParameterSpec<?> parameterSpec, @Nullable T value ) {
        return checkNotNull ( parameterSpec.getName(), value );
    }
}
