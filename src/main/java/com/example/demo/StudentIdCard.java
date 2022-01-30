package com.example.demo;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "StudentIdCard")
@Table(
        name = "student_id_card",
        uniqueConstraints={
        @UniqueConstraint(
                name = "student_id_card_number_unique",
                columnNames = "card_number"
        )
})
public class StudentIdCard {

    @Id
    @SequenceGenerator(
            name = "student_card_id_sequence",
            sequenceName = "student_card_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "student_card_id_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "card_number",
            nullable = true,
            length = 15
    )
    private String cardNumber;

    @OneToOne(
            cascade = CascadeType.ALL, //allow save student when saving the studentIdCard
            fetch = FetchType.EAGER // one to one default is EAGER, it fetches whole target table, ONE to MANY or M:N default are LAZY
    )
    @JoinColumn(
            name = "student_id", //local table column name
            referencedColumnName = "id" //target table column name
    )
    private Student student; //target entity

    public StudentIdCard() {
    }

    public StudentIdCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public StudentIdCard(String cardNumber, Student student) {
        this.cardNumber = cardNumber;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public String toString() {
        return "StudentIdCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", student=" + student +
                '}';
    }
}
