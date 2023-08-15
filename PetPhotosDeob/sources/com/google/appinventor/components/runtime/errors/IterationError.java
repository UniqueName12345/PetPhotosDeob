package com.google.appinventor.components.runtime.errors;

import com.google.appinventor.components.runtime.util.ErrorMessages;

/* loaded from: classes.dex */
public class IterationError extends DispatchableError {
    public IterationError(int error, Object[] arguments) {
        super(error, arguments);
    }

    public int getIndex() {
        return ((Integer) getArguments()[0]).intValue();
    }

    public String getExpected() {
        return (String) getArguments()[1];
    }

    public String getFound() {
        return (String) getArguments()[2];
    }

    public static DispatchableError fromError(int index, DispatchableError e) {
        switch (e.getErrorCode()) {
            case ErrorMessages.ERROR_INVALID_POINT /* 3405 */:
                return new IterationError(ErrorMessages.ERROR_INVALID_POINT_AT_INDEX, prepend(index, e.getArguments()));
            case ErrorMessages.ERROR_INVALID_POINT_AT_INDEX /* 3406 */:
            case ErrorMessages.ERROR_INVALID_TYPE_AT_INDEX /* 3407 */:
            case ErrorMessages.ERROR_INVALID_NUMBER_OF_VALUES_IN_POINT_AT_INDEX /* 3408 */:
            default:
                return e;
            case ErrorMessages.ERROR_INVALID_NUMBER_OF_VALUES_IN_POINT /* 3409 */:
                return new IterationError(ErrorMessages.ERROR_INVALID_NUMBER_OF_VALUES_IN_POINT_AT_INDEX, prepend(index, e.getArguments()));
            case ErrorMessages.ERROR_INVALID_TYPE /* 3410 */:
                return new IterationError(ErrorMessages.ERROR_INVALID_TYPE_AT_INDEX, prepend(index, e.getArguments()));
        }
    }

    private static Object[] prepend(int index, Object[] arguments) {
        Object[] newArguments = new Object[arguments.length + 1];
        newArguments[0] = Integer.valueOf(index);
        System.arraycopy(arguments, 0, newArguments, 1, arguments.length);
        return newArguments;
    }
}
