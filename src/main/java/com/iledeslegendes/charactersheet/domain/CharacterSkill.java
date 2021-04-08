package com.iledeslegendes.charactersheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CharacterSkill.
 */
@Entity
@Table(name = "character_skill")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CharacterSkill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "event", nullable = false)
    private String event;

    @NotNull
    @Column(name = "real_cost", nullable = false)
    private Integer realCost;

    @ManyToOne
    @JsonIgnoreProperties(value = { "racialCondition", "careerCondition", "skillCondition" }, allowSetters = true)
    private Skill skill;

    @OneToMany(mappedBy = "skills")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "skills", "deity", "blood", "race", "career", "inventories" }, allowSetters = true)
    private Set<Character> owners = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CharacterSkill id(Long id) {
        this.id = id;
        return this;
    }

    public String getEvent() {
        return this.event;
    }

    public CharacterSkill event(String event) {
        this.event = event;
        return this;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Integer getRealCost() {
        return this.realCost;
    }

    public CharacterSkill realCost(Integer realCost) {
        this.realCost = realCost;
        return this;
    }

    public void setRealCost(Integer realCost) {
        this.realCost = realCost;
    }

    public Skill getSkill() {
        return this.skill;
    }

    public CharacterSkill skill(Skill skill) {
        this.setSkill(skill);
        return this;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Set<Character> getOwners() {
        return this.owners;
    }

    public CharacterSkill owners(Set<Character> characters) {
        this.setOwners(characters);
        return this;
    }

    public CharacterSkill addOwner(Character character) {
        this.owners.add(character);
        character.setSkills(this);
        return this;
    }

    public CharacterSkill removeOwner(Character character) {
        this.owners.remove(character);
        character.setSkills(null);
        return this;
    }

    public void setOwners(Set<Character> characters) {
        if (this.owners != null) {
            this.owners.forEach(i -> i.setSkills(null));
        }
        if (characters != null) {
            characters.forEach(i -> i.setSkills(this));
        }
        this.owners = characters;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CharacterSkill)) {
            return false;
        }
        return id != null && id.equals(((CharacterSkill) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CharacterSkill{" +
            "id=" + getId() +
            ", event='" + getEvent() + "'" +
            ", realCost=" + getRealCost() +
            "}";
    }
}
