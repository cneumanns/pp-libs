package dev.pp.text.REMOVEdatawalker.impl;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.REMOVEdatawalker.DataWalkerException;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public class TextWriterObjectDataWalkerEventHandler extends DoNothingObjectDataWalkerEventHandler implements Flushable, Closeable {

    private final @NotNull Writer writer;


    public TextWriterObjectDataWalkerEventHandler ( @NotNull Writer writer ) {
        this.writer = writer;
    }


    // Record

    @Override
    public void onRecordFieldStart ( @NotNull String fieldName, @Nullable Object fieldValue ) throws IOException, DataWalkerException {

        if ( ! isScalarObject ( fieldValue ) ) {
            throw new DataWalkerException ( "This writer does not support elements of type '" + fieldValue.getClass() + "' in records. Only scalar objects are supported (e.g. string, number, boolean)." );
        }
        writer.write ( fieldName );
    }

    @Override
    public void onBetweenNameValueOfRecordField() throws IOException {
        writer.write ( "=" );
    }

    @Override
    public void onBetweenRecordFields() throws IOException {
        writer.write ( ", " );
    }


    // List

    @Override
    public void onIterableElementStart( @Nullable Object element ) throws IOException, DataWalkerException {

        if ( ! isScalarObject ( element ) ) {
            throw new DataWalkerException ( "This writer does not support elements of type '" + element.getClass() + "' in lists. Only scalar objects are supported (e.g. string, number, boolean)." );
        }
    }

    @Override
    public void onBetweenIterableElements() throws IOException {
        writer.write ( ", " );
    }


    // Scalars

    @Override
    public void onString ( @NotNull String string ) throws IOException {

        writer.write ( '\"' );
        writer.write ( string
            .replace ( "\\", "\\\\" )     // \ -> \\
            .replace ( "\"", "\\\"" ) );  // " -> \"
        writer.write ( '\"' );
    }

    @Override
    public void onNumber ( @NotNull Number number ) throws IOException {
        writer.write ( number.toString() );
    }

    @Override
    public void onBoolean ( @NotNull Boolean aBoolean ) throws IOException {
        writer.write ( aBoolean.toString() );
    }

    @Override
    public void onNull() throws IOException {
        writer.write ( "null" );
    }

    @Override
    public void flush() throws IOException {
        writer.flush();
    }

    @Override
    public void close() throws IOException {
        writer.close();;
    }


    private boolean isScalarObject ( @Nullable Object object ) {

        if ( object == null ) return true;

        return object instanceof String
            || object instanceof Number
            || object instanceof Boolean
            || object instanceof Enum<?>;
    }
}
