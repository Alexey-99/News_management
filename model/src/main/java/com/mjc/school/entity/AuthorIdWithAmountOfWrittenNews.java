package com.mjc.school.entity;

import javax.persistence.Entity;

/**
 * The type Author id with amount of written news.
 */
public class AuthorIdWithAmountOfWrittenNews extends NewsEntity {
    private long authorId;
    private long amountOfWrittenNews;

    /**
     * Sets author id.
     *
     * @param authorId the author id
     */
    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    /**
     * Sets amount of written news.
     *
     * @param amountOfWrittenNews the amount of written news
     */
    public void setAmountOfWrittenNews(long amountOfWrittenNews) {
        this.amountOfWrittenNews = amountOfWrittenNews;
    }

    /**
     * Gets amount of written news.
     *
     * @return the amount of written news
     */
    public long getAmountOfWrittenNews() {
        return amountOfWrittenNews;
    }

    /**
     * Gets author id.
     *
     * @return the author id
     */
    public long getAuthorId() {
        return authorId;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = result * PRIME + Long.hashCode(this.authorId);
        result = result * PRIME + Long.hashCode(this.amountOfWrittenNews);
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        }
        AuthorIdWithAmountOfWrittenNews otherAuthorIdWithAmountOfWrittenNews =
                (AuthorIdWithAmountOfWrittenNews) object;
        if (this.authorId != otherAuthorIdWithAmountOfWrittenNews.authorId) {
            return false;
        }
        if (this.amountOfWrittenNews != otherAuthorIdWithAmountOfWrittenNews.amountOfWrittenNews) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("AuthorIdWithAmountOfWrittenNews{");
        builder.append("authorId=").append(authorId);
        builder.append(", amountOfWrittenNews=").append(amountOfWrittenNews);
        builder.append('}');
        return builder.toString();
    }

    /**
     * The type Author id with amount of written news builder.
     */
    public static class AuthorIdWithAmountOfWrittenNewsBuilder {
        private final AuthorIdWithAmountOfWrittenNews authorIdWithAmountOfWrittenNews;

        /**
         * Instantiates a new Author id with amount of written news builder.
         */
        public AuthorIdWithAmountOfWrittenNewsBuilder() {
            this.authorIdWithAmountOfWrittenNews = new AuthorIdWithAmountOfWrittenNews();
        }

        /**
         * Sets author id.
         *
         * @param authorId the author id
         * @return the author id
         */
        public AuthorIdWithAmountOfWrittenNewsBuilder setAuthorId(long authorId) {
            this.authorIdWithAmountOfWrittenNews.setAuthorId(authorId);
            return this;
        }

        /**
         * Sets amount of written news.
         *
         * @param amountOfWrittenNews the amount of written news
         * @return the amount of written news
         */
        public AuthorIdWithAmountOfWrittenNewsBuilder setAmountOfWrittenNews(long amountOfWrittenNews) {
            this.authorIdWithAmountOfWrittenNews.setAmountOfWrittenNews(amountOfWrittenNews);
            return this;
        }

        /**
         * Build author id with amount of written news.
         *
         * @return the author id with amount of written news
         */
        public AuthorIdWithAmountOfWrittenNews build() {
            return this.authorIdWithAmountOfWrittenNews;
        }
    }
}