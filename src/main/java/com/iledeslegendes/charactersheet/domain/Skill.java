package com.iledeslegendes.charactersheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Skill.
 */
@Entity
@Table(name = "skill")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "cost", nullable = false)
    private Integer cost;

    @NotNull
    @Column(name = "restriction", nullable = false)
    private String restriction;

    @ManyToOne
    private Race racialCondition;

    @ManyToOne
    private Career careerCondition;

    @ManyToOne
    @JsonIgnoreProperties(value = { "racialCondition", "careerCondition", "skillCondition", "characterSkill" }, allowSetters = true)
    private Skill skillCondition;

    @ManyToOne
    @JsonIgnoreProperties(value = { "skills", "owners" }, allowSetters = true)
    private CharacterSkill characterSkill;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Skill id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Skill name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCost() {
        return this.cost;
    }

    public Skill cost(Integer cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getRestriction() {
        return this.restriction;
    }

    public Skill restriction(String restriction) {
        this.restriction = restriction;
        return this;
    }

    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }

    public Race getRacialCondition() {
        return this.racialCondition;
    }

    public Skill racialCondition(Race race) {
        this.setRacialCondition(race);
        return this;
    }

    public void setRacialCondition(Race race) {
        this.racialCondition = race;
    }

    public Career getCareerCondition() {
        return this.careerCondition;
    }

    public Skill careerCondition(Career career) {
        this.setCareerCondition(career);
        return this;
    }

    public void setCareerCondition(Career career) {
        this.careerCondition = career;
    }

    public Skill getSkillCondition() {
        return this.skillCondition;
    }

    public Skill skillCondition(Skill skill) {
        this.setSkillCondition(skill);
        return this;
    }

    public void setSkillCondition(Skill skill) {
        this.skillCondition = skill;
    }

    public CharacterSkill getCharacterSkill() {
        return this.characterSkill;
    }

    public Skill characterSkill(CharacterSkill characterSkill) {
        this.setCharacterSkill(characterSkill);
        return this;
    }

    public void setCharacterSkill(CharacterSkill characterSkill) {
        this.characterSkill = characterSkill;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Skill)) {
            return false;
        }
        return id != null && id.equals(((Skill) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Skill{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", cost=" + getCost() +
            ", restriction='" + getRestriction() + "'" +
            "}";
    }
}
