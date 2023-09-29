package com.mjc.school.entity;

import com.mjc.school.entity.abstation.AbstractEntity;

public class AuthorIdWithAmountOfWrittenNews extends AbstractEntity {
    private long authorId;
    private long amountOfWrittenNews;

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public void setAmountOfWrittenNews(long amountOfWrittenNews) {
        this.amountOfWrittenNews = amountOfWrittenNews;
    }

    public long getAmountOfWrittenNews() {
        return amountOfWrittenNews;
    }

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