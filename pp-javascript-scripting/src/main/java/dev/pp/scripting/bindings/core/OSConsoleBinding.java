package dev.pp.scripting.bindings.core;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.os.OSConsoleUtils;
import dev.pp.scripting.bindings.ScriptingBinding;

public class OSConsoleBinding implements ScriptingBinding {

    public OSConsoleBinding () {}


    public @NotNull String bindingName () { return "OSConsole"; }

    public @Nullable String askString ( @NotNull String message ) {

        return OSConsoleUtils.askString ( message );
    }
}
