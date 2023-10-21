package com.mjc.school;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;

    private transient long newsId;

    @Column(name = "created", nullable = false)
    private String created;

    @Column(name = "modified", nullable = false)
    private String modified;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Comment{");
        sb.append("id=").append(id);
        sb.append(", content='").append(content).append("'");
        sb.append(", newsId=").append(newsId);
        sb.append(", created='").append(created).append("'");
        sb.append(", modified='").append(modified).append("'");
        sb.append('}');
        return sb.toString();
    }
}