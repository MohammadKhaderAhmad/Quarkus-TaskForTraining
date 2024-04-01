package org.acme.forQuery;

import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.ColumnResult;

/*@SqlResultSetMapping(name="JoinQueryResultMapping",
        classes = {
                @ConstructorResult(
                        targetClass = JoinQueryResult.class,
                        columns = {
                                @ColumnResult(name="citizenId", type=Long.class),
                                @ColumnResult(name="citizenName", type=String.class),
                                @ColumnResult(name="gender", type=String.class),
                                @ColumnResult(name="simCardId", type=Long.class)
                        }
                )
        }
)*/
public class JoinQueryResult {
    private Long citizenId;
    private String citizenName;
    private String gender;
    private Long simCardId;

    public JoinQueryResult(Long citizenId, String citizenName, String gender, Long simCardId) {
        this.citizenId = citizenId;
        this.citizenName = citizenName;
        this.gender = gender;
        this.simCardId = simCardId;
    }


    public Long getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(Long citizenId) {
        this.citizenId = citizenId;
    }

    public String getCitizenName() {
        return citizenName;
    }

    public void setCitizenName(String citizenName) {
        this.citizenName = citizenName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getSimCardId() {
        return simCardId;
    }

    public void setSimCardId(Long simCardId) {
        this.simCardId = simCardId;
    }
}
