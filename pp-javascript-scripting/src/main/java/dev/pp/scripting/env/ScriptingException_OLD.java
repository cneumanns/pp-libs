package dev.pp.scripting.env;

@Deprecated
public class ScriptingException_OLD extends Exception {

    public ScriptingException_OLD ( String message, Throwable cause ) {
        super ( message, cause );
    }

    public ScriptingException_OLD ( Throwable cause ) {
        this ( cause.getMessage(), cause );
    }
}
