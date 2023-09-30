package com.mjc.school.validation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mjc.school.entity.abstation.AbstractEntity;

public class AuthorIdWithAmountOfWrittenNewsDTO extends AbstractEntity {
    private long authorId;
    private long amountOfWrittenNews;

    public long getAuthorId() {
        return authorId;
    }

    @JsonIgnore
    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getAmountOfWrittenNews() {
        return amountOfWrittenNews;
    }

    @JsonIgnore
    public void setAmountOfWrittenNews(long amountOfWrittenNews) {
        this.amountOfWrittenNews = amountOfWrittenNews;
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
        AuthorIdWithAmountOfWrittenNewsDTO otherAuthorIdWithAmountOfWrittenNewsDTO =
                (AuthorIdWithAmountOfWrittenNewsDTO) object;
        if (this.authorId != otherAuthorIdWithAmountOfWrittenNewsDTO.authorId) {
            return false;
        }
        if (this.amountOfWrittenNews != otherAuthorIdWithAmountOfWrittenNewsDTO.amountOfWrittenNews) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuthorIdWithAmountOfWrittenNewsDTO{");
        sb.append("authorId=").append(authorId);
        sb.append(", amountOfWrittenNews=").append(amountOfWrittenNews);
        sb.append('}');
        return sb.toString();
    }

    public static class AuthorIdWithAmountOfWrittenNewsDTOBuilder {
        private final AuthorIdWithAmountOfWrittenNewsDTO authorIdWithAmountOfWrittenNews;

        public AuthorIdWithAmountOfWrittenNewsDTOBuilder() {
            this.authorIdWithAmountOfWrittenNews = new AuthorIdWithAmountOfWrittenNewsDTO();
        }

        public AuthorIdWithAmountOfWrittenNewsDTOBuilder setAuthorId(long authorId) {
            this.authorIdWithAmountOfWrittenNews.setAuthorId(authorId);
            return this;
        }

        public AuthorIdWithAmountOfWrittenNewsDTOBuilder setAmountOfWrittenNews(long amountOfWrittenNews) {
            this.authorIdWithAmountOfWrittenNews.setAmountOfWrittenNews(amountOfWrittenNews);
            return this;
        }

        public AuthorIdWithAmountOfWrittenNewsDTO build() {
            return this.authorIdWithAmountOfWrittenNews;
        }
    }
}