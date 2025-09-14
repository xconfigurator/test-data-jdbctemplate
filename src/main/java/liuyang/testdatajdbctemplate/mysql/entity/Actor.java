package liuyang.testdatajdbctemplate.mysql.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xconf
 * @since 2025/9/14
 */
// entity 可以由工具生成
public class Actor implements Serializable {

    private static final long serialVersionUID = 1L;

    //@TableId(value = "actor_id", type = IdType.AUTO)
    private Short actorId;

    private String firstName;

    private String lastName;

    private LocalDateTime lastUpdate;

    public Short getActorId() {
        return actorId;
    }

    public void setActorId(Short actorId) {
        this.actorId = actorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "actorId = " + actorId +
                ", firstName = " + firstName +
                ", lastName = " + lastName +
                ", lastUpdate = " + lastUpdate +
                "}";
    }
}
