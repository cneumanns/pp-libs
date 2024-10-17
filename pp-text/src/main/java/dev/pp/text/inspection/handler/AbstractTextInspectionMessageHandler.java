package dev.pp.text.inspection.handler;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.message.TextInspectionError;
import dev.pp.text.inspection.message.TextInspectionMessage;

import java.util.function.Function;

public abstract class AbstractTextInspectionMessageHandler implements TextInspectionMessageHandler {


    protected final @NotNull Function<TextInspectionMessage, String> inspectionToStringConverter;

    protected @Nullable TextInspectionError firstError;
    public TextInspectionError firstError() { return firstError; }

    protected @Nullable TextInspectionError lastError;
    public TextInspectionError lastError() { return lastError; }

//    protected @Nullable TextError firstErrorAfterMark;
//    protected boolean hasErrorMark;


    protected AbstractTextInspectionMessageHandler (
        @NotNull Function<TextInspectionMessage, String> inspectionToStringConverter ) {

        this.inspectionToStringConverter = inspectionToStringConverter;

        this.firstError = null;
        this.lastError = null;
    }

    protected AbstractTextInspectionMessageHandler () {
        this ( TextInspectionMessage::toString );
    }


    @Override
    public void handleMessage ( @NotNull TextInspectionMessage message ) {

        if ( message instanceof TextInspectionError error ) {
            if ( firstError == null ) firstError = error;
            lastError = error;
        }

        handle ( message, inspectionToStringConverter.apply ( message ) );
    }

    public abstract void handle ( @NotNull TextInspectionMessage message, @NotNull String text );

    @Override
    public @Nullable TextInspectionError newestErrorAfterInitialLastError ( @Nullable TextInspectionError initialLastError ) {
        return lastError != initialLastError ? lastError : null;
    }

    @Override
    public boolean hasNewErrors ( @Nullable TextInspectionError initialLastError ) {
        return lastError != initialLastError;
    }

/*
    public void setErrorMark() {
        hasErrorMark = true;
        firstErrorAfterMark = null;
    }

    public @Nullable TextError firstErrorAfterMark() { return firstErrorAfterMark; }
 */
}
