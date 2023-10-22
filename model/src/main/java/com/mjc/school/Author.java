package com.mjc.school;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@Builder
@Data
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false, length = 15, unique = true)
    private String name;

    @OneToMany(mappedBy = "author", cascade = ALL)
    private List<News> news;

    public Author() {
        this.news = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = result * PRIME + Long.hashCode(this.id);
        result = result * PRIME + (this.name != null ? this.name.hashCode() : 1);
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
        if (this.id != otherAuthor.getId()) {
            return false;
        }
        if (this.name == null) {
            if (otherAuthor.name != null) {
                return false;
            }
        } else if (!this.name.equals(otherAuthor.name)) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Author{");
        builder.append("id='").append(id).append("'");
        builder.append(", name='").append(name).append("'");
        builder.append("}");
        return builder.toString();
    }
}