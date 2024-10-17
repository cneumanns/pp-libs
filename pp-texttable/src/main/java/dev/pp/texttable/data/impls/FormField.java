package dev.pp.texttable.data.impls;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.util.function.Function;

public class FormField<VALUE> {


    private final @Nullable String label;
    public @Nullable String getLabel() { return label; }

    private final @Nullable VALUE value;
    public @Nullable VALUE getValue() { return value; }

    private final @NotNull Function<VALUE, String> valueToStringConverter;
    public @NotNull Function<VALUE, String> getValueToStringConverter() { return valueToStringConverter; }


    public FormField (
        @Nullable String label,
        @Nullable VALUE value,
        @NotNull Function<VALUE, String> valueToStringConverter ) {

        this.label = label;
        this.value = value;
        this.valueToStringConverter = valueToStringConverter;
    }

    public FormField (
        @Nullable String label,
        @Nullable VALUE value ) {

        this ( label, value, Object::toString );
    }


    public @Nullable String valueAsString() {

        if ( value != null ) {
            return valueToStringConverter.apply ( value );
        } else {
            return null;
        }
    }
}
