package com.mjc.school.exception;

import com.mjc.school.entity.NewsEntity;
import com.mjc.school.entity.Author;
import com.mjc.school.entity.Comment;
import com.mjc.school.entity.News;
import com.mjc.school.entity.Tag;

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
     * The constant BAD_PARAMETER_PART_OF_AUTHOR_NAME.
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
     * Keys for exception messages associated with {@link News}.
     */
    /**
     * The constant BAD_NEWS_TITLE.
     */
    public static final String BAD_NEWS_TITLE = "40005";
    /**
     * The constant BAD_NEWS_CONTENT.
     */
    public static final String BAD_NEWS_CONTENT = "40006";
    /**
     * The constant BAD_NEWS_AUTHOR_ID.
     */
    public static final String BAD_NEWS_AUTHOR_ID = "40007";
    /**
     * The constant BAD_PARAMETER_PART_OF_NEWS_TITLE.
     */
    public static final String BAD_PARAMETER_PART_OF_NEWS_TITLE = "40009";
    /**
     * The constant BAD_PARAMETER_PART_OF_NEWS_CONTENT.
     */
    public static final String BAD_PARAMETER_PART_OF_NEWS_CONTENT = "40010";

    /**
     * Keys for exception messages associated with {@link Tag}.
     */
    public static final String BAD_TAG_NAME = "40008";
    /**
     * The constant BAD_PARAMETER_PART_OF_TAG_NAME.
     */
    public static final String BAD_PARAMETER_PART_OF_TAG_NAME = "40011";
}