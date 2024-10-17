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

public abstract class AbstractDataType_NEW<T> extends AnyDataType<T> {

    protected final @Nullable ValueValidator_NEW<T> validator;
    // public @Nullable ValueValidator_NEW<T> getValidator () { return validator; }

    protected final @Nullable ValueReader_NEW<T> reader;
    // public @Nullable ValueReader_NEW<T> getReader () { return reader; }

    protected final @Nullable ValueParser_NEW<T> parser;
    // public @Nullable ValueParser_NEW<T> getParser () { return parser; }

    protected final @Nullable ValueWriter_NEW<T> writer;
    // public @Nullable ValueWriter_NEW<T> getWriter () { return writer; }


    public AbstractDataType_NEW (
        @Nullable String namespace,
        @NotNull String name,
        @Nullable Supplier<SimpleDocumentation> documentationSupplier,
        @Nullable ValueValidator_NEW<T> validator,
        @Nullable ValueReader_NEW<T> reader,
        @Nullable ValueParser_NEW<T> parser,
        @Nullable ValueWriter_NEW<T> writer ) {

        super ( namespace, name, documentationSupplier );

        this.validator = validator;
        this.reader = reader;
        this.parser = parser;
        this.writer = writer;
    }


    public boolean isNullable() { return false; }


    // Validate

    public boolean hasValidator() { return validator != null; }

    @Override
    public void validateValue ( @NotNull T value, @Nullable TextToken token ) throws InvalidDataException {

        if ( validator != null ) {
            validator.validate ( value, token );
        }
    }


    // Parse

    @Override
    public boolean canParse() { return parser != null; }

    @Override
    public @NotNull T parseValue ( @NotNull String string, @Nullable TextLocation location )
        throws InvalidDataException {

        if ( parser != null ) {
            return parser.parse ( string, location );
        } else {
            throw new IllegalStateException ( "No parser available." );
        }
    }


    // Write

    @Override
    public boolean canWrite() { return writer != null; }

    @Override
    public void writeValue ( @NotNull T value, @NotNull Writer writer ) throws IOException {

        if ( this.writer != null ) {
            this.writer.write ( value, writer );
        } else {
            throw new IllegalStateException ( "No writer available." );
        }
    }
}
