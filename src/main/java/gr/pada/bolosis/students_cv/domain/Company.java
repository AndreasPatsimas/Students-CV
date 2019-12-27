package gr.pada.bolosis.students_cv.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "email", unique=true)
    private String email;

    @NotNull
    @Column(name = "company_name")
    private String companyName;

    @Column(name = "units")
    private Long units;

    @Column(name = "logo_path")
    private String logoPath;

    @NotNull
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private User user;
}
