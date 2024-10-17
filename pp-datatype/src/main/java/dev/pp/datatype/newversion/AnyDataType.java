package dev.pp.datatype.newversion;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.InvalidDataException;
import dev.pp.text.documentation.SimpleDocumentation;
import dev.pp.text.location.TextLocation;
import dev.pp.text.token.TextToken;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.function.Supplier;

// TODO? public abstract class AnyDataType<JAVA_TYPE, GENERIC_TYPE extends AnyDataType> {
public abstract class AnyDataType<JAVA_TYPE> {

    protected final @Nullable String namespace;
    public @Nullable String getNamespace () { return namespace; }

    protected final @NotNull String name;
    public @NotNull String getName () { return name; }

    protected final @Nullable Supplier<SimpleDocumentation> documentationSupplier;
    public @Nullable Supplier<SimpleDocumentation> getDocumentationSupplier () {
        return documentationSupplier;
    }


    public AnyDataType (
        @Nullable String namespace,
        @NotNull String name,
        @Nullable Supplier<SimpleDocumentation> documentationSupplier ) {

        this.namespace = namespace;
        this.name = name;
        this.documentationSupplier = documentationSupplier;
    }


    public @NotNull String qualifiedName() {

        if ( namespace == null ) {
            return name;
        } else {
            return namespace + "." + name;
        }
    }

    public abstract boolean isNullable();


    // Validate

    public abstract boolean hasValidator();

    public abstract void validateValue ( @NotNull JAVA_TYPE value, @Nullable TextToken token )
        throws InvalidDataException;

    public boolean isValidValue ( @NotNull JAVA_TYPE value ) {
        try {
            validateValue ( value, null );
            return true;
        } catch ( InvalidDataException e ) {
            return false;
        }
    }


    // Read

    // TODO


    // Parse

    public abstract boolean canParse();

    public abstract @NotNull JAVA_TYPE parseValue ( @NotNull String string, @Nullable TextLocation location )
        throws InvalidDataException;

    // TODO? public abstract @NotNull GENERIC_TYPE parseGenericValue ( @NotNull TextToken token )
    //    throws InvalidDataException;

    public @NotNull JAVA_TYPE parseValue ( @NotNull TextToken token )
        throws InvalidDataException {

        return parseValue ( token.getText(), token.getLocation() );
    }

    public @NotNull JAVA_TYPE parseValueAndValidate ( @NotNull String string, @Nullable TextLocation location )
        throws InvalidDataException {

        JAVA_TYPE object = parseValue ( string, location );
        validateValue ( object, new TextToken ( string, location ) );
        return object;
    }

    public @NotNull JAVA_TYPE parseValueAndValidate ( @NotNull TextToken token ) throws InvalidDataException {
        return parseValueAndValidate ( token.getText(), token.getLocation() );
    }


    // Write

    public abstract boolean canWrite();

    public abstract void writeValue ( @NotNull JAVA_TYPE value, @NotNull Writer writer ) throws IOException;

    // TODO? public abstract void writeGenericValue ( @NotNull GENERIC_TYPE value, @NotNull Writer writer ) throws IOException;

    public @NotNull String valueToString ( @NotNull JAVA_TYPE value ) {

        try ( StringWriter stringWriter = new StringWriter() ) {
            writeValue ( value, stringWriter );
            return stringWriter.toString ();
        } catch ( IOException e ) {
            throw new RuntimeException ( e );
        }
    }
}
