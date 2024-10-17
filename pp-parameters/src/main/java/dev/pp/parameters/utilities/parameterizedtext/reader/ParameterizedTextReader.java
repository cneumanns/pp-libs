package dev.pp.parameters.utilities.parameterizedtext.reader;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.text.inspection.InvalidTextException;
import dev.pp.text.resource.TextResource;

import java.io.IOException;
import java.io.Reader;

public interface ParameterizedTextReader {

    @NotNull ParameterizedTextComponents read (
        @NotNull Reader reader,
        @Nullable TextResource resource,
        @Nullable Integer lineOffset,
        @Nullable Integer columnOffset ) throws IOException, InvalidTextException;
}
