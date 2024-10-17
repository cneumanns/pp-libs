package dev.pp.scripting.bindings;

import dev.pp.basics.annotations.NotNull;

public interface ScriptingBinding {

    // changing the name of this method requires the name also to be changed for ScriptingAPIDocCreator.BINDING_NAME_METHOD_NAME
    @NotNull String bindingName ();
}
