package cl.com.gesoft.workflow.security;

import java.util.Date;
import java.util.List;

import cl.com.gesoft.workflow.model.UserGroupRol;

/**
 * The type Auth response.
 */
public class AuthResponse {

    /**
     * The constant ACTIVO.
     */
    public static final String ACTIVO = "S";

    private Integer sessionId;
    private String userId;
    private String authKey;
    private String rol;
    private Integer clientId;
    private String status = ACTIVO;
    private Date creationDate;
    private Date expirationDate;
    private String email;
    private String name;
    private String lastName;
    private Date creationDateTime;


    /**
     * Gets session id.
     *
     * @return the session id
     */
    public Integer getSessionId() { return sessionId; }

    /**
     * Sets session id.
     *
     * @param sessionId the session id
     */
    public void setSessionId(Integer sessionId) { this.sessionId = sessionId; }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserId() { return userId; }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(String userId) { this.userId = userId; }

    /**
     * Gets auth key.
     *
     * @return the auth key
     */
    public String getAuthKey() { return authKey; }

    /**
     * Sets auth key.
     *
     * @param authKey the auth key
     */
    public void setAuthKey(String authKey) { this.authKey = authKey; }


    /**
     * Gets rol.
     *
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * Sets rol.
     *
     * @param rol the rol
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Gets client id.
     *
     * @return the client id
     */
    public Integer getClientId() { return clientId; }

    /**
     * Sets client id.
     *
     * @param clientId the client id
     */
    public void setClientId(Integer clientId) { this.clientId = clientId; }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() { return status; }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) { this.status = status; }

    /**
     * Gets creation date.
     *
     * @return the creation date
     */
    public Date getCreationDate() { return creationDate; }

    /**
     * Sets creation date.
     *
     * @param creationDate the creation date
     */
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }

    /**
     * Gets expiration date.
     *
     * @return the expiration date
     */
    public Date getExpirationDate() { return expirationDate; }

    /**
     * Sets expiration date.
     *
     * @param expirationDate the expiration date
     */
    public void setExpirationDate(Date expirationDate) { this.expirationDate = expirationDate; }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
		return email;
	}

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
		this.email = email;
	}

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
		return name;
	}

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
		this.name = name;
	}

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
            return lastName;
        }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
            this.lastName = lastName;
        }


    /**
     * Gets creation date time.
     *
     * @return the creation date time
     */
    public Date getCreationDateTime() {
            return creationDateTime;
        }

    /**
     * Sets creation date time.
     *
     * @param creationDateTime the creation date time
     */
    public void setCreationDateTime(Date creationDateTime) {
            this.creationDateTime = creationDateTime;
        }

		@Override
		public String toString() {
			return "AuthResponse [sessionId=" + sessionId + ", userId=" + userId + ", authKey=" + authKey
					+ ", clientId=" + clientId + ", status=" + status + ", creationDate=" + creationDate
					+ ", expirationDate=" + expirationDate + ", email=" + email
					+ ", name=" + name + ", lastName=" + lastName + ", creationDateTime=" + creationDateTime + "]";
		}
}
