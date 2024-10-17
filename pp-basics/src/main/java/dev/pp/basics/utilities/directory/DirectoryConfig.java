package dev.pp.basics.utilities.directory;

import java.nio.file.Path;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.os.OSDirectories;
import dev.pp.basics.utilities.os.OSName;

public class DirectoryConfig {

    /* see:
        https://specifications.freedesktop.org/basedir-spec/basedir-spec-latest.html
            $XDG_DATA_HOME   base directory for user-specific data.         Default: $HOME/.local/share
            $XDG_CONFIG_HOME base directory for user-specific config files. Default: $HOME/.config

        https://github.com/harawata/appdirs
            common paths used to store application specific user data:
                On Windows >= 7 : C:\Users\<Account>\AppData\<AppAuthor>\<AppName>
                On Unix/Linux : /home/<account>/.local/share/<AppName>
                On Mac OS X : /Users/<Account>/Library/Application Support/<AppName>
                On Windows XP : C:\Documents and Settings\<Account>\Application Data\Local Settings\<AppAuthor>\<AppName>

            Windows 7 (username = ave)
                User data dir (roaming): C:\Users\ave\AppData\Roaming\harawata\myapp\1.2.3
                User config dir (roaming): C:\Users\ave\AppData\Roaming\harawata\myapp\1.2.3

            Linux (username = ave, with no XDG environment variables defined)
                User data dir (roaming): /home/ave/.local/share/myapp/1.2.3
                User config dir (roaming): /home/ave/.config/myapp/1.2.3

            Mac OS X (username = ave)
                User data dir (roaming): /Users/ave/Library/Application Support/myapp/1.2.3
                User config dir (roaming): /Users/ave/Library/Preferences/myapp/1.2.3

            AppDirs respects XDG Base Directory Specification if variables are defined.
                Returns XDG_DATA_HOME for user data directory.
                Returns XDG_CONFIG_HOME for user config directory.
                roaming parameter has no effect on Unix/Linux.
     */

    public static @NotNull Path userDataDirectoryForApplication (
        @Nullable String applicationVendor,
        @NotNull String applicationName,
        @Nullable Integer applicationMajorVersionNumber,
        @Nullable Integer applicationMinorVersionNumber ) {

        return userDirectoryForApplication ( userDataDirectory(),
            applicationVendor, applicationName, applicationMajorVersionNumber, applicationMinorVersionNumber );
    }

    public static @NotNull Path userConfigDirectoryForApplication (
        @Nullable String applicationVendor,
        @NotNull String applicationName,
        @Nullable Integer applicationMajorVersionNumber,
        @Nullable Integer applicationMinorVersionNumber ) {

        return userDirectoryForApplication ( userConfigDirectory(),
            applicationVendor, applicationName, applicationMajorVersionNumber, applicationMinorVersionNumber );
    }

    private static @NotNull Path userDirectoryForApplication (
        @NotNull Path rootDirectory,
        @Nullable String applicationVendor,
        @NotNull String applicationName,
        @Nullable Integer applicationMajorVersionNumber,
        @Nullable Integer applicationMinorVersionNumber ) {

        StringBuilder path = new StringBuilder ();
        path.append ( rootDirectory );
        path.append ( OSDirectories.DIRECTORY_SEPARATOR );

        if ( applicationVendor != null ) {
            path.append ( rootDirectory );
            path.append ( OSDirectories.DIRECTORY_SEPARATOR );
        }

        path.append ( applicationName );

        // if ( applicationMajorVersionNumber >= 1 ) {
        if ( applicationMajorVersionNumber != null ) {
            path.append ( OSDirectories.DIRECTORY_SEPARATOR );
            path.append ( applicationMajorVersionNumber );
        }

        if ( applicationMinorVersionNumber != null ) {
            path.append ( "_" );
            path.append ( applicationMinorVersionNumber );
        }

        return Path.of ( path.toString() );
    }

    public static @NotNull Path userDataDirectory() {

        String path;

        if ( OSName.isWindowsOS() ) {
            path = System.getenv ( "APPDATA" );
            if ( path == null ) {
                path = OSDirectories.USER_HOME_DIRECTORY + "\\AppData\\Roaming";
            }

        } else if ( OSName.isUnixOS() ) {
            path = System.getenv ( "XDG_DATA_HOME" );
            if ( path == null ) {
                path = OSDirectories.USER_HOME_DIRECTORY + "/.local/share";
            }

        } else if ( OSName.isMacOS() ) {
            path = System.getenv ( "XDG_DATA_HOME" );
            if ( path == null ) {
                path = OSDirectories.USER_HOME_DIRECTORY + "/Library/Application Support";
            }

        } else {
            throw new RuntimeException ( "Unexpected OS " + OSName.name () );
        }

        return Path.of ( path );
    }

    public static @NotNull Path userConfigDirectory() {

        String path;

        if ( OSName.isWindowsOS() ) {
            return userDataDirectory();

        } else if ( OSName.isUnixOS() ) {
            path = System.getenv ( "XDG_CONFIG_HOME" );
            if ( path == null ) {
                path = OSDirectories.USER_HOME_DIRECTORY + "/.config";
            }

        } else if ( OSName.isMacOS() ) {
            path = System.getenv ( "XDG_CONFIG_HOME" );
            if ( path == null ) {
                path = OSDirectories.USER_HOME_DIRECTORY + "/Library/Preferences";
            }

        } else {
            throw new RuntimeException ( "Unexpected OS " + OSName.name () );
        }

        return Path.of ( path );
    }
}
