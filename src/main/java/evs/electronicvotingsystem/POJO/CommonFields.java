package evs.electronicvotingsystem.POJO;

import java.util.Date;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommonFields {
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "active")
    private boolean active;

    @Column(name = "deleted")
    private boolean deleted;
}
