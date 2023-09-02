package com.mjc.school.exception;

import com.mjc.school.entity.NewsEntity;
import com.mjc.school.entity.Author;
import com.mjc.school.entity.Comment;

/**
 * The type Exception incorrect parameter message code.
 */
public final class ExceptionIncorrectParameterMessageCode {
    /**
     * Keys for exception messages associated with {@link NewsEntity}.
     */
    public static final String BAD_ID = "40000";

    /**
     * Keys for exception messages associated with {@link Author}.
     */
    public static final String BAD_AUTHOR_NAME = "40001";
    /**
     * The constant BAD_GIFT_CERTIFICATE_PRICE.
     */
    public static final String BAD_PARAMETER_PART_OF_AUTHOR_NAME = "40002";

    /**
     * Keys for exception messages associated with {@link Comment}.
     */
    public static final String BAD_COMMENT_CONTENT = "40003";
    /**
     * The constant BAD_COMMENT_NEWS_ID.
     */
    public static final String BAD_COMMENT_NEWS_ID = "40004";

    /**
     * The constant BAD_GIFT_CERTIFICATE_DURATION.
     */
    public static final String BAD_COMMENT_MODIFIED_DATE_TIME = "40005";
}