package com.mjc.school.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * The type Author.
 */
@Entity
@Table(name = "authors",
        schema = "news_management")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @OneToMany(mappedBy = "authorId")
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
        if (this.id != otherAuthor.id) {
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
        final StringBuilder builder = new StringBuilder("Author{");
        builder.append("id=").append(id);
        builder.append(", name='").append(name).append('\'');
        builder.append('}');
        return builder.toString();
    }

    /**
     * The type Author builder.
     */
    public static class AuthorBuilder {
        private final Author author;

        /**
         * Instantiates a new Author builder.
         */
        public AuthorBuilder() {
            this.author = new Author();
        }

        /**
         * Set id.
         *
         * @param id the id
         * @return the id
         */
        public AuthorBuilder setId(long id) {
            this.author.setId(id);
            return this;
        }

        /**
         * Set name.
         *
         * @param name the name
         * @return the name
         */
        public AuthorBuilder setName(String name) {
            this.author.setName(name);
            return this;
        }

        /**
         * Build author.
         *
         * @return the author
         */
        public Author build() {
            return this.author;
        }
    }
}