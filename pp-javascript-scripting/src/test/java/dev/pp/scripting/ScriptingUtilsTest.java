package dev.pp.scripting;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.file.TempFileUtils;
import dev.pp.text.utilities.file.TextFileReaderUtil;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ScriptingUtilsTest {

/*
    @Test
    public void testExecuteScriptWithContext() throws IOException {

        String script = "context.write ( 'Hello from script context 2' );";

        Writer writer = new FileWriter ( "C:\\temp\\scriptContextOutput.txt" );
        ScriptingContext context = new WriterScriptingContext ( writer );

        // ScriptUtils.executeScriptWithWriter ( script, new PrintWriter ( System.out ), "js" );
        ScriptingUtils.executeJavascriptWithContext ( script, context );
        writer.close();
    }
*/

    @Test
    public void test() throws IOException {

        String def = """
            const two = "2";

            function four() {
                return "4";
            }

            """;

        String exp = "two + four()";

        String result = ScriptingUtils.evaluateJavascriptStringExpression ( def + exp, null, true );
        assertEquals ( "24", result );

        String languageID = JavaScriptConstants.LANGUAGE_ID;
        Context context = ScriptingUtils.createContext ( true, null, languageID );
        context.eval ( languageID, def );
        @NotNull Value value = context.eval ( languageID, exp );
        assertEquals ( "24", value.toString() );

        String script_1 = """
            const c1 = "c1";
            c1
            """;
        value = context.eval ( languageID, script_1 );
        assertEquals ( "c1", value.toString() );

        String script_2 = """
            (function f2() {
                const c1 = "c12";
                return c1;
            })();
            // f2();
            """;
        value = context.eval ( languageID, script_2 );
        assertEquals ( "c12", value.toString() );

        String script_3 = """
            const c1 = "c13";
            c1
            """;
        // error because 'c1' is defined already in 'script_1'
        assertThrows ( PolyglotException.class, () -> context.eval ( languageID, script_3 ) );
    }

    @Test
    public void evaluateJavascriptExpression() {

        String expression = "\"foo\"";
        String stringResult = ScriptingUtils.evaluateJavascriptExpression ( expression, null, true );
        assertEquals ( "foo", stringResult );

        expression = "1 + 1";
        int intResult = ScriptingUtils.evaluateJavascriptExpression ( expression, null, true );
        assertEquals ( 2, intResult );
    }

    @Test
    public void evaluateJavascriptStringExpression() {

        String expression = "\"foo\"";
        String result = ScriptingUtils.evaluateJavascriptStringExpression ( expression, null, true );
        assertEquals ( "foo", result );

        expression = "null";
        result = ScriptingUtils.evaluateJavascriptStringExpression ( expression, null, true );
        assertNull ( result );

        expression = """
            const s1 = "a";
            const s2 = "b";
            s1 + s2;
            """;
        result = ScriptingUtils.evaluateJavascriptStringExpression ( expression, null, true );
        assertEquals ( "ab", result );

        expression = """
            const s1 = "a";
            const s2 = "b";
            // null;
            """;
        result = ScriptingUtils.evaluateJavascriptStringExpression ( expression, null, true );
        assertNull ( result );

        expression = """
            (function f2() {
                const s1 = "s1";
                const s2 = "s2";
                return s1 + s2;
            })();
            // f2();
            """;
        result = ScriptingUtils.evaluateJavascriptStringExpression ( expression, null, true );
        assertEquals ( "s1s2", result );
    }

    @Test
    public void testExecuteJavascriptScript() throws IOException {

        String script = "writer.write ( 'Hello from script' );";
        Path path = TempFileUtils.createEmptyTempFile ( true );
        Writer writer = new FileWriter ( path.toFile() );

        // ScriptUtils.executeScriptWithWriter ( script, new PrintWriter ( System.out ), "js" );
        // ScriptingUtils.executeJavascriptWithWriter ( script, writer );
        ScriptingUtils.executeJavascriptScript ( script, Map.of ( "writer", writer ), true );
        writer.close();
        assertEquals ( "Hello from script", TextFileReaderUtil.readTextFromUTF8File ( path ) );
    }

/*
    @Test
    public void testExecuteJavascriptScriptWithReturn() throws IOException {

        String script = "return \"Hi\";";
        // String script = "\"Hi\"";
        Object returnValue = ScriptingUtils.executeJavascriptScriptWithReturn ( script, null, true );
        assertEquals ( "Hi", returnValue.toString() );
    }

 */
}
