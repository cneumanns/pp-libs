package dev.pp.text.inspection.handler;

import dev.pp.basics.annotations.NotNull;
import dev.pp.text.inspection.message.TextInspectionMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Collect_TextInspectionMessageHandler extends AbstractTextInspectionMessageHandler {


    private final @NotNull List<TextInspectionMessage> textInspections;


    public Collect_TextInspectionMessageHandler () {
        this.textInspections = new ArrayList<>();
    }


    public void handle ( @NotNull TextInspectionMessage message, @NotNull String text ) {
        textInspections.add ( message );
    }

    public List<TextInspectionMessage> list() {
        return Collections.unmodifiableList ( textInspections );
    }


    // queries

/* TODO
    public @Nullable List<TextError> errorsAndWarnings() {

        if ( hasErrors() || hasWarnings() ) {
            List<TextErrorOrWarningOld> list = new ArrayList<>();
            list.addAll ( errors );
            list.addAll ( warnings );
            return list;
        } else {
            return null;
        }
    }

    public @Nullable List<TextErrorOld> errors() {

        if ( hasErrors() ) {
            return Collections.unmodifiableList ( errors );
        } else {
            return null;
        }
    }

    public @Nullable List<TextWarningOld> warnings() {

        if ( hasWarnings() ) {
            return Collections.unmodifiableList ( warnings );
        } else {
            return null;
        }
    }


    public TextErrorOrWarningOld firstErrorOrWarning() {

        if ( hasErrors() ) {
            return errors.get ( 0 );
        } else if ( hasWarnings() ) {
            return warnings.get ( 0 );
        } else {
            return null;
        }
    }

    @Override
    public TextErrorOld firstError() {

        if ( hasErrors() ) {
            return errors.get ( 0 );
        } else {
            return null;
        }
    }

    public TextWarningOld firstWarning() {

        if ( hasWarnings() ) {
            return warnings.get ( 0 );
        } else {
            return null;
        }
    }



    public boolean hasErrorsOrWarnings() { return hasErrors() || hasWarnings(); }

    public boolean hasErrors() { return ! errors.isEmpty(); }

    public boolean hasWarnings() { return ! warnings.isEmpty(); }


    public int errorOrWarningCount() { return errorCount() + warningCount(); }

    public int errorCount() { return errors.size(); }

    public int warningCount() { return warnings.size(); }
*/
}
