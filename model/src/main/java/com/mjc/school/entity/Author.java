package com.mjc.school.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<News> getNews() {
        return news;
    }

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

    public static class AuthorBuilder {
        private final Author author;

        public AuthorBuilder() {
            this.author = new Author();
        }

        public AuthorBuilder setId(long id) {
            this.author.setId(id);
            return this;
        }

        public AuthorBuilder setName(String name) {
            this.author.setName(name);
            return this;
        }

        public AuthorBuilder setNews(List<News> news) {
            this.author.setNews(news);
            return this;
        }

        public Author build() {
            return this.author;
        }
    }
}