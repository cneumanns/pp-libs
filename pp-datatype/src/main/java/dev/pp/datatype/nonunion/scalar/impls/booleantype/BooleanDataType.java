package dev.pp.datatype.nonunion.scalar.impls.booleantype;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.InvalidDataException;
import dev.pp.datatype.nonunion.scalar.ScalarDataType;
import dev.pp.datatype.nonunion.scalar.impls.nulltype.NullDataType;
import dev.pp.datatype.utils.parser.DataParserException;
import dev.pp.datatype.utils.validator.DataValidator;
import dev.pp.text.documentation.SimpleDocumentation;
import dev.pp.text.location.TextLocation;

import java.util.List;
import java.util.function.Supplier;

public class BooleanDataType extends ScalarDataType<Boolean> {

    public static final String TRUE_LITERAL = "true";
    public static final String FALSE_LITERAL = "false";
    public static final String YES_LITERAL = "yes";
    public static final String NO_LITERAL = "no";

    public static boolean parse (
        @NotNull String string,
        boolean caseInsensitive,
        boolean allowYesNoStrings,
        @Nullable TextLocation location ) throws InvalidDataException {

        String literal = caseInsensitive ? string.toLowerCase() : string;

        switch ( literal ) {
            case TRUE_LITERAL -> { return true; }
            case FALSE_LITERAL -> { return false; }
            default -> {}
        }

        if ( allowYesNoStrings ) {
            switch ( literal ) {
                case YES_LITERAL -> { return true; }
                case NO_LITERAL -> { return false; }
                default -> {}
            }
        }

        throw new InvalidDataException (
            "'" + string + "' is an invalid boolean value. Valid values are: " +
            validValuesAsString ( caseInsensitive, allowYesNoStrings, ", " ) + ".",
            "INVALID_BOOLEAN_VALUE",
            string, location );
    }

    public static @NotNull List<String> validStringValues ( boolean allowYesNoStrings ) {

        return allowYesNoStrings
            ? List.of ( YES_LITERAL, NO_LITERAL, TRUE_LITERAL, FALSE_LITERAL )
            : List.of ( TRUE_LITERAL, FALSE_LITERAL );
    }

    public static @NotNull String validValuesAsString (
        boolean caseInsensitive,
        boolean allowYesNoStrings,
        @NotNull String separator ) {

        return String.join ( separator, validStringValues ( allowYesNoStrings ) ) +
            ( caseInsensitive ? " (case-insensitive)" : " (case-sensitive)" );
    }




    public static @NotNull List<String> validStringValues_() {
        return List.of ( YES_LITERAL, NO_LITERAL, TRUE_LITERAL, FALSE_LITERAL );
    }

    public static @NotNull String validValuesAsString_ ( @NotNull String separator ) {
        return String.join ( separator, validStringValues_() ) + " (case-insensitive)";
    }

    public static final @NotNull BooleanDataType INSTANCE = new BooleanDataType (
        "boolean",
        null,
        () -> new SimpleDocumentation (
            "Boolean",
            "A boolean value. Valid values are: " + validValuesAsString_ ( "," ) + ".",
            "true" ) );


    public BooleanDataType (
        @NotNull String name,
        @Nullable DataValidator<Boolean> validator,
        @Nullable Supplier<SimpleDocumentation> documentation ) {

        super ( name, validator, documentation );
    }


    @NotNull public Boolean parse (
        @Nullable String string,
        @Nullable TextLocation location ) throws DataParserException {

        NullDataType.checkNotNullString ( string, location );

        assert string != null;
        String lstring = string.toLowerCase();

        if ( lstring.equals ( YES_LITERAL ) || lstring.equals ( TRUE_LITERAL ) ) {
            return true;

        } else if ( lstring.equals ( NO_LITERAL ) || lstring.equals ( FALSE_LITERAL ) ) {
            return false;

        } else {
            throw new DataParserException (
                "'" + string + "' is an invalid boolean value. Valid values are: " + validValuesAsString() + ".",
                "ILLEGAL_BOOLEAN_VALUE",
                string, location );
        }
    }


    @Override
    public @Nullable String validValuesAsString ( @NotNull String separator ) {
        return validValuesAsString_ ( separator );
    }

    @Override
    public @NotNull List<String> validStringValues() { return validStringValues_(); }

    @Override
    public @NotNull List<Boolean> validValues() { return List.of ( true, false ); }
}
