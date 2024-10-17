package dev.pp.scripting.env;

import dev.pp.scripting.JavaScriptConstants;
import dev.pp.scripting.ScriptingUtils;
import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.scripting.bindings.builder.BindingsBuilder;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.util.Map;

public class JavaScriptScriptingEnvironment implements ScriptingEnvironment_OLD_2 {

    private static final @NotNull String LANGUAGE_ID = "js";
    protected static final @NotNull String JAVASCRIPT_LANGUAGE_ID = "js";

    private final Context context;

    int scriptFunctionCount;

    private JavaScriptScriptingEnvironment (
        @Nullable Map<String, Object> bindings,
        boolean allowAllAccess ) {

        this.context = ScriptingUtils.createContext ( allowAllAccess, bindings, LANGUAGE_ID );
        this.scriptFunctionCount = 1;
    }

    public JavaScriptScriptingEnvironment ( boolean allowAllAccess ) {

        this ( BindingsBuilder.createWithCoreBindings(), allowAllAccess );
    }


    public @NotNull String getLanguageName() {
        return JavaScriptConstants.LANGUAGE_NAME;
    }

    public void addBinding ( @NotNull String identifier, @NotNull Object object ) {

        Value bindings = context.getBindings ( LANGUAGE_ID );
        if ( bindings.hasMember ( identifier ) )
            throw new RuntimeException ( "Binding '" + identifier + "' has already been added." );

        bindings.putMember ( identifier, object );
    }

    public void addDefinitions ( @NotNull String definitions ) throws ScriptingException_OLD {

        evaluate ( definitions );
    }

    public void executeScript ( @NotNull String script ) throws ScriptingException_OLD {

        // embed script in a function to create a local scope for constants and variables
        StringBuilder sb = new StringBuilder();
        sb.append ( "(function " )
            .append ( "script__" )
            .append ( scriptFunctionCount )
            .append ( "() {" )
            .append ( StringConstants.OS_LINE_BREAK )
            .append ( script )
            .append ( StringConstants.OS_LINE_BREAK )
            .append ( "})();" );

        scriptFunctionCount = scriptFunctionCount + 1;

        evaluate ( sb.toString() );
    }

    public @Nullable Value evaluateExpression ( @NotNull String expression ) throws ScriptingException_OLD {

        return evaluate ( expression );
    }

    public @Nullable String evaluateExpressionToString ( @NotNull String expression ) throws ScriptingException_OLD {

        @NotNull Value value = evaluate ( expression );
        if ( value.isNull() ) {
            return null;
        } else {
            return value.toString();
        }
    }

    public void close() { context.close(); }

    private Value evaluate ( String code ) throws ScriptingException_OLD {
        try {
            return context.eval ( LANGUAGE_ID, code );
        } catch ( Exception e ) {
            // TODO improve
            throw new ScriptingException_OLD ( e );
        }
    }
}
