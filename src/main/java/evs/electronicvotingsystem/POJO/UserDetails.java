package evs.electronicvotingsystem.POJO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import evs.electronicvotingsystem.Constants.AppConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_details")
@Setter
@Getter
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Valid
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank(message = AppConstants.NOT_Blank)
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = AppConstants.NOT_NULL)
    @Past(message = AppConstants.PAST_DATE)
    @DateTimeFormat(pattern = AppConstants.DATE_PATTERN)
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Size(min = 1, max = 1)
    @Pattern(regexp = AppConstants.GENDER_PATTERN, message = AppConstants.GENDER_VALIDATION_MESSAGE)
    @NotNull(message = AppConstants.NOT_NULL)
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotBlank(message = AppConstants.NOT_Blank)
    @Column(name = "address", nullable = false)
    private String address;

    @Pattern(regexp = AppConstants.MOBILE_REGEX_PATTERN, message = AppConstants.MOBILE_VALIDATION_MESSAGE)
    @NotBlank(message = AppConstants.NOT_Blank)
    @Column(name = "mobile_number")
    private String mobileNumber;

    @NotBlank(message = AppConstants.NOT_Blank)
    @Column(name = "district")
    private String district;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(name = "active")
    private boolean active;

    @Column(name = "deleted")
    private boolean deleted;
}
