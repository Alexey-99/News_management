package com.mjc.school.exception.code;

import com.mjc.school.entity.abstation.AbstractEntity;
import com.mjc.school.entity.Author;
import com.mjc.school.entity.Comment;
import com.mjc.school.entity.News;
import com.mjc.school.entity.Tag;

public final class ExceptionIncorrectParameterMessageCode {
    public static final String BAD_ID = "40000";

    public static final String BAD_AUTHOR_NAME = "40001";
    public static final String BAD_PARAMETER_PART_OF_AUTHOR_NAME = "40002";
    public static final String BAD_PARAMETER_AUTHOR_NAME_EXISTS = "40012";

    public static final String BAD_COMMENT_CONTENT = "40003";
    public static final String BAD_COMMENT_NEWS_ID = "40004";

    public static final String BAD_NEWS_TITLE = "40005";
    public static final String BAD_NEWS_CONTENT = "40006";
    public static final String BAD_NEWS_AUTHOR_ID = "40007";
    public static final String BAD_PARAMETER_PART_OF_NEWS_TITLE = "40009";
    public static final String BAD_PARAMETER_PART_OF_NEWS_CONTENT = "40010";
    public static final String BAD_PARAMETER_NEWS_TITLE_EXISTS = "40013";
    public static final String BAD_TAG_NAME = "40008";
    public static final String BAD_PARAMETER_PART_OF_TAG_NAME = "40011";
    public static final String BAD_PARAMETER_TAF_NAME_EXISTS = "40014";

    private ExceptionIncorrectParameterMessageCode() {
    }
}