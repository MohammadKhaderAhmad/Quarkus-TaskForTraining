package org.acme.forSimCard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.acme.forCitizen.Citizen;

@Entity
public class SimCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long number;
    String provider;
    boolean isActive;

    @ManyToOne
    @JsonIgnore
    Citizen citizen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

}
