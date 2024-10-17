package dev.pp.basics.utilities;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.utilities.string.StringConstants;

import java.util.logging.*;

public class SimpleLogger {

    public enum LogLevel {
        QUIET, ERROR, WARNING, INFO, ALL
    }

    private static final @NotNull Handler HANDLER = new ConsoleHandler();
    private static final @NotNull Logger LOGGER = createLogger();

    private static @NotNull Logger createLogger() {

        Logger logger = Logger.getLogger ( SimpleLogger.class.getName() );
        logger.addHandler ( HANDLER );
        logger.setUseParentHandlers ( false ); // disable global logger

        return logger;
    }

    public static void debug ( @NotNull String debugInfo ) {
        LOGGER.fine ( debugInfo );
    }

    public static void info ( @NotNull String info ) {
        LOGGER.info ( info );
    }

    public static void warning ( @NotNull String warning ) {
        LOGGER.warning ( warning );
    }

    public static void error ( @NotNull String error ) {
        LOGGER.severe ( error );
    }

    public static void useSimpleFormat() {

        // setFormat ( "%4$s %5$s%n" );
        // setFormat ( "%5$s%n" );

        HANDLER.setFormatter ( new Formatter () {

            @Override public String format ( LogRecord record ) {

                LogLevel level = JavaToPPLevel ( record.getLevel() );
                boolean isErrorOrWarning = level == LogLevel.ERROR || level == LogLevel.WARNING;

                return (level == LogLevel.ALL ? "DEBUG" : level) +
                    ":" +
                    ( isErrorOrWarning ? StringConstants.OS_LINE_BREAK : " " ) +
                    record.getMessage () +
                    StringConstants.OS_LINE_BREAK;
            }
        } );
    }

    /*
    public static void setFormat ( @NotNull String format ) {

        System.setProperty (
            "java.util.logging.SimpleFormatter.format",
            format );
    }
    */

    public static void setLevel ( @NotNull LogLevel level ) {

        Level javaLevel = PPToJavaLevel ( level );
        LOGGER.setLevel ( javaLevel );
        HANDLER.setLevel ( javaLevel );
    }

    private static @NotNull Level PPToJavaLevel ( LogLevel PPLevel ) {

        return switch ( PPLevel ) {
            case QUIET -> Level.OFF;
            case ERROR -> Level.SEVERE;
            case WARNING -> Level.WARNING;
            case INFO -> Level.INFO;
            case ALL -> Level.ALL;
        };
    }

    private static @NotNull LogLevel JavaToPPLevel ( Level JavaLevel ) {

        if ( JavaLevel == Level.OFF ) {
            return LogLevel.QUIET;
        } else if ( JavaLevel == Level.SEVERE ) {
            return LogLevel.ERROR;
        } else if ( JavaLevel == Level.WARNING ) {
            return LogLevel.WARNING;
        } else if ( JavaLevel == Level.INFO ) {
            return LogLevel.INFO;
        } else {
            return LogLevel.ALL;
        }
    }
}
