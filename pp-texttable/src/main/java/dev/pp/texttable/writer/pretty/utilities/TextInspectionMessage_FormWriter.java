package dev.pp.texttable.writer.pretty.utilities;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.os.OSIO;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.text.inspection.handler.SimpleLogger_TextInspectionMessageHandler;
import dev.pp.text.inspection.handler.TextInspectionMessageHandler;
import dev.pp.text.inspection.handler.Write_TextInspectionMessageHandler;
import dev.pp.text.inspection.message.TextInspectionMessage;
import dev.pp.text.location.TextLocation;
import dev.pp.text.utilities.text.TextMarker;
import dev.pp.texttable.data.impls.FormField;
import dev.pp.texttable.data.impls.FormFields;
import dev.pp.texttable.data.impls.FormFields_TableDataProvider;
import dev.pp.texttable.writer.pretty.PrettyTableDataTextWriterImpl;
import dev.pp.texttable.writer.pretty.config.PrettyTableDataTextWriterColumnConfig;
import dev.pp.texttable.writer.pretty.config.PrettyTableDataTextWriterConfig;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

public class TextInspectionMessage_FormWriter {

    private static final int DEFAULT_LABEL_WIDTH = 10;
    private static final int DEFAULT_VALUE_WIDTH = 108;
    private static final int DEFAULT_WIDTH = DEFAULT_LABEL_WIDTH + 1 +DEFAULT_VALUE_WIDTH;


    // Log

    public static TextInspectionMessageHandler createLogMessageHandler() {
        return new SimpleLogger_TextInspectionMessageHandler ( TextInspectionMessage_FormWriter::messageToString );
    }

/*
    public static TextErrorHandler createLogErrorHandler() {
        return new SimpleLogger_TextErrorHandler ( TextInspectionMessage_FormWriter::messageToString );
    }

    public static TextWarningHandler createLogWarningHandler() {
        return new SimpleLogger_TextWarningHandler ( TextInspectionMessage_FormWriter::messageToString );
    }
 */


    // Write

    public static TextInspectionMessageHandler createWritingMessageHandler (
        @NotNull Writer writer,
        @Nullable String separator ) {

        return new Write_TextInspectionMessageHandler (
            writer,
            TextInspectionMessage_FormWriter::messageToString,
            separator );
    }

/*
    public static TextErrorHandler createWritingErrorHandler (
        @NotNull Writer writer,
        @Nullable String separator ) {

        return new Write_TextErrorHandler (
            writer,
            TextInspectionMessage_FormWriter::messageToString,
            separator );
    }

    public static TextWarningHandler createWritingWarningHandler (
        @NotNull Writer writer,
        @Nullable String separator ) {

        return new Write_TextWarningHandler (
            writer,
            TextInspectionMessage_FormWriter::messageToString,
            separator );
    }
 */

    public static TextInspectionMessageHandler createStderrMessageHandler () {

        return createWritingMessageHandler (
            OSIO.standardErrorUTF8Writer(),
            "-".repeat ( DEFAULT_WIDTH ) + StringConstants.OS_LINE_BREAK );
    }

/*
    public static TextErrorHandler createStderrErrorHandler () {

        return createWritingErrorHandler (
            OSIO.standardErrorUTF8Writer(),
            "-".repeat ( DEFAULT_WIDTH ) + StringConstants.OS_LINE_BREAK );
    }

    public static TextWarningHandler createStderrWarningHandler () {

        return createWritingWarningHandler (
            OSIO.standardErrorUTF8Writer(),
            "-".repeat ( DEFAULT_WIDTH ) + StringConstants.OS_LINE_BREAK );
    }
 */


    public static @NotNull String messageToString ( @NotNull TextInspectionMessage message ) {

        StringWriter sw = new StringWriter();
        try {
            writeMessage ( message, sw );
        } catch ( Exception e ) {
            throw new RuntimeException ( e );
        }
        return sw.toString();
    }

    public static void writeMessage ( @NotNull TextInspectionMessage message, @NotNull Writer writer ) throws Exception {

        FormFields<String> formFields = createFormFields ( message );

        FormFields_TableDataProvider<String> data = new FormFields_TableDataProvider<String> ( formFields );

        List<PrettyTableDataTextWriterColumnConfig<String>> columnConfigs = List.of (
            new PrettyTableDataTextWriterColumnConfig<> ( DEFAULT_LABEL_WIDTH, HTextAlign.LEFT, null ),
            new PrettyTableDataTextWriterColumnConfig<> ( DEFAULT_VALUE_WIDTH, HTextAlign.LEFT, null ) );

        new PrettyTableDataTextWriterImpl<FormField<String>, String>().write (
            data,
            writer,
            new PrettyTableDataTextWriterConfig<> ( columnConfigs ) );
    }

    private static FormFields<String> createFormFields ( @NotNull TextInspectionMessage message ) throws Exception {

        FormFields<String> formFields = new FormFields<String>();

        // String label = ew.getLabel();
        // formFields.add ( label, ew.getMessage() );
        formFields.add ( "Message", message.getMessage() );

        @Nullable TextLocation location = message.getLocation();
        if ( location != null ) {

            @Nullable String code  = message.textLine();
            if ( code != null ) {
                String text = message.getTextFragment ();
                int markerLength = text == null ? 1 : text.length();

                code = TextMarker.breakSingleTextLineAndInsertMarkerLine (
                    code, (int) location.getColumnNumber() - 1, TextMarker.DEFAULT_MARKER_IN_MARKER_LINE,
                    markerLength, true, 105 );
                formFields.add ( "Code", code );
            }

            formFields.add ( "Location", location.toString ( false, false ) );

            String parentLocations = location.parentLocationsToString ( false );
            if ( parentLocations != null ) {
                formFields.add ( "Call stack", parentLocations );
            }
        }

        String id = message.getId();
        if ( id != null ) {
            formFields.add ( message.label() + " id", id );
        }

        return formFields;
    }
}
