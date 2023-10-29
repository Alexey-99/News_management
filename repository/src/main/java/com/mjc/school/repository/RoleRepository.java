package com.mjc.school.repository;

import com.mjc.school.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = """
            SELECT id, name
            FROM roles
            WHERE name = :name
            """, nativeQuery = true)
    Role getByName(@Param("name") String name);
}