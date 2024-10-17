package dev.pp.text.REMOVEobjectwriterOLD;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.io.IOException;

// TODO? ObjectDataTraverserEventHandler
// public interface ObjectWriterDelegate {
public interface ObjectSerializerEventHandler {

    // Start/End

    // onStart
    void writeStart() throws IOException, ObjectWriterException;

    void writeEnd() throws IOException, ObjectWriterException;


    // Record

    // recordStart, onRecordStart
    void writeRecordStart() throws IOException, ObjectWriterException;

    void writeRecordEnd() throws IOException, ObjectWriterException;

    void writeRecordFieldStart ( @NotNull String fieldName, @Nullable Object forFieldValue )
        throws IOException, ObjectWriterException;

    void writeRecordFieldEnd() throws IOException, ObjectWriterException;

    // betweenNameValueOfRecordField
    void writeRecordFieldValueSeparator() throws IOException, ObjectWriterException;

    // betweenRecordFields
    void writeRecordFieldSeparator() throws IOException, ObjectWriterException;


    // Iterable
    // List

    // onIterableStart
    void writeListStart() throws IOException, ObjectWriterException;

    void writeListEnd() throws IOException, ObjectWriterException;

    void writeListElementStart ( @Nullable Object element )
        throws IOException, ObjectWriterException;

    void writeListElementEnd() throws IOException, ObjectWriterException;

    // betweenListElements
    void writeListElementSeparator() throws IOException, ObjectWriterException;


    // Scalar

    // string, onString
    void writeString ( @NotNull String string ) throws IOException, ObjectWriterException;

    void writeNumber ( @NotNull Number number ) throws IOException, ObjectWriterException;

    void writeBoolean ( @NotNull Boolean aBoolean ) throws IOException, ObjectWriterException;


    // Null

    // null, onNull
    void writeNull() throws IOException, ObjectWriterException;


    // Other

    // needed?
    void flush() throws IOException;
}
