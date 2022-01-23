package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return args -> {
            Student goodman = new Student("Goodman", "Freeman", "goodman@freeman.com", 28);
            Student goodman2 = new Student("Goodman2", "Freeman2", "goodman2@freeman.com", 28);
            Student ahmed = new Student("Ahmed", "Ali", "ahmed@abc.com", 12);

////            studentRepository.save(maria);
//            System.out.println("Adding maria and ahmed");
//            studentRepository.saveAll(List.of(goodman, ahmed));
//            studentRepository.saveAll(List.of(goodman2));

            //using psql
            studentRepository.findStudentByEmail("goodman@freeman.com")
                    .ifPresentOrElse(System.out::println, ()-> System.out.println("Students with email not found"));

            //using psql
            studentRepository.findStudentsByFirstNameEqualsAndAgeIsGreaterThanEqual("Goodman2", 28)
                    .forEach(System.out::println);

            //using native sql
            studentRepository.findStudentsByFirstNameEqualsAndAgeIsGreaterOrEqualNative("Goodman", 28)
                    .forEach(System.out::println);

            //delete. using @Modifying
            System.out.println(studentRepository.deleteByStudentId(6L));
        };

    }

}
