package dev.pp.scripting.env;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.scripting.ScriptingUtils;

import java.util.Map;

@Deprecated
public class ScriptingEnvironmentImpl_OLD_1 implements ScriptingEnvironment_OLD_1 {


    // Hack:
    // Sharing code between different contexts seems not to work in GraalVM version 21.3.0
    // This implementation includes all definitions in scripts and expressions -> much slower, probably
    private String definitions = "";


    public ScriptingEnvironmentImpl_OLD_1 () {}


    public void addDefinitions (
        @NotNull String languageID,
        @NotNull String code,
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess ) {

        ScriptingUtils.parse ( languageID, code, bindings, allowAllAccess );

        definitions = definitions + StringConstants.OS_LINE_BREAK + code;
    }

    public void executeScript (
        @NotNull String languageID,
        @NotNull String script,
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess ) {

        ScriptingUtils.executeScript (
            languageID, definitions + StringConstants.OS_LINE_BREAK + script, bindings, allowAllAccess );
    }

    public @Nullable String evaluateExpressionToString (
        @NotNull String languageID,
        @NotNull String expression,
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess ) {

        return ScriptingUtils.evaluateStringExpression (
            languageID, definitions + StringConstants.OS_LINE_BREAK + expression, bindings, allowAllAccess );
    }
}
