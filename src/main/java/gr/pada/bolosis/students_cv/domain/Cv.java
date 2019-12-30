package gr.pada.bolosis.students_cv.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cvs")
public class Cv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "file_name")
    private String fileName;

    @NotNull
    @Column(name = "file_download_uri")
    private String fileDownloadUri;

    @NotNull
    @Column(name = "file_type")
    private String fileType;

    @NotNull
    @Column(name = "size")
    private Long size;

    @NotNull
    @Column(name = "time_insert")
    private Instant timeInsert;

    @NotNull
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "student_id")
    private Student student;
}
