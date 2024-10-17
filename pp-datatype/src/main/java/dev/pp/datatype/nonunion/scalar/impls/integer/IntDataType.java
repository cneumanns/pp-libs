package dev.pp.datatype.nonunion.scalar.impls.integer;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.InvalidDataException;
import dev.pp.datatype.nonunion.scalar.ScalarDataType;
import dev.pp.datatype.nonunion.scalar.impls.nulltype.NullDataType;
import dev.pp.datatype.utils.parser.DataParserException;
import dev.pp.datatype.utils.validator.DataValidator;
import dev.pp.text.documentation.SimpleDocumentation;
import dev.pp.text.location.TextLocation;

import java.util.function.Supplier;

public class IntDataType extends ScalarDataType<Integer> {

    public static int parse_ (
        @NotNull String string,
        @Nullable TextLocation location ) throws InvalidDataException {

        try {
            // TODO improve to also allow binary, octal, and hex formats,
            // e.g. 0b00010001, 0hFA1
            // TODO improve to also allow _
            // e.g. 1_000, 0b0001_0001
            // see Java, Rust, and Swift doc.
            return Integer.parseInt ( string );
        } catch ( NumberFormatException e ) {
            throw new InvalidDataException (
                "'" + string + "' is an invalid integer number. Reason: " + e.getMessage(),
                "INVALID_INTEGER_VALUE",
                string, location, e );
        }
    }

    public static final @NotNull IntDataType DEFAULT = new IntDataType (
        "integer32",
        null,
        () -> new SimpleDocumentation (
            "32 Bits Integer", "A 32 bits signed integer number", "42" ));


    public IntDataType (
        @NotNull String name,
        @Nullable DataValidator<Integer> validator,
        @Nullable Supplier<SimpleDocumentation> documentation ) {

        super ( name, validator, documentation );
    }


    @NotNull public Integer parse (
        @Nullable String string,
        @Nullable TextLocation location ) throws DataParserException {

        NullDataType.checkNotNullString ( string, location );
        assert string != null;

        try {
            return Integer.parseInt ( string );
        } catch ( NumberFormatException e ) {
            throw new DataParserException (
                "'" + string + "' is an invalid integer number. Reason: " + e.getMessage(),
                "ILLEGAL_INTEGER_VALUE",
                string, location, e );
        }
    }
}
