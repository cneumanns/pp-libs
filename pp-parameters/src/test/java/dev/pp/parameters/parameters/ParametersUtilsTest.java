package dev.pp.parameters.parameters;

import dev.pp.datatype.CommonDataTypes;
import dev.pp.parameters.cli.CLIArguments;
import dev.pp.parameters.cli.token.NameOrValueToken;
import dev.pp.parameters.parameterspec.ParameterSpec;
import dev.pp.parameters.parameterspecs.MutableParameterSpecs;
import dev.pp.text.inspection.InvalidTextException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParametersUtilsTest {

    @Test
    void createFromNameOrValueTokens() throws InvalidTextException {

        List<NameOrValueToken> nameOrValueTokens = CLIArguments.parseToNameOrValueTokens (
            new String[]{"-n1", "v1", "--n2", "v 2", "---n3", "", "-n4=-v4", "v5", "v 6", "", "-n8"}, 0 );
        assert nameOrValueTokens != null;
        MutableParameterSpecs<String> parameterSpecs = new MutableParameterSpecs<String>()
            .add ( ParameterSpec.ofString ( "n1", null ) )
            .add ( ParameterSpec.ofString ( "n2", null ) )
            .add ( ParameterSpec.ofString ( "-n3", null ) )
            .add ( ParameterSpec.ofString ( "n4", null  ) )
            .add ( ParameterSpec.builder ( "n5", CommonDataTypes.STRING ).position ( 0 ).build() )
            .add ( ParameterSpec.builder ( "n6", CommonDataTypes.STRING ).position ( 1 ).build() )
            .add ( ParameterSpec.builder ( "n7", CommonDataTypes.STRING ).position ( 2 ).build() )
            .add ( ParameterSpec.ofString ( "n8", null ) );
        Parameters<String> stringParameters = ParametersUtils.createFromNameOrValueTokens (
            nameOrValueTokens, null, parameterSpecs );
        assertEquals ( "{n1=v1, n2=v 2, -n3=, n4=-v4, n5=v5, n6=v 6, n7=, n8=true}", stringParameters.toMap().toString() );

        // errors

        // invalid name
        final List<NameOrValueToken> nameOrValueTokens2 = CLIArguments.parseToNameOrValueTokens (
            new String[]{"-invalid_name", "v1"}, 0 );
        assert nameOrValueTokens2 != null;
        assertThrows ( InvalidTextException.class, () -> ParametersUtils.createFromNameOrValueTokens (
            nameOrValueTokens2, null, parameterSpecs ) );

        // same parameter defined twice
        final List<NameOrValueToken> nameOrValueTokens3 = CLIArguments.parseToNameOrValueTokens (
            new String[]{"-n1", "v1", "-n1", "v2"}, 0 );
        assert nameOrValueTokens3 != null;
        assertThrows ( InvalidTextException.class, () -> ParametersUtils.createFromNameOrValueTokens (
            nameOrValueTokens3, null, parameterSpecs ) );
    }
}
