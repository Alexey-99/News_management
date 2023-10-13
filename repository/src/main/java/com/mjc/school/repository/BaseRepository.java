package com.mjc.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<T, V> extends JpaRepository<T, V> {
}