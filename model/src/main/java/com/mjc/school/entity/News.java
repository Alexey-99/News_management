package com.mjc.school.entity;

import com.mjc.school.entity.abstation.AbstractEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

/**
 * The type News.
 */
@Entity
@Table(name = "news",
        schema = "news_management")
public class News extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @OneToMany(mappedBy = "newsId")
    private long id;
    @Column(name = "title",
            nullable = false,
            length = 50,
            unique = true)
    private String title;
    @Column(name = "content",
            nullable = false,
            length = 255)
    private String content;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private long authorId;
    @Column(name = "created",
            nullable = false)
    private String created;

    @Column(name = "modified",
            nullable = false)
    private String modified;
    private List<Comment> comments;
    private List<Tag> tags;

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
     * Get title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
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
     * Get author id.
     *
     * @return the author id
     */
    public long getAuthorId() {
        return authorId;
    }

    /**
     * Set author id.
     *
     * @param authorId the author id
     */
    public void setAuthorId(long authorId) {
        this.authorId = authorId;
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

    /**
     * Get comments.
     *
     * @return the comments
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * Set comments.
     *
     * @param comments the comments
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Get tags.
     *
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Set tags.
     *
     * @param tags the tags
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = result * PRIME + Long.hashCode(this.id);
        result = result * PRIME + (this.title != null ? this.title.hashCode() : 1);
        result = result * PRIME + (this.content != null ? this.content.hashCode() : 1);
        result = result * PRIME + Long.hashCode(this.authorId);
        result = result * PRIME + (this.created != null ? this.created.hashCode() : 1);
        result = result * PRIME + (this.modified != null ? this.modified.hashCode() : 1);
        result = result * PRIME + (this.comments != null ? this.comments.hashCode() : 1);
        result = result * PRIME + (this.tags != null ? this.tags.hashCode() : 1);
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        }
        News otherNews = (News) object;
        if (this.id != otherNews.id) {
            return false;
        }
        if (this.title == null) {
            if (otherNews.title != null) {
                return false;
            }
        } else if (!this.title.equals(otherNews.title)) {
            return false;
        }
        if (this.content == null) {
            if (otherNews.content != null) {
                return false;
            }
        } else if (!this.content.equals(otherNews.content)) {
            return false;
        }
        if (this.authorId != otherNews.authorId) {
            return false;
        }
        if (this.created == null) {
            if (otherNews.created != null) {
                return false;
            }
        } else if (!this.created.equals(otherNews.created)) {
            return false;
        }
        if (this.modified == null) {
            if (otherNews.modified != null) {
                return false;
            }
        } else if (!this.modified.equals(otherNews.modified)) {
            return false;
        }
        if (this.comments == null) {
            if (otherNews.comments != null) {
                return false;
            }
        } else if (!this.comments.equals(otherNews.comments)) {
            return false;
        }
        if (this.tags == null) {
            if (otherNews.tags != null) {
                return false;
            }
        } else if (!this.tags.equals(otherNews.tags)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("News{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", authorId=").append(authorId);
        sb.append(", created=").append(created);
        sb.append(", modified=").append(modified);
        sb.append('}');
        return sb.toString();
    }

    /**
     * The type News builder.
     */
    public static class NewsBuilder {
        private final News news;

        /**
         * Instantiates a new News builder.
         */
        public NewsBuilder() {
            this.news = new News();
        }

        /**
         * Set id.
         *
         * @param id the id
         * @return the id
         */
        public NewsBuilder setId(long id) {
            this.news.setId(id);
            return this;
        }

        /**
         * Set title.
         *
         * @param title the title
         * @return the title
         */
        public NewsBuilder setTitle(String title) {
            this.news.setTitle(title);
            return this;
        }

        /**
         * Set content.
         *
         * @param content the content
         * @return the content
         */
        public NewsBuilder setContent(String content) {
            this.news.setContent(content);
            return this;
        }

        /**
         * Set author id.
         *
         * @param authorId the author id
         * @return the author id
         */
        public NewsBuilder setAuthorId(long authorId) {
            this.news.setAuthorId(authorId);
            return this;
        }

        /**
         * Set created.
         *
         * @param created the created
         * @return the created
         */
        public NewsBuilder setCreated(String created) {
            this.news.setCreated(created);
            return this;
        }

        /**
         * Set modified.
         *
         * @param modified the modified
         * @return the modified
         */
        public NewsBuilder setModified(String modified) {
            this.news.setModified(modified);
            return this;
        }

        /**
         * Set comments.
         *
         * @param comments the comments
         * @return the comments
         */
        public NewsBuilder setComments(List<Comment> comments) {
            this.news.setComments(comments);
            return this;
        }


        /**
         * Set tags.
         *
         * @param tags the tags
         * @return the tags
         */
        public NewsBuilder setTags(List<Tag> tags) {
            this.news.setTags(tags);
            return this;
        }

        /**
         * Build news.
         *
         * @return the news
         */
        public News build() {
            return this.news;
        }
    }
}