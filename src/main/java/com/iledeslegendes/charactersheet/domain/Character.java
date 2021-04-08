package com.iledeslegendes.charactersheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iledeslegendes.charactersheet.domain.enumeration.Alignment;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Character.
 */
@Entity
@Table(name = "character")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Character implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "alignment", nullable = false)
    private Alignment alignment;

    @NotNull
    @Column(name = "experience", nullable = false)
    private Integer experience;

    @Column(name = "party")
    private String party;

    @ManyToOne
    @JsonIgnoreProperties(value = { "skill", "owners" }, allowSetters = true)
    private CharacterSkill skills;

    @ManyToOne
    private Deity deity;

    @ManyToOne
    private Deity blood;

    @ManyToOne
    private Race race;

    @ManyToOne
    private Career career;

    @OneToMany(mappedBy = "owner")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "owner" }, allowSetters = true)
    private Set<Item> inventories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Character name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Alignment getAlignment() {
        return this.alignment;
    }

    public Character alignment(Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public Integer getExperience() {
        return this.experience;
    }

    public Character experience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public String getParty() {
        return this.party;
    }

    public Character party(String party) {
        this.party = party;
        return this;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public CharacterSkill getSkills() {
        return this.skills;
    }

    public Character skills(CharacterSkill characterSkill) {
        this.setSkills(characterSkill);
        return this;
    }

    public void setSkills(CharacterSkill characterSkill) {
        this.skills = characterSkill;
    }

    public Deity getDeity() {
        return this.deity;
    }

    public Character deity(Deity deity) {
        this.setDeity(deity);
        return this;
    }

    public void setDeity(Deity deity) {
        this.deity = deity;
    }

    public Deity getBlood() {
        return this.blood;
    }

    public Character blood(Deity deity) {
        this.setBlood(deity);
        return this;
    }

    public void setBlood(Deity deity) {
        this.blood = deity;
    }

    public Race getRace() {
        return this.race;
    }

    public Character race(Race race) {
        this.setRace(race);
        return this;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Career getCareer() {
        return this.career;
    }

    public Character career(Career career) {
        this.setCareer(career);
        return this;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public Set<Item> getInventories() {
        return this.inventories;
    }

    public Character inventories(Set<Item> items) {
        this.setInventories(items);
        return this;
    }

    public Character addInventories(Item item) {
        this.inventories.add(item);
        item.setOwner(this);
        return this;
    }

    public Character removeInventories(Item item) {
        this.inventories.remove(item);
        item.setOwner(null);
        return this;
    }

    public void setInventories(Set<Item> items) {
        if (this.inventories != null) {
            this.inventories.forEach(i -> i.setOwner(null));
        }
        if (items != null) {
            items.forEach(i -> i.setOwner(this));
        }
        this.inventories = items;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Character)) {
            return false;
        }
        return id != null && id.equals(((Character) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Character{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", alignment='" + getAlignment() + "'" +
            ", experience=" + getExperience() +
            ", party='" + getParty() + "'" +
            "}";
    }
}
