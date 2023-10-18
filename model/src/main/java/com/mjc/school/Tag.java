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
@Table(name = "tags")
public class Tag implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, length = 15, unique = true)
    private String name;

    @OneToMany(mappedBy = "tag", cascade = {ALL})
    private List<NewsTag> news;

    public Tag() {
        this.news = new ArrayList<>();
    }
}