package com.mjc.school;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@Builder
@Data
@AllArgsConstructor
@Entity
@Table(name = "news")
public class News implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title", nullable = false, length = 50, unique = true)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "authors_id")
    private Author author;

    @Column(name = "created", nullable = false)
    private String created;

    @Column(name = "modified", nullable = false)
    private String modified;

    @OneToMany(mappedBy = "news", cascade = ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "news", cascade = ALL)
    private List<NewsTag> tags;

    public News() {
        this.comments = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("News{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append("'");
        sb.append(", content='").append(content).append("'");
        sb.append(", author=").append(author);
        sb.append(", created='").append(created).append("'");
        sb.append(", modified='").append(modified).append("'");
        sb.append(", comments=").append(comments);
        StringBuilder tagsStringBuilder = new StringBuilder();
        tags.forEach(newsTag -> tagsStringBuilder.append(newsTag.getTag().toString()));
        sb.append(", tags=").append(tagsStringBuilder.toString());
        sb.append('}');
        return sb.toString();
    }
}