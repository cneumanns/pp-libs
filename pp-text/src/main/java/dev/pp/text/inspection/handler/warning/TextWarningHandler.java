package dev.pp.text.inspection.handler.warning;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.message.TextInspectionWarning;

@Deprecated
public interface TextWarningHandler {

    void handleWarning ( @NotNull TextInspectionWarning error );

    @Nullable TextInspectionWarning firstWarning();
    @Nullable TextInspectionWarning lastWarning();

    // void throwIfNewWarnings ( @Nullable TextWarning initialLastWarning ) throws TextWarningException;
}
