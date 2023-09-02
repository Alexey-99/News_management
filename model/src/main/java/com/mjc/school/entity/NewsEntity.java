package com.mjc.school.entity;

public abstract class NewsEntity {
    @Override
    public abstract int hashCode();

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
        return true;
    }

    @Override
    public abstract String toString();
}