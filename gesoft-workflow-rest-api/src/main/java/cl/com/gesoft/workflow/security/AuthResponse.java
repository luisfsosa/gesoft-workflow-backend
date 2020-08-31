package cl.com.gesoft.workflow.security;

import java.util.Date;
import java.util.List;

import cl.com.gesoft.workflow.model.UserGroupRol;

public class AuthResponse {
    
    public static final String ACTIVO = "S";

    private Integer sessionId;
    private String userId;
    private String authKey;
    private Integer clientId;
    private String status = ACTIVO;
    private Date creationDate;
    private Date expirationDate;
    private Long companyId;
    private String email;
    private List<String> permission;
    private List<UserGroupRol> role;
    private String companyName;
    private String name;
    private String lastName;
    private String document;
    private String address;
    private String photo;
    private String phone;
    private Date creationDateTime;


    public Integer getSessionId() { return sessionId; }

    public void setSessionId(Integer sessionId) { this.sessionId = sessionId; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getAuthKey() { return authKey; }

    public void setAuthKey(String authKey) { this.authKey = authKey; }

    public Integer getClientId() { return clientId; }

    public void setClientId(Integer clientId) { this.clientId = clientId; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public Date getCreationDate() { return creationDate; }

    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }

    public Date getExpirationDate() { return expirationDate; }

    public void setExpirationDate(Date expirationDate) { this.expirationDate = expirationDate; }

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getPermission() {
		return permission;
	}

	public void setPermission(List<String> permission) {
		this.permission = permission;
	}

	public String getCompanyName() {
		return companyName;
	}

        public List<UserGroupRol> getRole() {
            return role;
        }

        public void setRole(List<UserGroupRol> role) {
            this.role = role;
        }

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getDocument() {
            return document;
        }

        public void setDocument(String document) {
            this.document = document;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Date getCreationDateTime() {
            return creationDateTime;
        }

        public void setCreationDateTime(Date creationDateTime) {
            this.creationDateTime = creationDateTime;
        }

		@Override
		public String toString() {
			return "AuthResponse [sessionId=" + sessionId + ", userId=" + userId + ", authKey=" + authKey
					+ ", clientId=" + clientId + ", status=" + status + ", creationDate=" + creationDate
					+ ", expirationDate=" + expirationDate + ", companyId=" + companyId + ", email=" + email
					+ ", permission=" + permission + ", role=" + role + ", companyName=" + companyName + ", name="
					+ name + ", lastName=" + lastName + ", document=" + document + ", address=" + address + ", photo="
					+ photo + ", phone=" + phone + ", creationDateTime=" + creationDateTime + "]";
		}
        
        
        
}
