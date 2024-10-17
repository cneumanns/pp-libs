package dev.pp.texttable.writer.pretty.utilities;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.HTextAlign;
import dev.pp.texttable.data.impls.FormField;
import dev.pp.texttable.data.impls.FormFields;
import dev.pp.texttable.data.impls.FormFields_TableDataProvider;
import dev.pp.texttable.writer.pretty.PrettyTableDataTextWriterImpl;
import dev.pp.texttable.writer.pretty.config.PrettyTableDataTextWriterColumnConfig;
import dev.pp.texttable.writer.pretty.config.PrettyTableDataTextWriterConfig;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

public class TextFormWriterUtil {

    public static <V> void writeFormFields (
        @NotNull FormFields<V> formFields,
        int labelWidth,
        int valueWidth,
        @NotNull Writer writer ) throws IOException {

        FormFields_TableDataProvider<V> data = new FormFields_TableDataProvider<> ( formFields );

        List<PrettyTableDataTextWriterColumnConfig<String>> columnConfigs = List.of (
            new PrettyTableDataTextWriterColumnConfig<> ( labelWidth, HTextAlign.LEFT, null ),
            new PrettyTableDataTextWriterColumnConfig<> ( valueWidth, HTextAlign.LEFT, null ) );

        new PrettyTableDataTextWriterImpl<FormField<V>, String>().write (
            data,
            writer,
            new PrettyTableDataTextWriterConfig<> ( columnConfigs ) );
    }

    public static <V> void appendFormFields (
        @NotNull FormFields<V> formFields,
        int labelWidth,
        int valueWidth,
        @NotNull StringBuilder sb ) {

        try ( Writer writer = new StringWriter () ) {
            writeFormFields ( formFields, labelWidth, valueWidth, writer );
            sb.append ( writer.toString() );
        } catch ( IOException e ) {
            throw new RuntimeException ( "Should never happen." );
        }
    }


    public static <V> void writeFormField (
        @NotNull FormField<V> formField,
        int labelWidth,
        int valueWidth,
        @NotNull Writer writer ) throws IOException {

        writeFormFields (
            new FormFields<V>().add ( formField ),
            labelWidth, valueWidth, writer );
    }

    public static <V> void appendFormField (
        @NotNull FormField<V> formField,
        int labelWidth,
        int valueWidth,
        @NotNull StringBuilder sb ) {

        appendFormFields (
            new FormFields<V>().add ( formField ),
            labelWidth, valueWidth, sb );
    }

    public static void writeFormField (
        @NotNull String label,
        @Nullable String value,
        int labelWidth,
        int valueWidth,
        @NotNull Writer writer ) throws IOException {

        writeFormField (
            new FormField<> ( label, value ),
            labelWidth, valueWidth, writer );
    }

    public static void appendFormField (
        @NotNull String label,
        @Nullable String value,
        int labelWidth,
        int valueWidth,
        @NotNull StringBuilder sb ) {

        appendFormFields (
            new FormFields<String>().add ( new FormField<> ( label, value ) ),
            labelWidth, valueWidth, sb );
    }
}
