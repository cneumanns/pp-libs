package dev.pp.datatype;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.datatype.nonunion.scalar.impls.nulltype.NullDataType;
import dev.pp.datatype.utils.parser.DataParserException;
import dev.pp.datatype.utils.validator.DataValidator;
import dev.pp.datatype.utils.validator.DataValidatorException;
import dev.pp.text.documentation.SimpleDocumentation;
import dev.pp.text.location.TextLocation;
import dev.pp.text.token.TextToken;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public interface DataType<T> {

    /*
    @Nullable String namespace
    @NotNull String name
    @Nullable Supplier<SimpleDocumentation> documentationSupplier
    @Nullable DataValidator<T> validator;

    boolean isNullable()
    validate
    isValid

    Scalar:
        parseXXX
        writeObject/writeValue
        objectToString/valueToString

    Enum
        validValues()
     */

    @NotNull String getName();

    @Nullable SimpleDocumentation getDocumentation();


    // Validation

    boolean isNullable();

    @Nullable DataValidator<T> getValidator();

    default void validate ( @Nullable T object, @Nullable TextToken token ) throws DataValidatorException {

        validateNull ( object, token );
        if ( object == null ) return;

        DataValidator<T> validator = getValidator();
        if ( validator != null ) {
            validator.validate ( object, token );
        }
    }

    default boolean isValid ( @Nullable T object ) {

        try {
            validate ( object, null );
            return true;
        } catch ( DataValidatorException e ) {
            return false;
        }
    }

    default void validateNull ( @Nullable T object, @Nullable TextToken token ) throws DataValidatorException {

        if ( object == null && ! isNullable() ) throw new DataValidatorException (
            "A value must be provided. 'null' (no value) is not allowed.",
            "NULL_NOT_ALLOWED",
            token,
            null );
    }

    default @Nullable String validValuesAsString() {

        return validValuesAsString ( ", " );
    }

    default @Nullable String validValuesAsString ( @NotNull String separator ) {

        @Nullable List<String> validStringValues = validStringValues();
        return validStringValues == null ? null : String.join ( separator, validStringValues );
    }

    default @Nullable List<String> validStringValues() {

        @Nullable List<T> validValues = validValues();
        return validValues == null ? null : validValues
            .stream()
            .map ( Object::toString )
            .toList();
    }

    default @Nullable List<T> validValues() { return null; }

    // TODO? default @Nullable List<T> exampleValues() { return null; }


    // Parse

    default @Nullable T parse ( @NotNull TextToken token ) throws DataParserException {
        return parse ( token.getText(), token.getLocation() );
    }

    @Nullable T parse ( @Nullable String string, @Nullable TextLocation location ) throws DataParserException;

    default @Nullable T parseAndValidate ( @NotNull TextToken token )
        throws DataParserException, DataValidatorException {

        return parseAndValidate ( token.getText(), token.getLocation() );
    }

    default @Nullable T parseAndValidate ( @Nullable String string, @Nullable TextLocation location )
        throws DataParserException, DataValidatorException {

        T object = parse ( string, location );
        validate ( object, TextToken.createForNullable ( string, location ) );
        return object;
    }


    // Write

    default @NotNull String objectToString ( @Nullable T object ) {
        return object != null ? object.toString() : NullDataType.NULL_STRING;
    }

    default void writeObject ( @Nullable T object, @NotNull Writer writer ) throws IOException {
        writer.write ( objectToString ( object ) );
    }


    // TODO: compare

    // @Override
    // default String toString() { return getName(); }
}
