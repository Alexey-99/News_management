package com.mjc.school.entity;

import com.mjc.school.entity.abstation.AbstractEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "news")
public class News
        extends AbstractEntity
        implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
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
    @JoinColumn(name = "authors_id")
    private Author author;
    @Column(name = "created",
            nullable = false)
    private String created;
    @Column(name = "modified",
            nullable = false)
    private String modified;
    @OneToMany(mappedBy = "news")
    private List<Comment> comments;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "news_tags",
            joinColumns = {@JoinColumn(name = "news_id")},
            inverseJoinColumns = {@JoinColumn(name = "tags_id")})
    private List<Tag> tags;

    public News() {
        this.comments = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Tag> getTags() {
        return tags;
    }

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
        result = result * PRIME + (this.author != null ? this.author.hashCode() : 1);
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
        if (this.content == null) {
            if (otherNews.content != null) {
                return false;
            }
        } else if (!this.content.equals(otherNews.content)) {
            return false;
        }
        if (this.author != otherNews.author) {
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
        sb.append(", author=").append(author);
        sb.append(", created=").append(created);
        sb.append(", modified=").append(modified);
        sb.append('}');
        return sb.toString();
    }

    public static class NewsBuilder {
        private final News news;

        public NewsBuilder() {
            this.news = new News();
        }

        public NewsBuilder setId(long id) {
            this.news.setId(id);
            return this;
        }

        public NewsBuilder setTitle(String title) {
            this.news.setTitle(title);
            return this;
        }

        public NewsBuilder setContent(String content) {
            this.news.setContent(content);
            return this;
        }

        public NewsBuilder setAuthor(Author author) {
            this.news.setAuthor(author);
            return this;
        }

        public NewsBuilder setCreated(String created) {
            this.news.setCreated(created);
            return this;
        }

        public NewsBuilder setModified(String modified) {
            this.news.setModified(modified);
            return this;
        }

        public NewsBuilder setComments(List<Comment> comments) {
            this.news.setComments(comments);
            return this;
        }


        public NewsBuilder setTags(List<Tag> tags) {
            this.news.setTags(tags);
            return this;
        }

        public News build() {
            return this.news;
        }
    }
}