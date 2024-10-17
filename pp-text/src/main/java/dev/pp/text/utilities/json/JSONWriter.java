package dev.pp.text.utilities.json;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.json.JSONConstants;
import dev.pp.basics.utilities.json.JSONEscaper;
import dev.pp.text.utilities.text.BasicDocumentWriter;
import dev.pp.text.utilities.text.IORunnable;

import java.io.IOException;
import java.io.Writer;

@Deprecated
public class JSONWriter extends BasicDocumentWriter {


    public JSONWriter ( @NotNull Writer writer, @NotNull String singleIndent ) {
        super ( writer, singleIndent );
    }

    public JSONWriter ( @NotNull Writer writer ) {
        super ( writer );
    }


    // Basics

    @Override
    public JSONWriter write ( @NotNull String string ) throws IOException {
        super.write ( string );
        return this;
    }

    @Override
    public JSONWriter writeNullable ( @Nullable String string ) throws IOException {
        super.writeNullable ( string );
        return this;
    }

    @Override
    public JSONWriter writeLine ( @NotNull String string ) throws IOException {
        super.writeLine ( string );
        return this;
    }

    @Override
    public JSONWriter writeLineBreak () throws IOException {
        super.writeLineBreak ();
        return this;
    }

    @Override
    public JSONWriter flush() throws IOException {
        super.flush();
        return this;
    }


    // Text

    public @NotNull String escapeText ( @NotNull String text ) {
        return JSONEscaper.escapeString ( text );
    }

    @Override
    public JSONWriter escapeAndWriteText ( @NotNull String text ) throws IOException {
        super.escapeAndWriteText ( text );
        return this;
    }

    @Override
    public JSONWriter escapeAndWriteNullableText ( @Nullable String text ) throws IOException {
        super.escapeAndWriteNullableText ( text );
        return this;
    }

    @Override
    public JSONWriter writeText ( @NotNull String text, boolean escapeText ) throws IOException {
        super.writeText ( text, escapeText );
        return this;
    }


    // Indent

    @Override
    public JSONWriter increaseIndent() {
        super.increaseIndent();
        return this;
    }

    @Override
    public JSONWriter decreaseIndent() {
        super.decreaseIndent();
        return this;
    }

    @Override
    public JSONWriter writeIndent() throws IOException {
        super.writeIndent();
        return this;
    }


    // Document

    public JSONWriter writeDocumentStartLine() throws IOException {
        writeDocumentStart();
        writeLineBreak ();
        increaseIndent();
        return this;
    }

    public JSONWriter writeDocumentStart() throws IOException {
        write ( JSONConstants.OBJECT_START );
        return this;
    }

    public JSONWriter writeDocumentEndLine() throws IOException {
        return writeObjectEndLine ( true );
    }

    public JSONWriter writeDocumentEnd() throws IOException {
        return writeObjectEnd ( true );
    }


    // Object

    public JSONWriter writeObjectStartLine ( @NotNull String objectName ) throws IOException {
        return writeBlockStartLine ( () -> writeObjectStart ( objectName ) );
    }

    public JSONWriter writeObjectStartLine() throws IOException {
        return writeBlockStartLine ( this::writeObjectStart );
    }

    public JSONWriter writeObjectStart ( @NotNull String objectName ) throws IOException {
        writeName ( objectName );
        write ( JSONConstants.ASSIGN_SYMBOL );
        return writeObjectStart();
    }

    public JSONWriter writeObjectStart() throws IOException {
        write ( JSONConstants.OBJECT_START );
        return this;
    }

    public JSONWriter writeObjectEndLine ( boolean isLastObject ) throws IOException {
        return writeBlockEndLine ( JSONConstants.OBJECT_END, isLastObject );
    }

    public JSONWriter writeObjectEnd ( boolean isLastObject ) throws IOException {
        return writeBlockEnd ( JSONConstants.OBJECT_END, isLastObject );
    }


    // Array

    public JSONWriter writeArrayStartLine ( @NotNull String arrayName ) throws IOException {
        return writeBlockStartLine ( () -> writeArrayStart ( arrayName ) );
    }

    public JSONWriter writeArrayStartLine() throws IOException {
        return writeBlockStartLine ( this::writeArrayStart );
    }

    public JSONWriter writeArrayStart ( @NotNull String arrayName ) throws IOException {
        writeName ( arrayName );
        write ( JSONConstants.ASSIGN_SYMBOL );
        return writeArrayStart();
    }

    public JSONWriter writeArrayStart() throws IOException {
        write ( JSONConstants.ARRAY_START );
        return this;
    }

    public JSONWriter writeArrayEndLine ( boolean isLastObject ) throws IOException {
        return writeBlockEndLine ( JSONConstants.ARRAY_END, isLastObject );
    }

    public JSONWriter writeArrayEnd ( boolean isLastObject ) throws IOException {
        return writeBlockEnd ( JSONConstants.ARRAY_END, isLastObject );
    }


    // Attribute assignments

    public JSONWriter writeStringAttributeLine (
        @NotNull String name, @Nullable String value, boolean isLastAttribute ) throws IOException {

        return writeAttributeAssignmentLine ( name, () -> writeStringValue ( value ), isLastAttribute );
    }

    public JSONWriter writeStringAttribute ( @NotNull String name, @Nullable String value ) throws IOException {
        return writeAttributeAssignment ( name, () -> writeStringValue ( value ) );
    }

    public JSONWriter writeNumberAttributeLine (
        @NotNull String name, @Nullable Number value, boolean isLastAttribute ) throws IOException {

        return writeAttributeAssignmentLine ( name, () -> writeNumberValue ( value ), isLastAttribute );
    }

    public JSONWriter writeNumberAttribute ( @NotNull String name, @Nullable Number value ) throws IOException {
        return writeAttributeAssignment ( name, () -> writeNumberValue ( value ) );
    }

    public JSONWriter writeBooleanAttributeLine (
        @NotNull String name, @Nullable Boolean value, boolean isLastAttribute ) throws IOException {

        return writeAttributeAssignmentLine ( name, () -> writeBooleanValue ( value ), isLastAttribute );
    }

    public JSONWriter writeBooleanAttribute ( @NotNull String name, @Nullable Boolean value ) throws IOException {
        return writeAttributeAssignment ( name, () -> writeBooleanValue ( value ) );
    }

    public JSONWriter writeNullAttributeLine ( @NotNull String name, boolean isLastAttribute ) throws IOException {
        return writeAttributeAssignmentLine ( name, this::writeNullValue, isLastAttribute );
    }

    public JSONWriter writeNullAttribute ( @NotNull String name ) throws IOException {
        return writeAttributeAssignment ( name, this::writeNullValue );
    }


    // Name

    public JSONWriter writeName ( @NotNull String name ) throws IOException {
        write ( JSONConstants.QUOTE );
        write ( escapeText ( name ) );
        write ( JSONConstants.QUOTE );
        return this;
    }


    // Values

    public JSONWriter writeStringValue ( @Nullable String value ) throws IOException {

        if ( value == null ) {
            writeNullValue();
        } else {
            write ( JSONConstants.QUOTE );
            write ( escapeText ( value ) );
            write ( JSONConstants.QUOTE );
        }
        return this;
    }

    public JSONWriter writeNumberValue ( @Nullable Number value ) throws IOException {

        if ( value == null ) {
            writeNullValue();
        } else {
            write ( value.toString() );
        }
        return this;
    }

    public JSONWriter writeBooleanValue ( @Nullable Boolean value ) throws IOException {

        if ( value == null ) {
            writeNullValue();
        } else {
            write ( value ? "true" : "false" );
        }
        return this;
    }

    public JSONWriter writeNullValue() throws IOException {
        write ( JSONConstants.NULL_VALUE );
        return this;
    }


    // Private helpers

    private JSONWriter writeBlockStartLine ( @NotNull IORunnable runnable ) throws IOException {
        writeIndent();
        runnable.run ();
        writeLineBreak ();
        increaseIndent();
        return this;
    }

    private JSONWriter writeBlockEndLine ( char endChar, boolean isLastObject ) throws IOException {
        decreaseIndent();
        writeIndent();
        writeBlockEnd ( endChar, isLastObject );
        writeLineBreak ();
        return this;
    }

    private JSONWriter writeBlockEnd ( char endChar, boolean isLastObject ) throws IOException {
        write ( endChar );
        if ( ! isLastObject ) writeItemSeparator();
        return this;
    }

    private JSONWriter writeAttributeAssignmentLine (
        @NotNull String name, @NotNull IORunnable runnable, boolean isLastAttribute ) throws IOException {

        writeIndent();
        writeAttributeAssignment ( name, runnable );
        if ( ! isLastAttribute ) writeItemSeparator();
        writeLineBreak ();
        return this;
    }

    private JSONWriter writeAttributeAssignment ( @NotNull String name, @NotNull IORunnable runnable ) throws IOException {
        writeName ( name );
        write ( JSONConstants.ASSIGN_SYMBOL );
        runnable.run ();
        return this;
    }

    private JSONWriter writeItemSeparator() throws IOException {
        write ( JSONConstants.ITEM_SEPARATOR );
        return this;
    }

/*
    private JSONWriter writeSeparator() throws IOException {
        write ( ' ' );
        return this;
    }
*/
}
