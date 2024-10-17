package dev.pp.text.REMOVEdatawalker;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.io.IOException;

public interface DataWalkerEventHandler {

    // TODO is this needed?
    void onStart() throws IOException, DataWalkerException;

    // TODO is this needed?
    void onEnd() throws IOException, DataWalkerException;


    // Record

    // TODO?
    // void onRecordStart ( @NotNull Object record ) throws IOException, DataWalkerException;
    void onRecordStart() throws IOException, DataWalkerException;

    // TODO?
    // void onRecordEnd ( @NotNull Object record ) throws IOException, DataWalkerException;
    void onRecordEnd() throws IOException, DataWalkerException;

    void onRecordFieldStart ( @NotNull String fieldName, @Nullable Object forFieldValue )
        throws IOException, DataWalkerException;

    void onRecordFieldEnd() throws IOException, DataWalkerException;

    void onBetweenNameValueOfRecordField() throws IOException, DataWalkerException;

    void onBetweenRecordFields() throws IOException, DataWalkerException;


    // Iterable

    // TODO?
    // void onIterableStart ( @NotNull Iterable<?> iterable ) throws IOException, DataWalkerException;
    void onIterableStart() throws IOException, DataWalkerException;

    void onIterableEnd() throws IOException, DataWalkerException;

    void onIterableElementStart ( @Nullable Object element )
        throws IOException, DataWalkerException;

    void onIterableElementEnd() throws IOException, DataWalkerException;

    void onBetweenIterableElements() throws IOException, DataWalkerException;


    // Scalars

    void onString ( @NotNull String string ) throws IOException, DataWalkerException;

    void onNumber ( @NotNull Number number ) throws IOException, DataWalkerException;

    void onBoolean ( @NotNull Boolean aBoolean ) throws IOException, DataWalkerException;


    // Null

    void onNull() throws IOException, DataWalkerException;
}
