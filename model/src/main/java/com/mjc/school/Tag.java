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
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Tag{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append("'");
        sb.append('}');
        return sb.toString();
    }
}