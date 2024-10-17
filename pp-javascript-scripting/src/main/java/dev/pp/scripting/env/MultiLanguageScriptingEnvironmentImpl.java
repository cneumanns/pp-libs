package dev.pp.scripting.env;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MultiLanguageScriptingEnvironmentImpl implements MultiLanguageScriptingEnvironment {


    private final @NotNull Map<String, ScriptingEnvironment_OLD_2> scriptingEnvironments;

    private final @NotNull ScriptingEnvironment_OLD_2 defaultScriptingEnvironment;


    public MultiLanguageScriptingEnvironmentImpl (
        @NotNull List<ScriptingEnvironment_OLD_2> scriptingEnvironments,
        @NotNull ScriptingEnvironment_OLD_2 defaultScriptingEnvironment ) {

        this.scriptingEnvironments = new HashMap<>();
        for ( ScriptingEnvironment_OLD_2 env : scriptingEnvironments ) {
            addEnv ( env );
        }

        this.defaultScriptingEnvironment = defaultScriptingEnvironment;
    }


    public @NotNull String getLanguageName() {
        // return "MultiLanguage";
        return defaultScriptingEnvironment.getLanguageName();
    }

    public void addBinding ( @NotNull String identifier, @NotNull Object object ) {
        defaultScriptingEnvironment.addBinding ( identifier, object );
    }

    public void addBinding ( @NotNull String identifier, @NotNull Object object, @NotNull String language ){

        ScriptingEnvironment_OLD_2 env = getEnv ( language );
        env.addBinding ( identifier, object );
    }

    public void addDefinitions ( @NotNull String definitions ) throws ScriptingException_OLD {
        defaultScriptingEnvironment.addDefinitions ( definitions );
    }

    public void addDefinitions ( @NotNull String definitions, @NotNull String language ) throws ScriptingException_OLD {

        ScriptingEnvironment_OLD_2 env = getEnv ( language );
        env.addDefinitions ( definitions );
    }

    public void executeScript ( @NotNull String script ) throws ScriptingException_OLD {
        defaultScriptingEnvironment.executeScript ( script );
    }

    public void executeScript ( @NotNull String script, @NotNull String language ) throws ScriptingException_OLD {

        ScriptingEnvironment_OLD_2 env = getEnv ( language );
        env.executeScript ( script );
    }

    // <T> @Nullable T evaluateExpression ( @NotNull String expression ) throws ScriptingException;
    public @Nullable Object evaluateExpression ( @NotNull String expression ) throws ScriptingException_OLD {
        return defaultScriptingEnvironment.evaluateExpression ( expression );
    }

    public @Nullable Object evaluateExpression ( @NotNull String expression, @NotNull String language ) throws ScriptingException_OLD {

        ScriptingEnvironment_OLD_2 env = getEnv ( language );
        return env.evaluateExpression ( expression );
    }

    public void close() {

        for ( ScriptingEnvironment_OLD_2 env : scriptingEnvironments.values() ) {
            env.close();
        }
    }


    private void addEnv ( @NotNull ScriptingEnvironment_OLD_2 env ) {

        // TODO check not exists
        scriptingEnvironments.put ( env.getLanguageName(), env );
    }

    private @NotNull ScriptingEnvironment_OLD_2 getEnv ( @NotNull String language ) {

        ScriptingEnvironment_OLD_2 result = getEnvOrNull ( language );
        Objects.requireNonNull ( result );
        return result;
    }

    private @Nullable ScriptingEnvironment_OLD_2 getEnvOrNull ( @NotNull String language ) {

        return scriptingEnvironments.get ( language );
    }
}
