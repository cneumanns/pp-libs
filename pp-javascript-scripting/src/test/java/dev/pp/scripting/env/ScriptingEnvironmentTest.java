package dev.pp.scripting.env;

import dev.pp.basics.utilities.file.TempFileUtils;
import dev.pp.text.utilities.file.TextFileReaderUtil;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ScriptingEnvironmentTest {

    @Test
    public void testEvaluateJavascriptExpressionToString() throws ScriptingException_OLD {

        ScriptingEnvironment_OLD_2 env = new JavaScriptScriptingEnvironment ( true );
        String expression = "1 + 1";
        String result = env.evaluateExpressionToString ( expression );
        assertEquals ( "2", result );
    }

    @Test
    public void testExecuteJavascriptScript() throws ScriptingException_OLD, IOException {

        String script = "writer.write ( 'Hello from script' );";
        Path path = TempFileUtils.createEmptyTempFile ( true );
        Writer writer = new FileWriter ( path.toFile() );

        // ScriptUtils.executeScriptWithWriter ( script, new PrintWriter ( System.out ), "js" );
        // ScriptingUtils.executeJavascriptWithWriter ( script, writer );
        ScriptingEnvironment_OLD_2 env = new JavaScriptScriptingEnvironment ( true );
        env.addBinding ( "writer", writer );
        env.executeScript ( script );
        writer.close();
        assertEquals ( "Hello from script", TextFileReaderUtil.readTextFromUTF8File ( path ) );
    }


    @Test
    public void testAddDefinitions() throws ScriptingException_OLD {

        String defs = """
            const twelve = 12;

            function foo() { return "foo"; }

            function bar() { return "bar"; }
            """;

        ScriptingEnvironment_OLD_2 env = new JavaScriptScriptingEnvironment ( true );
//        ScriptingEnvironment env = new ScriptingEnvironmentImplNotOk ();

/*
        String r = env.evaluateExpressionToString (
            ScriptingConstants.JAVASCRIPT_LANGUAGE_ID,
            defs + """

            foo()""",
            null, true );
        assertEquals ( "foo", r );
*/
        env.addDefinitions ( defs );

        String twelveResult = env.evaluateExpressionToString ( "twelve" );
        assertEquals ( "12", twelveResult );

        String fooResult = env.evaluateExpressionToString ( "foo()" );
        assertEquals ( "foo", fooResult );

        String barResult = env.evaluateExpressionToString ( "bar()" );
        assertEquals ( "bar", barResult );
    }
}
