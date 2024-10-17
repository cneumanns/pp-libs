package dev.pp.scripting.bindings.core;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.os.process.WindowsScript;
import dev.pp.scripting.bindings.ScriptingBinding;

import java.io.IOException;

public class WindowsCmdBinding implements ScriptingBinding {

    public WindowsCmdBinding() {}


    public @NotNull String bindingName () { return "WinCmdUtils"; }

    public @Nullable String getInstructionsOutput (
        @NotNull String instructions,
        @Nullable String[] arguments,
        @Nullable String input ) throws IOException, InterruptedException {

        return WindowsScript.textPipeWithCmdScript ( instructions, arguments, input );
    }

}
