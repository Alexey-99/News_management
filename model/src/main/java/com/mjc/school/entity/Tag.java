package com.mjc.school.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * The type Tag.
 */
@Entity
@Table(name = "tags",
        schema = "news_management")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @OneToMany(mappedBy = "tagId")
    private long id;
    @Column(name = "name",
            nullable = false,
            length = 15,
            unique = true)
    private String name;

    /**
     * Get id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Set id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
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
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Tag{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /**
     * The type Tag builder.
     */
    public static class TagBuilder {
        private final Tag tag;

        /**
         * Instantiates a new Tag builder.
         */
        public TagBuilder() {
            this.tag = new Tag();
        }

        /**
         * Set id.
         *
         * @param id the id
         * @return the id
         */
        public TagBuilder setId(long id) {
            this.tag.setId(id);
            return this;
        }

        /**
         * Set name.
         *
         * @param name the name
         * @return the name
         */
        public TagBuilder setName(String name) {
            this.tag.setName(name);
            return this;
        }

        /**
         * Build tag.
         *
         * @return the tag
         */
        public Tag build() {
            return this.tag;
        }
    }
}