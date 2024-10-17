package dev.pp.text.REMOVEobjectreader.impl;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.REMOVEobjectreader.ObjectReaderDelegate;
import dev.pp.text.REMOVEobjectreader.ObjectReaderException;
import dev.pp.text.reader.CharReader;
import dev.pp.text.reader.CharReaderImpl;
import dev.pp.text.resource.TextResource;

import java.io.IOException;
import java.io.Reader;

public class TextObjectReaderDelegate implements ObjectReaderDelegate {

    private final @NotNull CharReader reader;


    public TextObjectReaderDelegate ( @NotNull CharReader charReader ) {
        this.reader = charReader;
    }

    public TextObjectReaderDelegate (
        @NotNull Reader reader,
        @Nullable TextResource textResource,
        @Nullable Integer lineOffset,
        @Nullable Integer columnOffset ) {

        this.reader = new CharReaderImpl ( reader, textResource, lineOffset, columnOffset );
    }

    public TextObjectReaderDelegate (
        @NotNull Reader reader,
        @Nullable TextResource textResource ) {

        this ( reader, textResource, null, null );
    }


    @Override
    public void start() throws IOException, ObjectReaderException {
    }

    @Override
    public void end() throws IOException, ObjectReaderException {
    }

    @Override
    public void readListStart() throws IOException, ObjectReaderException {
    }

    @Override
    public boolean readListEnd() throws IOException, ObjectReaderException {
        return false;
    }

    @Override
    public void readListElementStart() throws IOException, ObjectReaderException {
    }

    @Override
    public boolean readListElementEnd() throws IOException, ObjectReaderException {
        return false;
    }

    @Override
    public boolean readListElementSeparator() throws IOException, ObjectReaderException {
        return false;
    }

    @Override
    public @NotNull String readString() throws IOException, ObjectReaderException {
        return null;
    }

    @Override
    public boolean readBoolean() throws IOException, ObjectReaderException {

        return reader.skipString ( "true" ) ||
            reader.skipString ( "false" );
    }

    @Override
    public int readInt() throws IOException, ObjectReaderException {
        return 0;
    }

    @Override
    public double readDouble() throws IOException, ObjectReaderException {
        return 0;
    }

    @Override
    public boolean readNull() throws IOException, ObjectReaderException {

        return reader.skipString ( "null" );
    }
}
