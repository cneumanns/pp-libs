package dev.pp.basics.utilities.directory;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public class DirectoryDeleter {

    public static void deleteDirectoryContent ( Path path ) throws IOException {

        deleteDirectoryTree ( path, false );
    }

    public static void deleteDirectoryTree ( Path path ) throws IOException {

        deleteDirectoryTree ( path, true );
    }

    private static void deleteDirectoryTree ( Path path, boolean deleteRoot ) throws IOException {

        if ( Files.isDirectory ( path, LinkOption.NOFOLLOW_LINKS)) {
            try ( DirectoryStream<Path> entries = Files.newDirectoryStream ( path ) ) {
                for ( Path entry : entries ) {
                    deleteDirectoryTree ( entry, true );
                }
            }
        }

        if ( deleteRoot ) Files.delete ( path );
    }
}
