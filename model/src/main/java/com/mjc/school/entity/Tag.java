package com.mjc.school.entity;

import com.mjc.school.entity.abstation.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tags")
public class Tag
        extends AbstractEntity
        implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    @Column(name = "name",
            nullable = false,
            length = 15,
            unique = true)
    private String name;
    @ManyToMany(mappedBy = "tags")
    private List<News> news;

    public Tag() {
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

    public static class TagBuilder {
        private final Tag tag;

        public TagBuilder() {
            this.tag = new Tag();
        }

        public TagBuilder setId(long id) {
            this.tag.setId(id);
            return this;
        }

        public TagBuilder setName(String name) {
            this.tag.setName(name);
            return this;
        }

        public TagBuilder setNews(List<News> news) {
            this.tag.setNews(news);
            return this;
        }

        public Tag build() {
            return this.tag;
        }
    }
}