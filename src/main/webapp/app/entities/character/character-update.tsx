import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICharacterSkill } from 'app/shared/model/character-skill.model';
import { getEntities as getCharacterSkills } from 'app/entities/character-skill/character-skill.reducer';
import { IDeity } from 'app/shared/model/deity.model';
import { getEntities as getDeities } from 'app/entities/deity/deity.reducer';
import { IRace } from 'app/shared/model/race.model';
import { getEntities as getRaces } from 'app/entities/race/race.reducer';
import { ICareer } from 'app/shared/model/career.model';
import { getEntities as getCareers } from 'app/entities/career/career.reducer';
import { getEntity, updateEntity, createEntity, reset } from './character.reducer';
import { ICharacter } from 'app/shared/model/character.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICharacterUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CharacterUpdate = (props: ICharacterUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { characterEntity, characterSkills, deities, races, careers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/character');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getCharacterSkills();
    props.getDeities();
    props.getRaces();
    props.getCareers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...characterEntity,
        ...values,
        skills: characterSkills.find(it => it.id.toString() === values.skillsId.toString()),
        deity: deities.find(it => it.id.toString() === values.deityId.toString()),
        blood: deities.find(it => it.id.toString() === values.bloodId.toString()),
        race: races.find(it => it.id.toString() === values.raceId.toString()),
        career: careers.find(it => it.id.toString() === values.careerId.toString()),
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
          <h2 id="characterSheetApp.character.home.createOrEditLabel" data-cy="CharacterCreateUpdateHeading">
            <Translate contentKey="characterSheetApp.character.home.createOrEditLabel">Create or edit a Character</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : characterEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="character-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="character-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="character-name">
                  <Translate contentKey="characterSheetApp.character.name">Name</Translate>
                </Label>
                <AvField id="character-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="alignmentLabel" for="character-alignment">
                  <Translate contentKey="characterSheetApp.character.alignment">Alignment</Translate>
                </Label>
                <AvInput
                  id="character-alignment"
                  data-cy="alignment"
                  type="select"
                  className="form-control"
                  name="alignment"
                  value={(!isNew && characterEntity.alignment) || 'LAWFUL_GOOD'}
                >
                  <option value="LAWFUL_GOOD">{translate('characterSheetApp.Alignment.LAWFUL_GOOD')}</option>
                  <option value="NEUTRAL_GOOD">{translate('characterSheetApp.Alignment.NEUTRAL_GOOD')}</option>
                  <option value="CHAOTIC_GOOD">{translate('characterSheetApp.Alignment.CHAOTIC_GOOD')}</option>
                  <option value="LAWFUL_NEUTRAL">{translate('characterSheetApp.Alignment.LAWFUL_NEUTRAL')}</option>
                  <option value="NEUTRAL_NEUTRAL">{translate('characterSheetApp.Alignment.NEUTRAL_NEUTRAL')}</option>
                  <option value="CHAOTIC_NEUTRAL">{translate('characterSheetApp.Alignment.CHAOTIC_NEUTRAL')}</option>
                  <option value="LAWFUL_EVIL">{translate('characterSheetApp.Alignment.LAWFUL_EVIL')}</option>
                  <option value="NEUTRAL_EVIL">{translate('characterSheetApp.Alignment.NEUTRAL_EVIL')}</option>
                  <option value="CHAOTIC_EVIL">{translate('characterSheetApp.Alignment.CHAOTIC_EVIL')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="experienceLabel" for="character-experience">
                  <Translate contentKey="characterSheetApp.character.experience">Experience</Translate>
                </Label>
                <AvField id="character-experience" data-cy="experience" type="string" className="form-control" name="experience" />
              </AvGroup>
              <AvGroup>
                <Label id="partyLabel" for="character-party">
                  <Translate contentKey="characterSheetApp.character.party">Party</Translate>
                </Label>
                <AvField id="character-party" data-cy="party" type="text" name="party" />
              </AvGroup>
              <AvGroup>
                <Label for="character-skills">
                  <Translate contentKey="characterSheetApp.character.skills">Skills</Translate>
                </Label>
                <AvInput id="character-skills" data-cy="skills" type="select" className="form-control" name="skillsId">
                  <option value="" key="0" />
                  {characterSkills
                    ? characterSkills.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="character-deity">
                  <Translate contentKey="characterSheetApp.character.deity">Deity</Translate>
                </Label>
                <AvInput id="character-deity" data-cy="deity" type="select" className="form-control" name="deityId">
                  <option value="" key="0" />
                  {deities
                    ? deities.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="character-blood">
                  <Translate contentKey="characterSheetApp.character.blood">Blood</Translate>
                </Label>
                <AvInput id="character-blood" data-cy="blood" type="select" className="form-control" name="bloodId">
                  <option value="" key="0" />
                  {deities
                    ? deities.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="character-race">
                  <Translate contentKey="characterSheetApp.character.race">Race</Translate>
                </Label>
                <AvInput id="character-race" data-cy="race" type="select" className="form-control" name="raceId">
                  <option value="" key="0" />
                  {races
                    ? races.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="character-career">
                  <Translate contentKey="characterSheetApp.character.career">Career</Translate>
                </Label>
                <AvInput id="character-career" data-cy="career" type="select" className="form-control" name="careerId">
                  <option value="" key="0" />
                  {careers
                    ? careers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/character" replace color="info">
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
  characterSkills: storeState.characterSkill.entities,
  deities: storeState.deity.entities,
  races: storeState.race.entities,
  careers: storeState.career.entities,
  characterEntity: storeState.character.entity,
  loading: storeState.character.loading,
  updating: storeState.character.updating,
  updateSuccess: storeState.character.updateSuccess,
});

const mapDispatchToProps = {
  getCharacterSkills,
  getDeities,
  getRaces,
  getCareers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CharacterUpdate);
