package dev.pp.basics.utilities;

import dev.pp.basics.annotations.NotNull;
import dev.pp.basics.annotations.Nullable;
import dev.pp.basics.utilities.string.StringConstants;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Function;

public class ExceptionUtil {

    private static final @NotNull Function<Throwable, String>
        DEFAULT_CHECKED_EXCEPTION_MESSAGE_SUPPLIER = Throwable::getMessage;
    private static final @NotNull Function<Throwable, String>
        DEFAULT_UNCHECKED_EXCEPTION_MESSAGE_SUPPLIER = ExceptionUtil::getStackTrace;

    public static boolean isCheckedException ( @NotNull Throwable throwable ) {

        return throwable instanceof Exception
            && ! ( throwable instanceof RuntimeException );
    }

    public static boolean isUncheckedException ( @NotNull Throwable throwable ) {

        return ! isCheckedException ( throwable );
    }

    public static @NotNull String throwableToUserString (
        @NotNull Throwable throwable ) {

        return throwableToUserString (
            throwable, DEFAULT_CHECKED_EXCEPTION_MESSAGE_SUPPLIER, DEFAULT_UNCHECKED_EXCEPTION_MESSAGE_SUPPLIER );
    }

    public static @NotNull String throwableToUserString (
        @NotNull Throwable throwable,
        @Nullable Function<Throwable, String> checkedExceptionMessageSupplier,
        @Nullable Function<Throwable, String> uncheckedExceptionMessageSupplier ) {

        StringBuilder sb = new StringBuilder();
        sb.append ( getMessage ( throwable, checkedExceptionMessageSupplier, uncheckedExceptionMessageSupplier ) );
        if ( isCheckedException ( throwable ) )
            appendCauses ( sb, throwable,checkedExceptionMessageSupplier, uncheckedExceptionMessageSupplier );

        return sb.toString();
    }

    public static @NotNull String getStackTrace ( @NotNull Throwable throwable ) {

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter ( sw );
        throwable.printStackTrace ( pw );

        return sw.toString();
    }

    public static @NotNull Throwable getRootCause ( @NotNull Throwable throwable ) {

        @Nullable Throwable cause = throwable.getCause();
        if ( cause == null ) return throwable;

        return getRootCause ( cause );
    }


    private static @NotNull String getMessage (
        @NotNull Throwable throwable,
        @Nullable Function<Throwable, String> checkedExceptionMessageSupplier,
        @Nullable Function<Throwable, String> uncheckedExceptionMessageSupplier ) {

        String result = null;
        if ( isCheckedException ( throwable ) ) {
            if ( checkedExceptionMessageSupplier != null ) {
                result = checkedExceptionMessageSupplier.apply ( throwable );
            }
            if ( result == null ) result = DEFAULT_CHECKED_EXCEPTION_MESSAGE_SUPPLIER.apply ( throwable );
        } else {
            if ( uncheckedExceptionMessageSupplier != null ) {
                result = uncheckedExceptionMessageSupplier.apply ( throwable );
            }
            if ( result == null ) result = DEFAULT_UNCHECKED_EXCEPTION_MESSAGE_SUPPLIER.apply ( throwable );
        }
        return result;
    }

    private static void appendCauses (
        @NotNull StringBuilder sb,
        @NotNull Throwable throwable,
        @Nullable Function<Throwable, String> checkedExceptionMessageSupplier,
        @Nullable Function<Throwable, String> uncheckedExceptionMessageSupplier ) {

        @Nullable Throwable cause = throwable.getCause();
        if ( cause == null ) return;

        sb.append ( StringConstants.OS_LINE_BREAK );
        sb.append ( "Cause:" );
        sb.append ( StringConstants.OS_LINE_BREAK );
        sb.append ( getMessage ( cause, checkedExceptionMessageSupplier, uncheckedExceptionMessageSupplier ) );

        appendCauses ( sb, cause, checkedExceptionMessageSupplier, uncheckedExceptionMessageSupplier );
    }
}
