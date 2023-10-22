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
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = result * PRIME + Long.hashCode(this.id);
        result = result * PRIME + (this.content != null ? this.content.hashCode() : 1);
        result = result * PRIME + Long.hashCode(this.newsId);
        result = result * PRIME + (this.created != null ? this.created.hashCode() : 1);
        result = result * PRIME + (this.modified != null ? this.modified.hashCode() : 1);
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
        Comment otherComment = (Comment) object;
        if (this.id != otherComment.getId()) {
            return false;
        }
        if (this.content == null) {
            if (otherComment.content != null) {
                return false;
            }
        } else if (!this.content.equals(otherComment.content)) {
            return false;
        }
        if (this.newsId != otherComment.newsId) {
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
        final StringBuilder builder = new StringBuilder();
        builder.append("Comment{");
        builder.append("id=").append(id).append("'");
        builder.append(", content='").append(content).append("'");
        builder.append(", newsId=").append(newsId).append("'");
        builder.append(", created='").append(created).append("'");
        builder.append(", modified='").append(modified).append("'");
        builder.append("}");
        return builder.toString();
    }
}