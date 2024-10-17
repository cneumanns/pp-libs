package dev.pp.parameters.utilities;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameters.MutableOrImmutableParameters;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.text.inspection.message.TextInspectionError;
import dev.pp.text.inspection.InvalidTextUtil;

import java.io.IOException;

public class InvalidTextUtil2 {

    public static void showInEditor (
        @NotNull TextInspectionError error,
        @NotNull MutableOrImmutableParameters<?> parameters,
        @NotNull ParameterSpec<?> openFileOSCommandTemplateParameterSpec ) throws IOException {

        @Nullable String template = parameters.castedValue ( openFileOSCommandTemplateParameterSpec );
        if ( template == null ) return;

        InvalidTextUtil.showInEditor ( error, template );
    }
}
