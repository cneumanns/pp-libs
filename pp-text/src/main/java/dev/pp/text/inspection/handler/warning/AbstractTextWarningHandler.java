package dev.pp.text.inspection.handler.warning;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.message.TextInspectionWarning;

import java.util.function.Function;

@Deprecated
public abstract class AbstractTextWarningHandler implements TextWarningHandler {


    protected final @NotNull Function<TextInspectionWarning, String> warningToStringConverter;

    protected @Nullable TextInspectionWarning firstWarning;
    public TextInspectionWarning firstWarning() { return firstWarning; }

    protected @Nullable TextInspectionWarning lastWarning;
    public TextInspectionWarning lastWarning() { return lastWarning; }


    protected AbstractTextWarningHandler (
        @NotNull Function<TextInspectionWarning, String> warningToStringConverter ) {

        this.warningToStringConverter = warningToStringConverter;
        this.firstWarning = null;
        this.lastWarning = null;
    }

    protected AbstractTextWarningHandler () {
        this ( TextInspectionWarning::toString );
    }


    public void handleWarning ( @NotNull TextInspectionWarning warning ) {

        if ( firstWarning == null ) firstWarning = warning;
        lastWarning = warning;
        // warning.setHasBeenHandled ( true );

        handle ( warning, warningToStringConverter.apply ( warning ) );
    }

    public abstract void handle ( @NotNull TextInspectionWarning error, @NotNull String text );
}
