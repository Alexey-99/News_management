package com.mjc.school.entity;

import com.mjc.school.entity.abstation.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * The type Comment.
 */
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

    /**
     * Get id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Set id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Set content.
     *
     * @param content the content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get news.
     *
     * @return the news
     */
    public News getNews() {
        return news;
    }

    /**
     * Set news.
     *
     * @param news the news
     */
    public void setNews(News news) {
        this.news = news;
    }

    /**
     * Get created.
     *
     * @return the created
     */
    public String getCreated() {
        return created;
    }

    /**
     * Set created.
     *
     * @param created the created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * Get modified.
     *
     * @return the modified
     */
    public String getModified() {
        return modified;
    }

    /**
     * Set modified.
     *
     * @param modified the modified
     */
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

    /**
     * The type Comment builder.
     */
    public static class CommentBuilder {
        private final Comment comment;

        /**
         * Instantiates a new Comment builder.
         */
        public CommentBuilder() {
            this.comment = new Comment();
        }

        /**
         * Set id.
         *
         * @param id the id
         * @return the Comment builder
         */
        public CommentBuilder setId(long id) {
            this.comment.setId(id);
            return this;
        }

        /**
         * Set content.
         *
         * @param content the content
         * @return the Comment builder
         */
        public CommentBuilder setContent(String content) {
            this.comment.setContent(content);
            return this;
        }

        /**
         * Set news.
         *
         * @param news the news
         * @return the Comment builder
         */
        public CommentBuilder setNews(News news) {
            this.comment.setNews(news);
            return this;
        }

        /**
         * Set created.
         *
         * @param created the created
         * @return the Comment builder
         */
        public CommentBuilder setCreated(String created) {
            this.comment.setCreated(created);
            return this;
        }

        /**
         * Set modified.
         *
         * @param modified the modified
         * @return the Comment builder
         */
        public CommentBuilder setModified(String modified) {
            this.comment.setModified(modified);
            return this;
        }

        /**
         * Build comment.
         *
         * @return the comment
         */
        public Comment build() {
            return this.comment;
        }
    }
}