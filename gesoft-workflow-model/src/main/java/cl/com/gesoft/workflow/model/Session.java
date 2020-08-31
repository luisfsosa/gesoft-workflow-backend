package cl.com.gesoft.workflow.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gwf_sesion")
public class Session implements Serializable {

    private static final long serialVersionUID = 6152324555670677980L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String observations;
    private Integer userId;
    private Integer clientId;
    private Date validFrom;
    private Date validUntil;
    private Date lastAccess;
    private Integer finishedBy;
    private Date finished;
    private Integer createBy;
    private String active;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getClientId() { return clientId; }

    public void setClientId(Integer clientId) { this.clientId = clientId; }

    public Date getValidFrom() { return validFrom; }

    public void setValidFrom(Date validFrom) { this.validFrom = validFrom; }

    public Date getValidUntil() { return validUntil; }

    public void setValidUntil(Date validUntil) { this.validUntil = validUntil; }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Integer getFinishedBy() {
        return finishedBy;
    }

    public void setFinishedBy(Integer finishedBy) {
        this.finishedBy = finishedBy;
    }

    public Date getFinished() {
        return finished;
    }

    public void setFinished(Date finished) { this.finished = finished; }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) { this.createBy = createBy; }

    public String isActive() { return active; }

    public void setActive(String active) { this.active = active; }


    @Override
    public String toString() {
        return "Session{" +
                "id=" + getId() +
                ", observations=" + observations +
                ", userId=" + userId +
                ", clientId=" + clientId +
                ", validFrom=" + validFrom +
                ", validUntil=" + validUntil +
                ", lastAccess=" + lastAccess +
                ", finishedBy=" + finishedBy +
                ", finished=" + finished +
                ", createBy=" + createBy +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(getId(), session.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
