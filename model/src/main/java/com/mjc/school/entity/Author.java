package com.mjc.school.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Author.
 */
@Entity
@Table(name = "authors",
        schema = "news_management")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name",
            nullable = false,
            length = 15,
            unique = true)
    private String name;
    @OneToMany(mappedBy = "author")
    private List<News> news;

    public Author() {
        this.news = new ArrayList<>();
    }


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
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!this.getClass().equals(object.getClass())) {
            return false;
        }
        Author otherAuthor = (Author) object;
        if (this.id != otherAuthor.id) {
            return false;
        }
        if (this.name == null) {
            if (otherAuthor.name != null) {
                return false;
            }
        } else if (!this.name.equals(otherAuthor.name)) {
            return false;
        }
        if (this.news == null) {
            if (otherAuthor.news != null) {
                return false;
            }
        } else if (!this.news.equals(otherAuthor.news)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Author{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", news=").append(news);
        sb.append('}');
        return sb.toString();
    }

    /**
     * The type Author builder.
     */
    public static class AuthorBuilder {
        private final Author author;

        /**
         * Instantiates a new Author builder.
         */
        public AuthorBuilder() {
            this.author = new Author();
        }

        /**
         * Set id.
         *
         * @param id the id
         * @return the Author builder
         */
        public AuthorBuilder setId(long id) {
            this.author.setId(id);
            return this;
        }

        /**
         * Set name.
         *
         * @param name the name
         * @return the Author builder
         */
        public AuthorBuilder setName(String name) {
            this.author.setName(name);
            return this;
        }

        /**
         * Set news.
         *
         * @param news the news
         * @return the Author builder
         */
        public AuthorBuilder setNews(List<News> news) {
            this.author.setNews(news);
            return this;
        }

        /**
         * Build author.
         *
         * @return the Author
         */
        public Author build() {
            return this.author;
        }
    }
}