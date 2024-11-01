package evs.electronicvotingsystem.POJO;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import evs.electronicvotingsystem.Constants.AppConstants;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "election")
@Setter
@Getter
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = AppConstants.NOT_Blank)
    @Column(name = "name", nullable = false)
    private String name;

    @Future(message = AppConstants.FUTURE_DATE)
    @NotNull(message = AppConstants.NOT_NULL)
    @Column(name = "election_date")
    private Date electionDate;

    @OneToOne(optional = false)
    @JoinColumn(name = "state_id", referencedColumnName = "id")
    private State state;

    @JsonIgnore
    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL)
    private List<Party> parties;

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "active")
    private boolean active;

    @Column(name = "deleted")
    private boolean deleted;

}
