package dev.pp.text.REMOVEobjectwriterOLD.impl;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.REMOVEobjectwriterOLD.ObjectWriterException;

import java.io.IOException;
import java.io.Writer;

public class TextObjectWriterDelegate extends DoNothingObjectWriterDelegate {

    private final @NotNull Writer writer;


    public TextObjectWriterDelegate ( @NotNull Writer writer ) {
        this.writer = writer;
    }


    // Record

    @Override
    public void writeRecordFieldStart ( @NotNull String fieldName, @Nullable Object fieldValue ) throws IOException, ObjectWriterException {
        if ( ! isScalarObject ( fieldValue ) ) {
            throw new ObjectWriterException ( "This writer does not support elements of type '" + fieldValue.getClass() + "' in records. Only scalar objects are supported (e.g. string, number, boolean)." );
        }
        writer.write ( fieldName );
    }

    @Override
    public void writeRecordFieldValueSeparator() throws IOException {
        writer.write ( "=" );
    }

    @Override
    public void writeRecordFieldSeparator() throws IOException {
        writer.write ( ", " );
    }


    // List

    @Override
    public void writeListElementStart ( @Nullable Object element ) throws IOException, ObjectWriterException {

        if ( ! isScalarObject ( element ) ) {
            throw new ObjectWriterException ( "This writer does not support elements of type '" + element.getClass() + "' in lists. Only scalar objects are supported (e.g. string, number, boolean)." );
        }
    }

    @Override
    public void writeListElementSeparator() throws IOException {
        writer.write ( ", " );
    }


    // Scalars

    @Override
    public void writeString ( @NotNull String string ) throws IOException {

        writer.write ( '\"' );
        writer.write ( string
            .replace ( "\\", "\\\\" )     // \ -> \\
            .replace ( "\"", "\\\"" ) );  // " -> \"
        writer.write ( '\"' );
    }

    @Override
    public void writeNumber ( @NotNull Number number ) throws IOException {
        writer.write ( number.toString() );
    }

    @Override
    public void writeBoolean ( @NotNull Boolean aBoolean ) throws IOException {
        writer.write ( aBoolean.toString() );
    }

    @Override
    public void writeNull() throws IOException {
        writer.write ( "null" );
    }

    @Override
    public void flush() throws IOException {
        writer.flush();
    }


    private boolean isScalarObject ( @Nullable Object object ) {

        if ( object == null ) return true;

        return object instanceof String
            || object instanceof Number
            || object instanceof Boolean
            || object instanceof Enum<?>;
    }
}
