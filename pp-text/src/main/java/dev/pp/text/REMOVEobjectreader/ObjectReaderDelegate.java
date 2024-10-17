package dev.pp.text.REMOVEobjectreader;

import dev.pp.basics.annotations.NotNull;

import java.io.IOException;

public interface ObjectReaderDelegate {

    void start() throws IOException, ObjectReaderException;
    void end() throws IOException, ObjectReaderException;


    // Record


    // List

    void readListStart() throws IOException, ObjectReaderException;
    boolean readListEnd() throws IOException, ObjectReaderException;
    void readListElementStart() throws IOException, ObjectReaderException;
    boolean readListElementEnd() throws IOException, ObjectReaderException;
    boolean readListElementSeparator() throws IOException, ObjectReaderException;


    // Scalars

    @NotNull String readString() throws IOException, ObjectReaderException;
    // @Nullable String readStringOrNull() throws IOException, ObjectReaderException;
    boolean readBoolean() throws IOException, ObjectReaderException;
    // @Nullable Boolean readBooleanOrNull() throws IOException, ObjectReaderException;
    int readInt() throws IOException, ObjectReaderException;
    double readDouble() throws IOException, ObjectReaderException;
    boolean readNull() throws IOException, ObjectReaderException;
}
