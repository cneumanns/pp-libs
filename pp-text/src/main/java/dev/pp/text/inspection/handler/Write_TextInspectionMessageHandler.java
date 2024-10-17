package dev.pp.text.inspection.handler;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.os.OSIO;
import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.text.inspection.message.TextInspectionMessage;

import java.io.IOException;
import java.io.Writer;
import java.util.function.Function;

public class Write_TextInspectionMessageHandler extends AbstractTextInspectionMessageHandler {


    private final @NotNull Writer writer;
    private final @Nullable String messageSeparator;


    public Write_TextInspectionMessageHandler (
        @NotNull Writer writer,
        @NotNull Function<TextInspectionMessage, String> inspectionToStringConverter,
        @Nullable String messageSeparator ) {

        super ( inspectionToStringConverter );

        this.writer = writer;
        this.messageSeparator = messageSeparator;
    }

    public Write_TextInspectionMessageHandler ( @NotNull Writer writer ) {
        this ( writer, TextInspectionMessage::toString, StringConstants.OS_LINE_BREAK );
    }

    public Write_TextInspectionMessageHandler () {
        this ( OSIO.standardErrorUTF8Writer() );
    }


    public void handle ( @NotNull TextInspectionMessage message, @NotNull String text ) {

        try {
            writer.write ( text );
            if ( messageSeparator != null ) writer.write ( messageSeparator );
            writer.flush();
        } catch ( IOException e ) {
            throw new RuntimeException ( e );
        }
    }
}
