package dev.pp.datatype.nonunion.scalar.impls.string;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.datatype.nonunion.scalar.ScalarDataType;
import dev.pp.datatype.nonunion.scalar.impls.nulltype.NullDataType;
import dev.pp.datatype.utils.parser.DataParserException;
import dev.pp.datatype.utils.validator.DataValidator;
import dev.pp.text.documentation.SimpleDocumentation;
import dev.pp.text.location.TextLocation;

import java.util.function.Supplier;

public class StringDataType extends ScalarDataType<String> {

    public static final @NotNull Supplier<SimpleDocumentation> DEFAULT_DOCUMENTATION = () -> new SimpleDocumentation (
        "String", "A sequence of Unicode characters.", "Any text" );

    public static final @NotNull StringDataType DEFAULT = new StringDataType (
        "string",
        null,
        DEFAULT_DOCUMENTATION );


    public StringDataType (
        @NotNull String name,
        @Nullable DataValidator<String> validator,
        @Nullable Supplier<SimpleDocumentation> documentation ) {

        super ( name, validator, documentation );
    }

    /* TODO
    public StringDataType ( @NotNull DataValidator validator name )
    public StringDataType ( @NotNull Pattern pattern )
    public StringDataType ( @NotNull Pattern string )
    public StringDataType ( int minLength, int maxLength )
    etc.
    public StringDataType ( @NotNull String name ) { this.name = name; }

     */


    @NotNull public String parse (
        @Nullable String string,
        @Nullable TextLocation location ) throws DataParserException {

        NullDataType.checkNotNullString ( string, location );
        assert string != null;

        return string;
    }
}
