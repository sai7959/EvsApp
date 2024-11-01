package evs.electronicvotingsystem.POJO;

import java.util.Date;

import java.util.*;
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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "party")
@Setter
@Getter
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = AppConstants.NOT_Blank)
    @Column(name = "name")
    private String name;

    @NotBlank(message = AppConstants.NOT_Blank)
    @Column(name = "candidate_name")
    private String candidateName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "election_id", referencedColumnName = "id")
    private Election election;

    @Column(name = "created_at")
    private Date createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL)
    List<Vote> votes;

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
