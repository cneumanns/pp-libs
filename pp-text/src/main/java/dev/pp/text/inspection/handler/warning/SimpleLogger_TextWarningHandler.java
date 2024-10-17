package dev.pp.text.inspection.handler.warning;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.SimpleLogger;
import dev.pp.text.inspection.message.TextInspectionWarning;

import java.util.function.Function;

@Deprecated
public class SimpleLogger_TextWarningHandler extends AbstractTextWarningHandler {


    public SimpleLogger_TextWarningHandler ( @NotNull Function<TextInspectionWarning, String> warningToStringConverter ) {
        super ( warningToStringConverter );
    }


    public void handle ( @NotNull TextInspectionWarning warning, @NotNull String text ) {
        SimpleLogger.warning ( text );
    }
}
