import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICharacter } from 'app/shared/model/character.model';
import { getEntities as getCharacters } from 'app/entities/character/character.reducer';
import { ISkill } from 'app/shared/model/skill.model';
import { getEntities as getSkills } from 'app/entities/skill/skill.reducer';
import { getEntity, updateEntity, createEntity, reset } from './character-skill.reducer';
import { ICharacterSkill } from 'app/shared/model/character-skill.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICharacterSkillUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CharacterSkillUpdate = (props: ICharacterSkillUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { characterSkillEntity, characters, skills, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/character-skill');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getCharacters();
    props.getSkills();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...characterSkillEntity,
        ...values,
        owner: characters.find(it => it.id.toString() === values.ownerId.toString()),
        skill: skills.find(it => it.id.toString() === values.skillId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="characterSheetApp.characterSkill.home.createOrEditLabel" data-cy="CharacterSkillCreateUpdateHeading">
            <Translate contentKey="characterSheetApp.characterSkill.home.createOrEditLabel">Create or edit a CharacterSkill</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : characterSkillEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="character-skill-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="character-skill-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="eventLabel" for="character-skill-event">
                  <Translate contentKey="characterSheetApp.characterSkill.event">Event</Translate>
                </Label>
                <AvField
                  id="character-skill-event"
                  data-cy="event"
                  type="text"
                  name="event"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="realCostLabel" for="character-skill-realCost">
                  <Translate contentKey="characterSheetApp.characterSkill.realCost">Real Cost</Translate>
                </Label>
                <AvField
                  id="character-skill-realCost"
                  data-cy="realCost"
                  type="string"
                  className="form-control"
                  name="realCost"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="character-skill-owner">
                  <Translate contentKey="characterSheetApp.characterSkill.owner">Owner</Translate>
                </Label>
                <AvInput id="character-skill-owner" data-cy="owner" type="select" className="form-control" name="ownerId">
                  <option value="" key="0" />
                  {characters
                    ? characters.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="character-skill-skill">
                  <Translate contentKey="characterSheetApp.characterSkill.skill">Skill</Translate>
                </Label>
                <AvInput id="character-skill-skill" data-cy="skill" type="select" className="form-control" name="skillId">
                  <option value="" key="0" />
                  {skills
                    ? skills.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/character-skill" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  characters: storeState.character.entities,
  skills: storeState.skill.entities,
  characterSkillEntity: storeState.characterSkill.entity,
  loading: storeState.characterSkill.loading,
  updating: storeState.characterSkill.updating,
  updateSuccess: storeState.characterSkill.updateSuccess,
});

const mapDispatchToProps = {
  getCharacters,
  getSkills,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CharacterSkillUpdate);
