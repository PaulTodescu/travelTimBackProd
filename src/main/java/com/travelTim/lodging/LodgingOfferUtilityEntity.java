package com.travelTim.lodging;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "lodging_utility")
public class LodgingOfferUtilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "utilities")
    @JsonIgnore
    private Set<LodgingOfferEntity> lodgingOffers = new HashSet<>();

    public LodgingOfferUtilityEntity() {
    }

    public LodgingOfferUtilityEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<LodgingOfferEntity> getLodgingOffers() {
        return lodgingOffers;
    }

    public void setLodgingOffers(Set<LodgingOfferEntity> lodgingOffers) {
        this.lodgingOffers = lodgingOffers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LodgingOfferUtilityEntity utility = (LodgingOfferUtilityEntity) o;
        return id.equals(utility.id) && name.equals(utility.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "[id: " + id + ", name: " + name + "]";
    }
}
