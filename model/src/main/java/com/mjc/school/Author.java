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
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Author{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append("'");
        sb.append('}');
        return sb.toString();
    }
}