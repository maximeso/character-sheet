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

    @OneToMany(mappedBy = "characterSkill")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "racialCondition", "careerCondition", "skillCondition", "characterSkill" }, allowSetters = true)
    private Set<Skill> skills = new HashSet<>();

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

    public Set<Skill> getSkills() {
        return this.skills;
    }

    public CharacterSkill skills(Set<Skill> skills) {
        this.setSkills(skills);
        return this;
    }

    public CharacterSkill addSkill(Skill skill) {
        this.skills.add(skill);
        skill.setCharacterSkill(this);
        return this;
    }

    public CharacterSkill removeSkill(Skill skill) {
        this.skills.remove(skill);
        skill.setCharacterSkill(null);
        return this;
    }

    public void setSkills(Set<Skill> skills) {
        if (this.skills != null) {
            this.skills.forEach(i -> i.setCharacterSkill(null));
        }
        if (skills != null) {
            skills.forEach(i -> i.setCharacterSkill(this));
        }
        this.skills = skills;
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
