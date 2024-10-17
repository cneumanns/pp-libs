package dev.pp.scripting.bindings.core;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.os.OSName;
import dev.pp.basics.utilities.string.StringConstants;
import dev.pp.scripting.bindings.ScriptingBinding;

public class OSConfigBinding implements ScriptingBinding {

    public OSConfigBinding () {}


    public @NotNull String bindingName () { return "OSConfig"; }

    /**
     * Get the name of the current operating system.
     * @return The name of the current operating system.
     */
    public @NotNull String OSName() { return OSName.name (); }

    /**
     * Check if the current operating system is Windows.
     * @return 'true' if the current operating system is Windows, otherwise returns 'false '.
     */
    public boolean isWindows() { return OSName.isWindowsOS(); }

    /**
     * Get the line break character or character sequence used on the current operating system.
     * @return A string representing a line break (LF on Unix/Linux; CRLF on Windows).
     */
    public @NotNull String lineBreak() { return StringConstants.OS_LINE_BREAK; }
}
