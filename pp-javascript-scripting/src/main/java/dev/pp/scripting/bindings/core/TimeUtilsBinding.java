package dev.pp.scripting.bindings.core;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.scripting.bindings.ScriptingBinding;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/** Date/time utility functions
* line 2
* line 3
*/
public class TimeUtilsBinding implements ScriptingBinding {


    public TimeUtilsBinding () {}


    public @NotNull String bindingName () { return "timeUtils"; }


    // DateTime

/*
    public @NotNull String foo ( @NotNull String notNull, @Nullable String nullable ) {
        return "foo";
    }

    public @Nullable String nullableFoo() {
        return "foo";
    }

 */

    /** Get the current local date and time
     * line 2
     * line 3
     * @param format A string defining the format to use
     * @return A string representing the current local date and time
     */
    public @NotNull String formattedCurrentLocalDateTime ( @NotNull String format ) {
        return LocalDateTime.now().format ( DateTimeFormatter.ofPattern ( format ) );
    }

    /** Get the current local date and time up to minutes
     *
     * @return A string representing the current local date and time
     */
    public @NotNull String currentLocalDateTimeMinutes() {
        return formattedCurrentLocalDateTime ( "yyyy-MM-dd HH:mm" );
    }

    public @NotNull String currentLocalDateTimeSeconds() {
        return formattedCurrentLocalDateTime ( "yyyy-MM-dd HH:mm:ss" );
    }


    // Date

    public @NotNull String formattedCurrentLocalDate ( @NotNull String format ) {
        return LocalDate.now().format ( DateTimeFormatter.ofPattern ( format ) );
    }

    public @NotNull String currentLocalDate() {
        return formattedCurrentLocalDate ( "yyyy-MM-dd" );
    }


    // Time

    public @NotNull String formattedCurrentLocalTime ( @NotNull String format ) {
        return LocalTime.now().format ( DateTimeFormatter.ofPattern ( format ) );
    }

    public @NotNull String currentLocalTimeMinutes() {
        return formattedCurrentLocalTime ( "HH:mm" );
    }

    public @NotNull String currentLocalTimeSeconds() {
        return formattedCurrentLocalTime ( "HH:mm:ss" );
    }
}
