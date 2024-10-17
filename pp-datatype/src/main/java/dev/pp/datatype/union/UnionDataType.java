package dev.pp.datatype.union;

import dev.pp.basics.annotations.NotNull;
import dev.pp.datatype.DataType;
import dev.pp.datatype.nonunion.NonUnionDataType;

import java.util.Collection;
import java.util.List;

public interface UnionDataType<T> extends DataType<T> {

    char TYPE_VALUE_SEPARATOR = ':';

    @NotNull Collection<NonUnionDataType<?>> getMembers();

    boolean hasMember ( String typeName );

    @NotNull NonUnionDataType<?> requireMember ( String typeName );

    @NotNull List<String> getMemberNames();

    @NotNull String getMemberNamesAsString ( @NotNull String separator );

    @NotNull String getMemberNamesAsString();
}
