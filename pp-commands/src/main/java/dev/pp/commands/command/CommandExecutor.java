package dev.pp.commands.command;

import dev.pp.basics.annotations.Nullable;
import dev.pp.parameters.parameters.MutableOrImmutableParameters;
import dev.pp.parameters.parameters.Parameters;
import dev.pp.parameters.parameters.ParametersCreator;
import dev.pp.parameters.parameterspecs.ParameterSpecs;

import java.util.Map;

public interface CommandExecutor<I,O> {

    default O execute (
        @Nullable Map<String, String> stringMap,
        @Nullable ParameterSpecs<I> parameterSpecs ) throws Exception {

/*
        Parameters<I> parameters = ParametersCreator.createFromStringMap ( stringMap, null, parameterSpecs );
        return execute ( parameters );
 */

        return execute (
            stringMap == null ? null : Parameters.createFromStringMap ( stringMap, null ),
            parameterSpecs );
    }

    default O execute (
        @Nullable MutableOrImmutableParameters<String> stringParameters,
        @Nullable ParameterSpecs<I> parameterSpecs ) throws Exception {

        Parameters<I> parameters = ParametersCreator.createFromStringParameters (
            stringParameters,
            parameterSpecs );
        return execute ( parameters );
    }

    O execute ( @Nullable Parameters<I> parameters ) throws Exception;
}
