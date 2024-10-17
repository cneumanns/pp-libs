package dev.pp.commands.picocli;

import dev.pp.basics.annotations.NotNull;
import dev.pp.parameters.parameterspec.ParameterSpec;
import picocli.CommandLine.Model.OptionSpec;
import picocli.CommandLine.Model.PositionalParamSpec;

import java.util.*;

public class ParameterSpecToPicocliConverter {

    public static @NotNull OptionSpec convertToStringOption ( @NotNull ParameterSpec<?> parameterSpec ) {

        List<String> names = new ArrayList<>();
        names.add ( "--" + parameterSpec.getName() );
        Set<String> alternativeNames = parameterSpec.getAlternativeNames();
        if ( alternativeNames != null ) {
            for ( String alternativeName : alternativeNames ) {
                names.add ( "-" + alternativeName );
            }
        }

        return OptionSpec.builder ( names.toArray ( new String[0] ) )
            .type ( String.class )
            .description ( parameterSpec.documentationDescription () )
            .required ( parameterSpec.isRequired() )
            .build();
    }

    public static @NotNull PositionalParamSpec convertToStringPositional (
        @NotNull ParameterSpec<?> parameterSpec ) {

        Integer index = parameterSpec.getPosition ();
        assert index != null;

        return PositionalParamSpec.builder()
            .type ( String.class )
            .description ( parameterSpec.documentationDescription () )
            .required ( parameterSpec.isRequired () )
            .paramLabel ( parameterSpec.getName ().toUpperCase () )
            .index ( String.valueOf ( index ) )
            .arity ( "1" )
            .build ();
    }
}
