package dev.pp.scripting.bindings.core;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.os.OSEnvUtils;
import dev.pp.scripting.bindings.ScriptingBinding;

public class OSEnvBinding implements ScriptingBinding {

    public OSEnvBinding () {}


    public @NotNull String bindingName () { return "OSEnvUtils"; }

    public @Nullable String getVarOrNull ( @NotNull String varName ) {

        return OSEnvUtils.getVarOrNull ( varName );
    }

    public @NotNull String getVarOrDefault ( @NotNull String varName, @NotNull String defaultValue ) {

        return OSEnvUtils.getVarOrDefault ( varName, defaultValue );
    }

    public @NotNull String getVar ( @NotNull String varName ) {

        return OSEnvUtils.getVar ( varName );
    }
}
