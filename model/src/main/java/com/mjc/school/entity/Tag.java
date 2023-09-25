package com.mjc.school.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.List;

/**
 * The type Tag.
 */
@Entity
@Table(name = "tags",
        schema = "news_management")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name",
            nullable = false,
            length = 15,
            unique = true)
    private String name;
    @ManyToMany(mappedBy = "tags")
    private List<News> news;

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
     * Get name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get news.
     *
     * @return the news
     */
    public List<News> getNews() {
        return news;
    }

    /**
     * Set news.
     *
     * @param news the news
     */
    public void setNews(List<News> news) {
        this.news = news;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = result * PRIME + Long.hashCode(this.id);
        result = result * PRIME + (this.name != null ? this.name.hashCode() : 1);
        result = result * PRIME + (this.news != null ? this.news.hashCode() : 1);
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        }
        Tag otherTag = (Tag) object;
        if (this.id != otherTag.id) {
            return false;
        }
        if (this.name == null) {
            if (otherTag.name != null) {
                return false;
            }
        } else if (!this.name.equals(otherTag.name)) {
            return false;
        }
        if (this.news == null) {
            if (otherTag.news != null) {
                return false;
            }
        } else if (!this.news.equals(otherTag.news)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tag{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", news=").append(news);
        sb.append('}');
        return sb.toString();
    }

    /**
     * The type Tag builder.
     */
    public static class TagBuilder {
        private final Tag tag;

        /**
         * Instantiates a new Tag builder.
         */
        public TagBuilder() {
            this.tag = new Tag();
        }

        /**
         * Set id.
         *
         * @param id the id
         * @return the Tag builder
         */
        public TagBuilder setId(long id) {
            this.tag.setId(id);
            return this;
        }

        /**
         * Set name.
         *
         * @param name the name
         * @return the Tag builder
         */
        public TagBuilder setName(String name) {
            this.tag.setName(name);
            return this;
        }

        /**
         * Set news.
         *
         * @param news the news
         * @return the Tag builder
         */
        public TagBuilder setNews(List<News> news) {
            this.tag.setNews(news);
            return this;
        }

        /**
         * Build tag.
         *
         * @return the Tag
         */
        public Tag build() {
            return this.tag;
        }
    }
}