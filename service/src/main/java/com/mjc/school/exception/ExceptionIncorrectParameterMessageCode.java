package com.mjc.school.exception;

import com.mjc.school.entity.Author;
import com.mjc.school.entity.News;
import com.mjc.school.entity.Tag;

/**
 * The type Exception incorrect parameter message code.
 */
public class ExceptionIncorrectParameterMessageCode {
    /**
     * Keys for exception messages associated with {@link Author}.
     */
    public static final String BAD_ID = "40000";
    /**
     * Keys for exception messages associated with {@link Tag}.
     */
    public static final String BAD_TAG_NAME = "40001";

    /**
     * Keys for exception messages associated with {@link News}.
     */
    public static final String BAD_GIFT_CERTIFICATE_NAME = "40002";
    /**
     * The constant BAD_GIFT_CERTIFICATE_DESCRIPTION.
     */
    public static final String BAD_GIFT_CERTIFICATE_DESCRIPTION = "40003";
    /**
     * The constant BAD_GIFT_CERTIFICATE_PRICE.
     */
    public static final String BAD_GIFT_CERTIFICATE_PRICE = "40004";
    /**
     * The constant BAD_GIFT_CERTIFICATE_DURATION.
     */
    public static final String BAD_GIFT_CERTIFICATE_DURATION = "40005";
}