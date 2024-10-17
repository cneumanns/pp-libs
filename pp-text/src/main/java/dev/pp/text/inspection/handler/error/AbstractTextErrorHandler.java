package dev.pp.text.inspection.handler.error;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.message.TextInspectionError;

import java.util.function.Function;

@Deprecated
public abstract class AbstractTextErrorHandler implements TextErrorHandler {


    protected final @NotNull Function<TextInspectionError, String> errorToStringConverter;

    protected @Nullable TextInspectionError firstError;
    public TextInspectionError firstError() { return firstError; }

    protected @Nullable TextInspectionError lastError;
    public TextInspectionError lastError() { return lastError; }


    protected AbstractTextErrorHandler (
        @NotNull Function<TextInspectionError, String> errorToStringConverter ) {

        this.errorToStringConverter = errorToStringConverter;
        this.firstError = null;
        this.lastError = null;
    }

    protected AbstractTextErrorHandler () {
        this ( TextInspectionError::toString );
    }


    public void handleError ( @NotNull TextInspectionError error ) {

        if ( firstError == null ) firstError = error;
        lastError = error;
        // error.setHasBeenHandled ( true );

        handle ( error, errorToStringConverter.apply ( error ) );
    }

    public abstract void handle ( @NotNull TextInspectionError error, @NotNull String text );

/*
    public void throwIfNewErrors ( @Nullable TextError initialLastError ) throws TextErrorException {

        if ( ! hasNewErrors ( initialLastError ) ) return;

        assert lastError != null;
        throw new TextErrorException ( lastError );
    }

    private boolean hasNewErrors ( @Nullable TextError initialLastError ) {
        return lastError != initialLastError;
    }
 */
}
