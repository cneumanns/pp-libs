package dev.pp.basics.utilities.os;

import dev.pp.basics.annotations.NotNull;

import java.nio.file.Path;

public class OSDirectories {

    public static final @NotNull String DIRECTORY_SEPARATOR = System.getProperty ( "file.separator" );

    public static final @NotNull Path USER_HOME_DIRECTORY = userHomeDir();

    public static final @NotNull Path TEMPORARY_FILES_DIRECTORY = Path.of ( System.getProperty ( "java.io.tmpdir" ) );

    public static @NotNull Path currentWorkingDirectory() {
        return Path.of ( System.getProperty ( "user.dir" ) );
    }

    private static @NotNull Path userHomeDir() {

        String result = System.getProperty ( "user.home" );
        if ( result == null || result.isEmpty() || result.equals ( "?" ) ) {
            System.err.println (
                "ERROR:\nThe user home directory could not be defined.\nSystem.getProperty ( \"user.home\" ) returns \""
                + result + "\".\nOn *nix systems you can use the \"XDG_CONFIG_HOME\" environment variable to explicitly set the user home directory.");
            result = "?";
        }

        return Path.of ( result );
    }
}
