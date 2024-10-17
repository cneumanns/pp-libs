package dev.pp.datatype.nonunion.record;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.datatype.nonunion.NonUnionDataType;
import dev.pp.datatype.utils.parser.DataParser;
import dev.pp.datatype.utils.writer.DataWriter;

public interface RecordDataType<T, F> extends NonUnionDataType<T> {

    // this creates a circular dependency. Solution?: move dev.pp.datatype to dev.pp.parameters
    // @NotNull ParameterSpecs<V> getFields();
    boolean hasField ( @NotNull String fieldName );
    // @NotNull ParameterSpecs<V> getField ( @NotNull String fieldName );

    @Nullable DataParser<T> getParser();

    @Nullable DataWriter<T> getWriter();
}
