package dev.pp.text.REMOVEobjectwriterOLD.impl;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.REMOVEobjectwriterOLD.ObjectSerializerEventHandler;
import dev.pp.text.REMOVEobjectwriterOLD.ObjectWriterException;

import java.io.IOException;

public class DoNothingObjectWriterDelegate implements ObjectSerializerEventHandler {

    @Override
    public void writeStart() throws IOException, ObjectWriterException {
        // do nothing
    }

    @Override
    public void writeEnd() throws IOException, ObjectWriterException {
        // do nothing
    }


    // Record

    @Override
    public void writeRecordStart()
        throws IOException, ObjectWriterException {
        // do nothing
    }

    @Override
    public void writeRecordEnd()
        throws IOException, ObjectWriterException {
        // do nothing
    }

    @Override
    public void writeRecordFieldStart ( @NotNull String fieldName, @Nullable Object fieldValue )
        throws IOException, ObjectWriterException {
        // do nothing
    }

    @Override
    public void writeRecordFieldEnd()
        throws IOException, ObjectWriterException {
        // do nothing
    }

    public void writeRecordFieldValueSeparator()
        throws IOException, ObjectWriterException {
        // do nothing
    }

    @Override
    public void writeRecordFieldSeparator()
        throws IOException, ObjectWriterException {
        // do nothing
    }


    // List

    @Override
    public void writeListStart()
        throws IOException, ObjectWriterException {
        // do nothing
    }

    @Override
    public void writeListEnd()
        throws IOException, ObjectWriterException {
        // do nothing
    }

    @Override
    public void writeListElementStart ( @Nullable Object element )
        throws IOException, ObjectWriterException {
        // do nothing
    }

    @Override
    public void writeListElementEnd()
        throws IOException, ObjectWriterException {
        // do nothing
    }

    @Override
    public void writeListElementSeparator()
        throws IOException, ObjectWriterException {
        // do nothing
    }


    // Scalars

    @Override
    public void writeString ( @NotNull String string )
        throws IOException, ObjectWriterException {
        // do nothing
    }

    @Override
    public void writeNumber ( @NotNull Number number )
        throws IOException, ObjectWriterException {
        // do nothing
    }

    @Override
    public void writeBoolean ( @NotNull Boolean aBoolean )
        throws IOException, ObjectWriterException {
        // do nothing
    }

    @Override
    public void writeNull()
        throws IOException, ObjectWriterException {
        // do nothing
    }

    @Override
    public void flush() throws IOException {
        // do nothing
    }
}
