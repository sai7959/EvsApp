package evs.electronicvotingsystem.POJO;

import java.util.Date;

import evs.electronicvotingsystem.Constants.AppConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "evs_user")
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = AppConstants.NOT_Blank)
    @Email(message = AppConstants.NOT_IN_FORMAT)
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank(message = AppConstants.NOT_Blank)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "eligible_To_Vote")
    private boolean eligibleToVote;

    @Column(name = "casted_vote")
    private boolean castedVote;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "active")
    private boolean active;

    @Column(name = "deleted")
    private boolean deleted;

}
