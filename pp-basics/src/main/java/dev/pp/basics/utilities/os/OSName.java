package dev.pp.basics.utilities.os;

import dev.pp.basics.annotations.NotNull;

public class OSName {

    public static @NotNull String name() {
        return System.getProperty ( "os.name" );
    }


    // Thanks to https://mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/

    public static boolean isWindowsOS() {
        return name ().toLowerCase().contains ( "win" );
    }

    public static boolean isUnixOS() {
        String name = name ().toLowerCase();
        return name.contains ( "nix" )
            || name.contains ( "nux" )
            || name.contains ( "aix" );
    }

    public static boolean isMacOS() {
        return name ().toLowerCase().contains ( "mac" );
    }
}
