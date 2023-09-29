package com.mjc.school.name;

public final class ColumnName {
    public static final String TABLE_AUTHORS_COLUMN_ID = "id";
    public static final String TABLE_AUTHORS_COLUMN_NAME = "name";

    public static final String TABLE_NEWS_COLUMN_ID = "id";
    public static final String TABLE_NEWS_COLUMN_TITLE = "title";
    public static final String TABLE_NEWS_COLUMN_CONTENT = "content";
    public static final String TABLE_NEWS_COLUMN_AUTHORS_ID = "authors_id";
    public static final String TABLE_NEWS_COLUMN_CREATED = "created";
    public static final String TABLE_NEWS_COLUMN_MODIFIED = "modified";

    public static final String TABLE_COMMENTS_COLUMN_ID = "id";
    public static final String TABLE_COMMENTS_COLUMN_CONTENT = "content";
    public static final String TABLE_COMMENTS_COLUMN_NEWS_ID = "news_id";
    public static final String TABLE_COMMENTS_COLUMN_CREATED = "created";
    public static final String TABLE_COMMENTS_COLUMN_MODIFIED = "modified";

    public static final String TABLE_TAGS_COLUMN_ID = "id";
    public static final String TABLE_TAGS_COLUMN_NAME = "name";

    public static final String TABLE_NEWS_TAGS_COLUMN_ID = "id";
    public static final String TABLE_NEWS_TAGS_COLUMN_NEWS_ID = "news_id";
    public static final String TABLE_NEWS_TAGS_COLUMN_TAGS_ID = "tags_id";

    public static final String COUNT_ROWS = "count";

    private ColumnName() {
    }
}