package com.mjc.school.entity;

import com.mjc.school.entity.abstation.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comments",
        schema = "news_management")
public class Comment extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "content", nullable = false, length = 255)
    private String content;
    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;
    @Column(name = "created", nullable = false)
    private String created;
    @Column(name = "modified", nullable = false)
    private String modified;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }


    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = result * PRIME + Long.hashCode(this.id);
        result = result * PRIME + (this.content != null ? this.content.hashCode() : 1);
        result = result * PRIME + (this.news != null ? this.news.hashCode() : 1);
        result = result * PRIME + (this.created != null ? this.created.hashCode() : 1);
        result = result * PRIME + (this.modified != null ? this.modified.hashCode() : 1);
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        }
        Comment otherComment = (Comment) object;
        if (this.id != otherComment.id) {
            return false;
        }
        if (this.content == null) {
            if (otherComment.content != null) {
                return false;
            }
        } else if (!this.content.equals(otherComment.content)) {
            return false;
        }
        if (this.news == null) {
            if (otherComment.news != null) {
                return false;
            }
        } else if (!this.news.equals(otherComment.news)) {
            return false;
        }
        if (this.created == null) {
            if (otherComment.created != null) {
                return false;
            }
        } else if (!this.created.equals(otherComment.created)) {
            return false;
        }
        if (this.modified == null) {
            if (otherComment.modified != null) {
                return false;
            }
        } else if (!this.modified.equals(otherComment.modified)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Comment{");
        sb.append("id=").append(id);
        sb.append(", content='").append(content).append('\'');
        sb.append(", news=").append(news);
        sb.append(", created=").append(created);
        sb.append(", modified=").append(modified);
        sb.append('}');
        return sb.toString();
    }

    public static class CommentBuilder {
        private final Comment comment;

        public CommentBuilder() {
            this.comment = new Comment();
        }

        public CommentBuilder setId(long id) {
            this.comment.setId(id);
            return this;
        }

        public CommentBuilder setContent(String content) {
            this.comment.setContent(content);
            return this;
        }

        public CommentBuilder setNews(News news) {
            this.comment.setNews(news);
            return this;
        }

        public CommentBuilder setCreated(String created) {
            this.comment.setCreated(created);
            return this;
        }

        public CommentBuilder setModified(String modified) {
            this.comment.setModified(modified);
            return this;
        }

        public Comment build() {
            return this.comment;
        }
    }
}