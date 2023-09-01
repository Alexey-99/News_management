package com.mjc.school.name;

/**
 * The type Column name.
 */
public final class ColumnName {
    /**
     * The constant TABLE_AUTHORS_COLUMN_ID.
     */
    public static final String TABLE_AUTHORS_COLUMN_ID = "id";
    /**
     * The constant TABLE_AUTHORS_COLUMN_NAME.
     */
    public static final String TABLE_AUTHORS_COLUMN_NAME = "name";

    /**
     * The constant TABLE_NEWS_COLUMN_ID.
     */
    public static final String TABLE_NEWS_COLUMN_ID = "id";
    /**
     * The constant TABLE_NEWS_COLUMN_TITLE.
     */
    public static final String TABLE_NEWS_COLUMN_TITLE = "title";
    /**
     * The constant TABLE_NEWS_COLUMN_CONTENT.
     */
    public static final String TABLE_NEWS_COLUMN_CONTENT = "content";
    /**
     * The constant TABLE_NEWS_COLUMN_AUTHORS_ID.
     */
    public static final String TABLE_NEWS_COLUMN_AUTHORS_ID = "authors_id";
    /**
     * The constant TABLE_NEWS_COLUMN_CREATED.
     */
    public static final String TABLE_NEWS_COLUMN_CREATED = "created";
    /**
     * The constant TABLE_NEWS_COLUMN_MODIFIED.
     */
    public static final String TABLE_NEWS_COLUMN_MODIFIED = "modified";

    /**
     * The constant TABLE_COMMENTS_COLUMN_ID.
     */
    public static final String TABLE_COMMENTS_COLUMN_ID = "id";
    /**
     * The constant TABLE_COMMENTS_COLUMN_CONTENT.
     */
    public static final String TABLE_COMMENTS_COLUMN_CONTENT = "content";
    /**
     * The constant TABLE_COMMENTS_COLUMN_NEWS_ID.
     */
    public static final String TABLE_COMMENTS_COLUMN_NEWS_ID = "news_id";
    /**
     * The constant TABLE_COMMENTS_COLUMN_CREATED.
     */
    public static final String TABLE_COMMENTS_COLUMN_CREATED = "created";
    /**
     * The constant TABLE_COMMENTS_COLUMN_MODIFIED.
     */
    public static final String TABLE_COMMENTS_COLUMN_MODIFIED = "modified";

    /**
     * The constant TABLE_TAGS_COLUMN_ID.
     */
    public static final String TABLE_TAGS_COLUMN_ID = "id";
    /**
     * The constant TABLE_TAGS_COLUMN_NAME.
     */
    public static final String TABLE_TAGS_COLUMN_NAME = "name";

    /**
     * The constant TABLE_NEWS_TAGS_COLUMN_ID.
     */
    public static final String TABLE_NEWS_TAGS_COLUMN_ID = "id";
    /**
     * The constant TABLE_NEWS_TAGS_COLUMN_NEWS_ID.
     */
    public static final String TABLE_NEWS_TAGS_COLUMN_NEWS_ID = "news_id";
    /**
     * The constant TABLE_NEWS_TAGS_COLUMN_TAGS_ID.
     */
    public static final String TABLE_NEWS_TAGS_COLUMN_TAGS_ID = "tags_id";

    /**
     * The constant TABLE_NEWS_COUNT_ROWS_AUTHORS_ID.
     */
    public static final String TABLE_NEWS_COUNT_ROWS_AUTHORS_ID = "COUNT(" +
            TABLE_NEWS_COLUMN_AUTHORS_ID + ")";

    private ColumnName() {
    }
}
