package dev.pp.datatype.newversion;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.InvalidDataException;
import dev.pp.text.documentation.SimpleDocumentation;
import dev.pp.text.location.TextLocation;
import dev.pp.text.token.TextToken;

import java.io.IOException;
import java.io.Writer;
import java.util.function.Supplier;

public class NullDataType_NEW extends AnyDataType<Void> {


    public static final @NotNull String NULL_STRING = "null";

    public static final @NotNull NullDataType_NEW INSTANCE = new NullDataType_NEW (
        "native",
        "null",
        null );
        // NULL_STRING );


    // private final @NotNull String nullString;
    // public @NotNull String getNullString () { return nullString; }


    private NullDataType_NEW (
        @Nullable String namespace,
        @NotNull String name,
        @Nullable Supplier<SimpleDocumentation> documentationSupplier ) {
        // @NotNull String nullString ) {

        super ( namespace, name, null );

        // this.nullString = nullString;
    }


    @Override
    public boolean isNullable() { return true; }

    @Override
    public boolean hasValidator() { return false; };

    @Override
    public void validateValue ( @NotNull Void value, @Nullable TextToken token )
        throws InvalidDataException {}

    @Override
    public boolean canParse() { return true; }

    @Override
    public @NotNull Void parseValue (
        @NotNull String string,
        @Nullable TextLocation location ) throws InvalidDataException {

        if ( isNullString ( string ) ) {
            return null;
        } else {
            throw new InvalidDataException (
                "'" + string + "' is not a null value.",
                "INVALID_NULL_VALUE",
                string, location );
        }
    }

    @Override
    public boolean canWrite() { return true; }

    @Override
    public void writeValue ( @NotNull Void value, @NotNull Writer writer ) throws IOException {
        writer.write ( NULL_STRING );
    }

    // @Override
    public void writeValue ( @NotNull Writer writer ) throws IOException {
        writer.write ( NULL_STRING );
    }

    public boolean isNullString ( @NotNull String string ) {
        return string.equals ( NULL_STRING );
    }
}
