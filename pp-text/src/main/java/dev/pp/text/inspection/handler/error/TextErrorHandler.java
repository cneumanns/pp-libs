package dev.pp.text.inspection.handler.error;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.message.TextInspectionError;

@Deprecated
public interface TextErrorHandler {

    void handleError ( @NotNull TextInspectionError error );

    @Nullable TextInspectionError firstError();
    @Nullable TextInspectionError lastError();

    // void throwIfNewErrors ( @Nullable TextError initialLastError ) throws TextErrorException;
}
