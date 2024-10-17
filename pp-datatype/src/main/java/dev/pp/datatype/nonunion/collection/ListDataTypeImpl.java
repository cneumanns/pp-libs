package dev.pp.datatype.nonunion.collection;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.datatype.DataType;
import dev.pp.datatype.utils.validator.DataValidator;
import dev.pp.text.documentation.SimpleDocumentation;

import java.util.List;
import java.util.function.Supplier;

public class ListDataTypeImpl<E> implements ListDataType<E> {


    private final @NotNull String name;
    public @NotNull String getName() { return name; }

    private final @NotNull DataType<E> elementDataType;
    public @NotNull DataType<E> getElementType() { return elementDataType; }

    private final @Nullable DataValidator<List<E>> validator;
    public @Nullable DataValidator<List<E>> getValidator() { return validator; }

    private final @Nullable Supplier<SimpleDocumentation> documentation;
    public @Nullable SimpleDocumentation getDocumentation() { return documentation != null ? documentation.get() : null; }


    public ListDataTypeImpl (
        @NotNull String name,
        @NotNull DataType<E> elementDataType,
        @Nullable DataValidator<List<E>> validator,
        @Nullable Supplier<SimpleDocumentation> documentation ) {

        this.name = name;
        this.elementDataType = elementDataType;
        this.validator = validator;
        this.documentation = documentation;
    }

    public ListDataTypeImpl (
        @NotNull String name,
        @NotNull DataType<E> elementDataType ) {

        this ( name, elementDataType, null, null );
    }

    public ListDataTypeImpl (
        @NotNull DataType<E> elementDataType ) {

        this ( elementDataType.getName() + "_list", elementDataType );
    }
}
