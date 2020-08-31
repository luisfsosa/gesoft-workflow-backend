package cl.com.gesoft.workflow.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the gw_user_group_rol database table.
 * 
 */
@Entity
@Table(name="gwf_user_group_rol")
public class UserGroupRol implements Serializable {
	private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(name="group_id")
        @JsonIgnore
	private Integer groupId;

	@Column(name="rol_id")
	private Long rolId;
	
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonBackReference
	private User userId;

	public UserGroupRol() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Long getRolId() {
		return this.rolId;
	}

	public void setRolId(Long rolId) {
		this.rolId = rolId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

        @Override
        public String toString() {
            return "UserGroupRol{" + "id=" + id + ", groupId=" + groupId + ", rolId=" + rolId + ", userId=" + userId + '}';
        }
}