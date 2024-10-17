package dev.pp.parameters.utilities;

import dev.pp.parameters.cli.CLIArguments;
import dev.pp.parameters.cli.token.NameOrValueToken;
import dev.pp.text.inspection.InvalidTextException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CLIArgumentsTest {

    @Test
    public void testParseToNameOrValueTokens() throws InvalidTextException {

        // name/value pairs
        String[] arguments = new String[]{"-n1", "v1", "--n2", "v 2", "---n3", ""};
        assertEquals ( "n:n1, v:v1, n:n2, v:v 2, n:-n3, v:", runTest ( arguments ) );

        // empty/null
        arguments = new String[]{"-n1", "", "-n2", null};
        assertEquals ( "n:n1, v:, n:n2, v:", runTest ( arguments ) );

        // only names (boolean flags)
        arguments = new String[]{"-n1", "-n2", "--n 3", "---n4"};
        assertEquals ( "n:n1, n:n2, n:n 3, n:-n4", runTest ( arguments ) );

        // only values (positional arguments)
        arguments = new String[]{"v1", "v 2", "", null};
        assertEquals ( "v:v1, v:v 2, v:, v:", runTest ( arguments ) );

        // =
        arguments = new String[]{"-n1", "-n2=no", "--n3=", "-n4=--not true = false"};
        assertEquals ( "n:n1, n:n2, v:no, n:n3, v:, n:n4, v:--not true = false", runTest ( arguments ) );
        final String[] arguments2 = new String[]{"-n1=false", "-=true"};
        assertThrows ( InvalidTextException.class, () -> runTest ( arguments2 ) );

        // mixed named/positional arguments
        arguments = new String[]{"-n1", "v1", "v2", "-n3", "", "v4", ""};
        assertEquals ( "n:n1, v:v1, v:v2, n:n3, v:, v:v4, v:", runTest ( arguments ) );

        // -- to indicate that subsequent tokens are all values
        arguments = new String[]{"-n1", "v1", "--", "v2", "-v3", "--v4", "", null};
        assertEquals ( "n:n1, v:v1, v:v2, v:-v3, v:--v4, v:, v:", runTest ( arguments ) );

        // startIndex
        arguments = new String[]{"-n1", "v1", "--n2", "v 2"};
        assertEquals ( "n:n2, v:v 2", runTest ( arguments, 2 ) );
    }

    private String runTest ( String[] arguments ) throws InvalidTextException {
        return runTest ( arguments, 0 );
    }

    private String runTest ( String[] arguments, int startIndex ) throws InvalidTextException {

        List<NameOrValueToken> list = CLIArguments.parseToNameOrValueTokens ( arguments, startIndex );
        assert list != null;
        return NameOrValueToken.listToString ( list );
    }
}
