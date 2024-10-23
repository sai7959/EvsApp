package evs.electronicvotingsystem.POJO;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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

    @NotBlank(message = "Name can't be blank")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Date of birth can't be blank")
    @Past(message = "Date of Birth must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_birth", nullable = false)
    private String dateOfBirth;

    @Size(min = 1, max = 1)
    @NotBlank(message = "Gender can't be blank")
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotBlank(message = "Address can't be blank")
    @Column(name = "address", nullable = false)
    private String address;

    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be exactly 10 digits and contain only numbers 0-9")
    @NotBlank(message = "Mobile number can't be blank")
    @Column(name = "mobile_number")
    private String mobileNumber;

    @NotBlank(message = "District can't be blank")
    @Column(name = "district")
    private String district;

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(name = "active")
    private boolean active;

    @Column(name = "deleted")
    private boolean deleted;
}
