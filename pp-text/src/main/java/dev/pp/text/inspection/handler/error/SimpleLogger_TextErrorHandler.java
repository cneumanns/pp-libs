package dev.pp.text.inspection.handler.error;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.SimpleLogger;
import dev.pp.text.inspection.message.TextInspectionError;

import java.util.function.Function;

@Deprecated
public class SimpleLogger_TextErrorHandler extends AbstractTextErrorHandler {


    public SimpleLogger_TextErrorHandler ( @NotNull Function<TextInspectionError, String> errorToStringConverter ) {
        super ( errorToStringConverter );
    }


    public void handle ( @NotNull TextInspectionError error, @NotNull String text ) {
        SimpleLogger.error ( text );
    }
}
