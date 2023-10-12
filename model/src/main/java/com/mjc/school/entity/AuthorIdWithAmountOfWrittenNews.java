package com.mjc.school.entity;

import java.io.Serializable;

public class AuthorIdWithAmountOfWrittenNews implements Serializable {
    private long authorId;
    private long amountOfWrittenNews;

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getAmountOfWrittenNews() {
        return amountOfWrittenNews;
    }

    public void setAmountOfWrittenNews(long amountOfWrittenNews) {
        this.amountOfWrittenNews = amountOfWrittenNews;
    }

    public static class AuthorIdWithAmountOfWrittenNewsBuilder {
        private final AuthorIdWithAmountOfWrittenNews authorIdWithAmountOfWrittenNews;

        public AuthorIdWithAmountOfWrittenNewsBuilder() {
            this.authorIdWithAmountOfWrittenNews = new AuthorIdWithAmountOfWrittenNews();
        }

        public AuthorIdWithAmountOfWrittenNewsBuilder setAuthorId(long authorId) {
            this.authorIdWithAmountOfWrittenNews.setAuthorId(authorId);
            return this;
        }

        public AuthorIdWithAmountOfWrittenNewsBuilder setAmountOfWrittenNews(long amountOfWrittenNews) {
            this.authorIdWithAmountOfWrittenNews.setAmountOfWrittenNews(amountOfWrittenNews);
            return this;
        }

        public AuthorIdWithAmountOfWrittenNews build() {
            return this.authorIdWithAmountOfWrittenNews;
        }
    }
}