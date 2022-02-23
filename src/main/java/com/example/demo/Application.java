package com.example.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, StudentIdCardRepository studentIdCardRepository){
        return args -> {
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
            Student student =  new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17, 55)
            );
            student.addBook(new Book(faker.book().title(), LocalDateTime.now()));
            student.addBook(new Book(faker.book().title(), LocalDateTime.now().minusYears(1)));

            String studentCardNumber = faker.number().digits(5);
            StudentIdCard studentIdCard = new StudentIdCard(studentCardNumber, student);

            student.setStudentIdCard(studentIdCard);
//            student.enrolToCourse(new Course("Computer Science", "IT"));
//            student.enrolToCourse(new Course("Software Engineering", "IT"));
            student.addEnrolment(new Enrolment(
                    new EnrolmentId(1L, 1L),
                    student,
                    new Course("Computer Science", "IT")
            ));
            student.addEnrolment(new Enrolment(
                    new EnrolmentId(1L, 2L),
                    student,
                    new Course("Software Engineering", "IT")
            ));


            studentRepository.save(student);

        };
    }

    private Page<Student> paging(StudentRepository studentRepository) { //Page<Student> page = paging(studentRepository);
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("firstName").ascending());
        Page<Student> page =  studentRepository.findAll(pageRequest);
        return page;
    }

    private void sorting(StudentRepository studentRepository) { //sorting(studentRepository);
        Sort sort = Sort.by("firstName").ascending()
                        .and(Sort.by("age").descending());

        studentRepository.findAll(sort)
                .forEach(student -> System.out.println(student.getFirstName() + " " + student.getAge())) ;
    }

    private void generateRandomStudents(StudentRepository studentRepository) { //generateRandomStudents(studentRepository);
        Faker faker = new Faker();
        for(int i = 0; i < 20; i++){
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
            studentRepository.save(
                new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17, 55)
                    )
            );
        }
    }

}
