package gr.pada.bolosis.students_cv.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "email", unique=true)
    private String email;

    @NotNull
    @Column(name = "firstname")
    private String firstname;

    @NotNull
    @Column(name = "lastname")
    private String lastname;

    @NotNull
    @Column(name = "university_department")
    private short department;

    @NotNull
    @Column(name = "work_experience")
    private short workExperience;

    @NotNull
    @Column(name = "dob")
    private LocalDate birthday;

    @Column(name = "description")
    private String description;

    @Column(name = "mobile_phone")
    private Long mobilePhone;

    @Column(name = "image_path")
    private String imagePath;

    @NotNull
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "student")
    private Cv cv;
}
