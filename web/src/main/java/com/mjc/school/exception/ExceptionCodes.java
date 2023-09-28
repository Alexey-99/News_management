package com.mjc.school.exception;

public enum ExceptionCodes {
    NOT_FOUND_EXCEPTION(40401, "NOT_FOUND"),
    METHOD_NOT_ALLOWED_EXCEPTION(40501, "METHOD_NOT_ALLOWED");

    private final int code;
    private final String reasonPhrase;

    ExceptionCodes(int code, String reasonPhrase) {
        this.code = code;
        this.reasonPhrase = reasonPhrase;
    }

    public int getCode() {
        return code;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(code).append(" ");
        builder.append(reasonPhrase);
        return builder.toString();
    }
}