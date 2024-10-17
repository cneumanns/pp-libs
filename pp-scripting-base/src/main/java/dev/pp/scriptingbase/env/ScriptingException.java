package dev.pp.scriptingbase.env;

public class ScriptingException extends Exception {

    public ScriptingException ( String message, Throwable cause ) {
        super ( message, cause );
    }

    public ScriptingException ( Throwable cause ) {
        this ( cause.getMessage(), cause );
    }
}
