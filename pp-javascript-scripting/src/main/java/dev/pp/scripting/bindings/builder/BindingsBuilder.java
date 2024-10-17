package dev.pp.scripting.bindings.builder;

import dev.pp.basics.annotations.NotNull;
import dev.pp.scripting.bindings.ScriptingBinding;
import dev.pp.scripting.bindings.core.*;

import java.util.HashMap;
import java.util.Map;

public class BindingsBuilder {


    public static Map<String, Object> createWithCoreBindings() {
        return new BindingsBuilder().addCoreBindings().getResult();
    }


    private final @NotNull Map<String, Object> result;


    public BindingsBuilder() {
        this.result = new HashMap<>();

        // this.result.put ( "polyglot.js.allowAllAccess", true);
    }


    public boolean hasBinding ( @NotNull String name ) {
        return result.containsKey ( name );
    }

    public BindingsBuilder add ( @NotNull ScriptingBinding binding ) {
        add ( binding.bindingName (), binding );
        return this;
    }

    public BindingsBuilder add ( @NotNull String name, @NotNull Object object ) {

        if ( hasBinding ( name ) )
            throw new RuntimeException ( "Bindings with name '" + name + "' exists already." );

        result.put ( name, object );
        return this;
    }

    public BindingsBuilder addCoreBindings() {

        add ( new FileBinding() );
        add ( new URLBinding() );
        add ( new NumberBinding() );
        add ( new OSConfigBinding() );
        add ( new OSInBinding() );
        add ( new OSOutBinding() );
        add ( new OSErrBinding() );
        add ( new OSEnvBinding() );
        add ( new OSConsoleBinding() );
        add ( new OSCommandBinding() );
        add ( new WindowsCmdBinding() );
        add ( new TimeUtilsBinding() );
        add ( new GUIDialogsBinding() );

        return this;
    }

    public @NotNull Map<String, Object> getResult() {
        return result;
    }
}
