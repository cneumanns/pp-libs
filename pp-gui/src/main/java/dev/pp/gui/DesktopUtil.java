package dev.pp.gui;

import dev.pp.basics.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public class DesktopUtil {

    private static Desktop getDesktop() throws IOException {

        Desktop desktop = Desktop.getDesktop();
        if ( desktop != null ) {
            return desktop;
        } else {
            throw new IOException ( "No desktop is available on this system." );
        }
    }

    public static boolean isDesktopSupported() {
        return Desktop.isDesktopSupported();
    }


    // Browser

    public static void openInDefaultBrowser ( @NotNull URI uri ) throws IOException {
        getDesktop().browse ( uri );
    }

    public static void openInDefaultBrowser ( @NotNull String uri ) throws IOException {
        openInDefaultBrowser ( URI.create ( uri ) );
    }

    public static void openInDefaultBrowser ( @NotNull URL url ) throws IOException, URISyntaxException {
        openInDefaultBrowser ( url.toURI() );
    }

    public static void openInDefaultBrowser ( @NotNull Path filePath ) throws IOException {
        openInDefaultBrowser ( filePath.toUri() );
    }

    /*
	function show_HTML_code_in_default_browser ( HTML_code string ) -> runtime_error or null
		in_check: is_browse_command_supported

        const HTML_file = se_text_file_writer.create_temporary_text_file (
            file_name_extension = fa_file_name_extension.create ( "html" )
            delete_file_on_exit = yes
            text = i_HTML_code ) on_error:return_error

        return open_file_in_default_browser ( HTML_file )
     */


    // File

    public static void openFile ( @NotNull Path filePath ) throws IOException {
        getDesktop().open ( filePath.toFile() );
    }

    public static void editFile ( @NotNull Path filePath ) throws IOException {
        getDesktop().edit ( filePath.toFile() );
    }

    public static void printFile ( @NotNull Path filePath ) throws IOException {
        getDesktop().print ( filePath.toFile() );
    }

    public static void showFileInExplorer ( @NotNull Path filePath ) throws IOException {
        getDesktop().browseFileDirectory ( filePath.toFile() );
    }
}

/* TODO
    // email client

    function open_default_email_client -> runtime_error or null
    in to string
    in cc string or null default:null
    in bcc string or null default:null
    in subject string
    in body string
    in_check: is_email_command_supported

    // mailto:TTT?cc=CCC&bcc=BBB&subject=SSS&body=BBB

    const query_map = mutable_map<key:string, value:null or string>.create

    if i_cc is not null then
    query_map.add (
    key = "cc"
    value = i_cc )
    .

    if i_bcc is not null then
    query_map.add (
    key = "bcc"
    value = i_bcc )
    .

    query_map.add (
    key = "subject"
    value = i_subject )

    query_map.add (
    key = "body"
    value = i_body )

    const query string = se_URI_utilities.unencoded_query_map_to_encoded_query_string (
    unencoded_query_map = query_map.make_immutable )

    const to_encoded string = se_URI_utilities.encode_string ( string = i_to )

    const URI_string = """mailto:{{c_to_encoded}}?{{c_query}}"""

    var java_exception java_exception or null = null
    java
    try {
    URI uri = new URI ( c_URI_string.getJavaString() );
    // System.out.println ( uri.toString() );
    desktop.mail ( uri );
    } catch ( Exception e ) {
    v_java_exception = new fa_java_exception ( e );
    }
    end java

    if java_exception is null then
    return null
    else
    return fa_IO_error.create (
    info = """Could not open email client for '{{i_to}}'; URI={{c_URI_string}}. Reason:
{{v_java_exception.to_string}}"""
    java_exception )
    .
    .

    function open_default_email_client_for_URI ( mailto_URI URI ) -> runtime_error or null
    in_check: is_email_command_supported

    var java_exception java_exception or null = null
    java
    try {
    URI uri = new URI ( i_mailto_URI.to_string().getJavaString() );
    desktop.mail ( uri );
    } catch ( Exception e ) {
    v_java_exception = new fa_java_exception ( e );
    }
    end java

    if java_exception is null then
    return null
    else
    return fa_IO_error.create (
    info = """Could not open email client for URI '{{i_mailto_URI}}'. Reason:
{{v_java_exception.to_string}}"""
    java_exception )
    .
    .

    function open_empty_default_email_client -> runtime_error or null
    in_check: is_email_command_supported

    var java_exception java_exception or null = null
    java
    try {
    desktop.mail();
    } catch ( Exception e ) {
    v_java_exception = new fa_java_exception ( e );
    }
    end java

    if java_exception is null then
    return null
    else
    return fa_IO_error.create (
    info = """Could not open email client. Reason:
{{v_java_exception.to_string}}"""
    java_exception )
 */
