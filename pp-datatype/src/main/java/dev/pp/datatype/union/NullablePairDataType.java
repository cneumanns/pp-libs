package dev.pp.datatype.union;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.datatype.nonunion.NonUnionDataType;
import dev.pp.datatype.nonunion.scalar.impls.nulltype.NullDataType;
import dev.pp.datatype.utils.parser.DataParserException;
import dev.pp.datatype.utils.validator.DataValidator;
import dev.pp.datatype.utils.validator.DataValidatorException;
import dev.pp.text.documentation.SimpleDocumentation;
import dev.pp.text.location.TextLocation;

import java.util.List;
import java.util.function.Supplier;

public class NullablePairDataType<T> extends UnionDataTypeImpl<T> {

    protected final @NotNull NonUnionDataType<T> nonNullMember;
    public @NotNull NonUnionDataType<T> getNonNullMember() { return nonNullMember; }

    public NullablePairDataType (
        @NotNull String name,
        @NotNull NonUnionDataType<T> nonNullMember,
        @Nullable DataValidator<T> validator,
        @Nullable Supplier<SimpleDocumentation> documentation ) {

        super ( name, List.of ( nonNullMember, NullDataType.INSTANCE ), validator, documentation );
        this.nonNullMember = nonNullMember;
    }

    public NullablePairDataType ( @NotNull NonUnionDataType<T> nonNullMember ) {

        super ( List.of ( nonNullMember, NullDataType.INSTANCE ) );
        this.nonNullMember = nonNullMember;
    }


    public @Nullable T parse (
        @Nullable String string,
        @Nullable TextLocation location ) throws DataParserException {

        if ( NullDataType.isNullString ( string ) ) {
            return null;
        } else {
            try {
                return nonNullMember.parseAndValidate ( string, location );
            } catch ( DataValidatorException e ) {
                throw new DataParserException ( e, string, location );
            }
        }
    }

    public boolean isNullable() { return true; }
}
