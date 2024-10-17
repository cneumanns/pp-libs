package dev.pp.datatype.union;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.datatype.DataType;
import dev.pp.datatype.nonunion.NonUnionDataType;
import dev.pp.datatype.nonunion.scalar.impls.nulltype.NullDataType;
import dev.pp.datatype.utils.parser.DataParserException;
import dev.pp.datatype.utils.validator.DataValidator;
import dev.pp.datatype.utils.validator.DataValidatorException;
import dev.pp.text.documentation.SimpleDocumentation;
import dev.pp.text.location.TextLocation;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class UnionDataTypeImpl<T> implements UnionDataType<T> {


    protected final @NotNull String name;
    public @NotNull String getName() { return name; }

    protected final @NotNull LinkedHashMap<String, NonUnionDataType<?>> members;
    public @NotNull Collection<NonUnionDataType<?>> getMembers() { return members.values(); }

    protected final @Nullable DataValidator<T> validator;
    public @Nullable DataValidator<T> getValidator() { return validator; }

    protected final @Nullable Supplier<SimpleDocumentation> documentation;
    public @Nullable SimpleDocumentation getDocumentation() { return documentation != null ? documentation.get() : null; }


    public UnionDataTypeImpl (
        @NotNull String name,
        @NotNull List<NonUnionDataType<?>> members,
        @Nullable DataValidator<T> validator,
        @Nullable Supplier<SimpleDocumentation> documentation ) {

        this.name = name;
        this.members = createMembers ( members );
        this.validator = validator;
        this.documentation = documentation;
    }

    public UnionDataTypeImpl (
        @NotNull List<NonUnionDataType<?>> members ) {

        this.members = createMembers ( members );
        this.name = getMemberNamesAsString ( " or " );
        this.validator = null;
        this.documentation = () -> new SimpleDocumentation (
            name,
            "A union type. Allowed types: " + getMemberNamesAsString(),
            "123" );
    }


    private static LinkedHashMap<String, NonUnionDataType<?>> createMembers ( @NotNull List<NonUnionDataType<?>> members ) {

        if ( members.size() < 2 )
            throw new IllegalArgumentException ( "A union type must have two members. " + members.size() + " member(s) is not allowed." );

        LinkedHashMap<String, NonUnionDataType<?>> map = new LinkedHashMap<>();
        for ( NonUnionDataType<?> member : members ) {
            String name = member.getName();
            if ( ! map.containsKey ( name ) ) {
                map.put ( name, member );
            } else {
                throw new IllegalArgumentException ( "Member '" + name + "' cannot be used twice." );
            }
        }
        return map;
    }


    public @Nullable T parse (
        @Nullable String string,
        @Nullable TextLocation location ) throws DataParserException {

        if ( NullDataType.isNullString ( string ) ) {
            if ( isNullable() ) {
                return null;
            } else {
                throw new DataParserException (
                    "'null' is not allowed for type '" + getName () + "'.",
                    "NULL_NOT_ALLOWED",
                    string, location );
            }
        }

        char separatorChar = UnionDataType.TYPE_VALUE_SEPARATOR;
        int separatorIndex = string.indexOf ( separatorChar );
        if ( separatorIndex == -1 || separatorIndex == 0 || separatorIndex == string.length() - 1 )
            throw new DataParserException (
                "Invalid format for union type. A value for union type '" + getName() +
                    "' must have the format 'type" + separatorChar + "value'.",
                "INVALID_UNION_TYPE",
                string, location );

        String typeName = string.substring ( 0, separatorIndex );
        if ( ! hasMember ( typeName ) ) throw new DataParserException (
            "Type '" + typeName + "' is not a member of union type '" + getName() + "'.",
            "INVALID_UNION_TYPE",
            string, location );
        NonUnionDataType<?> type = requireMember ( typeName );

        String value = string.substring ( separatorIndex + 1 );
        // T result =  (T) type.parseWithoutValidating ( value, token );
        try {
            @SuppressWarnings ("unchecked")
            T result = (T) type.parseAndValidate ( value, location );
            return result;
        } catch ( DataValidatorException e ) {
            throw new DataParserException ( e, string, location );
        }
    }

    public boolean isNullable() {

        for ( NonUnionDataType<?> member : members.values() ) {
            if ( member == NullDataType.INSTANCE ) return true;
        }
        return false;
    }

    public @NotNull List<String> getMemberNames() {
        return members.values().stream().map ( DataType::getName ).collect ( Collectors.toList() );
    }

    public @NotNull String getMemberNamesAsString ( @NotNull String separator ) {
        return String.join ( separator, getMemberNames() );
    }

    public @NotNull String getMemberNamesAsString() {
        return getMemberNamesAsString ( ", " );
    }

    public boolean hasMember ( String typeName ) { return members.containsKey ( typeName ); }

    public @NotNull NonUnionDataType<?> requireMember ( String typeName ) {

        NonUnionDataType<?> member = members.get ( typeName );
        if ( member == null ) throw new IllegalArgumentException (
            "Member type '" + typeName + "' is not contained in the union type'" + getName() + "'." );

        return member;
    }
}
