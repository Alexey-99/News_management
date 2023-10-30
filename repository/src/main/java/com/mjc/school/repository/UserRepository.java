package com.mjc.school.repository;

import com.mjc.school.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = """
            SELECT id, email, login, password, roles_id
            FROM users
            WHERE login = :login
            """, nativeQuery = true)
    Optional<User> findByLogin(@Param("login") String login);

    @Query(value = """
            SELECT COUNT(login) = 0
            FROM users
            WHERE login = :login
            """, nativeQuery = true)
    boolean notExistsByLogin(@Param("login") String login);
}