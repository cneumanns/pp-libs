package dev.pp.text.inspection.handler;

import dev.pp.basics.annotations.NotNull;
import dev.pp.text.inspection.message.TextInspectionMessage;

public class DoNothing_TextInspectionMessageHandler extends AbstractTextInspectionMessageHandler {


    public DoNothing_TextInspectionMessageHandler() {
        super();
    }

    public void handle ( @NotNull TextInspectionMessage message, @NotNull String text ) {
        // do nothing
    }
}
