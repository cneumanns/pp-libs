package dev.pp.datatype.newversion;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.InvalidDataException;
import dev.pp.text.documentation.SimpleDocumentation;
import dev.pp.text.location.TextLocation;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.function.Supplier;

public class BooleanDataType_NEW extends AbstractDataType_NEW<Boolean> {

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

    private static final @NotNull ValueParser_NEW<Boolean> PARSER = new ValueParser_NEW<>() {

        public @NotNull Boolean parse (
            @NotNull String string, @Nullable TextLocation location ) throws InvalidDataException {

            return BooleanDataType_NEW.parse ( string, false, true, location );
        }
    };

    private static final @NotNull ValueWriter_NEW<Boolean> WRITER = new ValueWriter_NEW<>() {

        public void write ( @NotNull Boolean value, @NotNull Writer writer ) throws IOException {
            writer.write ( value.toString() );
        }
    };

    public static final @NotNull BooleanDataType_NEW INSTANCE = new BooleanDataType_NEW (
        "native",
        "boolean",
        () -> new SimpleDocumentation (
            "Boolean",
            "A boolean value. Valid values are: " + validValuesAsString ( false, true, "," ) + ".",
            "true" ),
        null, PARSER, WRITER );


    private BooleanDataType_NEW (
        @Nullable String namespace,
        @NotNull String name,
        @Nullable Supplier<SimpleDocumentation> documentationSupplier,
        // @Nullable ValueValidator_NEW<Boolean> validator,
        @Nullable ValueReader_NEW<Boolean> reader,
        @Nullable ValueParser_NEW<Boolean> parser,
        @Nullable ValueWriter_NEW<Boolean> writer ) {

        super ( namespace, name, documentationSupplier,
            null, reader, parser, writer );
    }
}
