package com.mjc.school.repository;

import com.mjc.school.model.News;
import com.mjc.school.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = """
            SELECT id, login, password, roles_id
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

    @Query(value = """
            SELECT COUNT(login) > 0
            FROM users
            WHERE login = :login
            """, nativeQuery = true)
    boolean existsByLogin(@Param("login") String login);

    @Modifying
    @Query(value = """
            UPDATE users
                SET roles_id = :roles_id
            WHERE login = :login
            """, nativeQuery = true)
    void changeRole(@Param("login") String login,
                    @Param("roles_id") Long roleId);

    @Query(value = """
            SELECT password
            FROM users
            WHERE login = :login
            """, nativeQuery = true)
    String getPasswordByLogin(@Param("login") String login);

    @Query(value = """
            SELECT id, login, password, roles_id
            FROM users
            ORDER BY id ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<User> findAllSortIdAsc(@Param("size") Integer size,
                                @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, login, password, roles_id
            FROM users
            ORDER BY id DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<User> findAllSortIdDesc(@Param("size") Integer size,
                                 @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, login, password, roles_id
            FROM users
            ORDER BY login ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<User> findAllSortLoginAsc(@Param("size") Integer size,
                                   @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, login, password, roles_id
            FROM users
            ORDER BY login DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<User> findAllSortLoginDesc(@Param("size") Integer size,
                                    @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT users.id, users.login, users.password, users.roles_id
            FROM users
                INNER JOIN roles
                    ON users.roles_id = roles.id
            ORDER BY roles.name ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<User> findAllSortRoleAsc(@Param("size") Integer size,
                                  @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT users.id, users.login, users.password, users.roles_id
            FROM users
                INNER JOIN roles
                    ON users.roles_id = roles.id
            ORDER BY roles.name DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<User> findAllSortRoleDesc(@Param("size") Integer size,
                                   @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT COUNT(id)
            FROM users
            """, nativeQuery = true)
    Long countAllUsers();


    @Query(value = """
            SELECT id, login, password, roles_id
            FROM users
                INNER JOIN roles
                    ON users.roles_id = roles.id
            WHERE roles.name = :roleName
            ORDER BY users.id ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<User> findByRoleSortIdAsc(@Param("roleName") String roleName,
                                   @Param("size") Integer size,
                                   @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, login, password, roles_id
             FROM users
                 INNER JOIN roles
                     ON users.roles_id = roles.id
             WHERE roles.name = :roleName
             ORDER BY id DESC
             LIMIT :size OFFSET :numberFirstElement
             """, nativeQuery = true)
    List<User> findByRoleSortIdDesc(@Param("roleName") String roleName,
                                    @Param("size") Integer size,
                                    @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, login, password, roles_id
            FROM users
                INNER JOIN roles
                    ON users.roles_id = roles.id
            WHERE roles.name = :roleName
            ORDER BY login ASC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<User> findByRoleSortLoginAsc(@Param("roleName") String roleName,
                                      @Param("size") Integer size,
                                      @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SSELECT id, login, password, roles_id
            FROM users
                INNER JOIN roles
                    ON users.roles_id = roles.id
            WHERE roles.name = :roleName
            ORDER BY login DESC
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<User> findByRoleSortLoginDesc(@Param("roleName") String roleName,
                                       @Param("size") Integer size,
                                       @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT id, login, password, roles_id
            FROM users
                INNER JOIN roles
                    ON users.roles_id = roles.id
            WHERE roles.name = :roleName
            LIMIT :size OFFSET :numberFirstElement
            """, nativeQuery = true)
    List<User> findByRole(@Param("roleName") String roleName,
                          @Param("size") Integer size,
                          @Param("numberFirstElement") Integer numberFirstElement);

    @Query(value = """
            SELECT COUNT(users.id)
            FROM users
                INNER JOIN roles
                    ON users.roles_id = roles.id
            WHERE roles.name = :roleName
            """, nativeQuery = true)
    Long countAllUsersByRole(@Param("roleName") String roleName);
}