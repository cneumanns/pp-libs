package dev.pp.parameters.parameter;

import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.StringTruncator;
import dev.pp.text.location.TextLocation;
import dev.pp.text.token.TextToken;

public class Parameter<V> {

    private final @NotNull String name;
    public @NotNull String getName() { return name; }

    private final @Nullable V value;
    public @Nullable V getValue() { return value; }

    private final @Nullable ParameterSpec<V> spec;
    public @Nullable ParameterSpec<V> getSpec() { return spec; }

    private final @Nullable TextLocation nameLocation;
    public @Nullable TextLocation getNameLocation() { return nameLocation; }

    private final @Nullable TextLocation valueLocation;
    public @Nullable TextLocation getValueLocation() { return valueLocation; }

    // private final @Nullable String originalStringValue;
    // public @Nullable String getOriginalStringValue() { return originalStringValue; }


    public Parameter (
        @NotNull String name,
        @Nullable V value,
        @Nullable ParameterSpec<V> spec,
        @Nullable TextLocation nameLocation,
        @Nullable TextLocation valueLocation ) {

        this.name = name;
        this.value = value;
        this.spec = spec;
        this.nameLocation = nameLocation;
        this.valueLocation = valueLocation;
    }

    public Parameter (
        @NotNull String name,
        @Nullable V value,
        @Nullable TextLocation nameLocation,
        @Nullable TextLocation valueLocation ) {

        this ( name, value, null, nameLocation, valueLocation );
    }

    public Parameter (
        @NotNull String name,
        @Nullable V value ) {

        this ( name, value, null, null, null );
    }

    public static @NotNull Parameter<String> ofString (
        @NotNull TextToken nameToken,
        @NotNull TextToken valueToken,
        @Nullable ParameterSpec<String> spec ) {

        return new Parameter<> (
            nameToken.getText(), valueToken.getText(), spec, nameToken.getLocation(), valueToken.getLocation() );
    }


    public @NotNull TextToken nameToken() {
        return new TextToken ( name, nameLocation );
    }

    public @NotNull TextToken valueToken() {
        return new TextToken ( valueAsNonNullString (), valueLocation );
    }

    public @Nullable TextLocation valueOrElseNameLocation() {
        return valueLocation != null ? valueLocation : nameLocation;
    }

    @Nullable public <T extends V> T castedValue() {
        @SuppressWarnings ( "unchecked" )
        T result = (T) value;
        return result;
    }

    public @NotNull Parameter<V> withName ( @NotNull String newName ) {
        return new Parameter<> ( newName, value, spec, nameLocation, valueLocation );
    }

    public @NotNull String valueAsNonNullString() {

        @Nullable String result = valueAsNullableString();
        return result == null ? "" : result;
    }

    public @Nullable String valueAsNullableString() {

        if ( value == null ) return null;

        if ( value instanceof String s ) {
            return s;
        } else if ( spec != null ){
            return spec.objectToString ( value );
        } else {
            return value.toString();
        }
    }

    @Override
    public @NotNull String toString() {
        return name + " = " + StringTruncator.truncateWithEllipses ( valueAsNonNullString () );
    }
}
