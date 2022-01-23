package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    //JPQL
    @Query("SELECT s FROM Student s WHERE s.email = ?1") //Student here refers to Student.class's(Entity class) @Entity
    Optional<Student> findStudentByEmail(String email);

    //JPQL //not database specific
    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.age >= ?2")
    List<Student> findStudentsByFirstNameEqualsAndAgeIsGreaterThanEqual(String firstName, Integer age);

    //using native sql //db specific, if change from postgres to mysql, this could be not working
    @Query(value = "SELECT * FROM student WHERE first_name = ?1 OR age >= ?2", nativeQuery = true)
    List<Student> findStudentsByFirstNameEqualsAndAgeIsGreaterOrEqualNative(String firstName, Integer age);

    //using native sql //db specific, if change from postgres to mysql, this could be not working
    //Named parameters
    @Query(value = "SELECT * FROM student WHERE first_name = :firstName OR age = :age", nativeQuery = true)
    List<Student> findStudentsByFirstNameEqualsAndAgeIsEqual(
            @Param("firstName") String firstName,
            @Param("age") Integer age);

    @Transactional //update and delete need it
    @Modifying //for update or delete
    @Query("DELETE FROM Student u WHERE u.id = ?1")
    int deleteByStudentId(Long id);

}
