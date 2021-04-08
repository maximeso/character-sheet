package com.iledeslegendes.charactersheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
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

    @Column(name = "event")
    private String event;

    @Column(name = "real_cost")
    private Integer realCost;

    @JsonIgnoreProperties(value = { "racialCondition", "careerCondition", "skillCondition" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Skill skills;

    @ManyToOne
    @JsonIgnoreProperties(value = { "racialCondition", "careerCondition", "skillCondition" }, allowSetters = true)
    private Skill skills;

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

    public Skill getSkills() {
        return this.skills;
    }

    public CharacterSkill skills(Skill skill) {
        this.setSkills(skill);
        return this;
    }

    public void setSkills(Skill skill) {
        this.skills = skill;
    }

    public Skill getSkills() {
        return this.skills;
    }

    public CharacterSkill skills(Skill skill) {
        this.setSkills(skill);
        return this;
    }

    public void setSkills(Skill skill) {
        this.skills = skill;
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
