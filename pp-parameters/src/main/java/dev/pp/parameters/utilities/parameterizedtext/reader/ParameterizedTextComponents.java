package dev.pp.parameters.utilities.parameterizedtext.reader;

import dev.pp.basics.annotations.Nullable;
import dev.pp.text.token.TextToken;

public record ParameterizedTextComponents (
    @Nullable TextToken parameters,
    @Nullable TextToken text ) {}
