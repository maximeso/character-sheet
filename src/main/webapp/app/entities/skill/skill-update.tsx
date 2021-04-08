import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IRace } from 'app/shared/model/race.model';
import { getEntities as getRaces } from 'app/entities/race/race.reducer';
import { ICareer } from 'app/shared/model/career.model';
import { getEntities as getCareers } from 'app/entities/career/career.reducer';
import { getEntities as getSkills } from 'app/entities/skill/skill.reducer';
import { getEntity, updateEntity, createEntity, reset } from './skill.reducer';
import { ISkill } from 'app/shared/model/skill.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISkillUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SkillUpdate = (props: ISkillUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { skillEntity, races, careers, skills, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/skill');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getRaces();
    props.getCareers();
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
        ...skillEntity,
        ...values,
        racialCondition: races.find(it => it.id.toString() === values.racialConditionId.toString()),
        careerCondition: careers.find(it => it.id.toString() === values.careerConditionId.toString()),
        skillCondition: skills.find(it => it.id.toString() === values.skillConditionId.toString()),
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
          <h2 id="characterSheetApp.skill.home.createOrEditLabel" data-cy="SkillCreateUpdateHeading">
            <Translate contentKey="characterSheetApp.skill.home.createOrEditLabel">Create or edit a Skill</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : skillEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="skill-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="skill-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="skill-name">
                  <Translate contentKey="characterSheetApp.skill.name">Name</Translate>
                </Label>
                <AvField id="skill-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="costLabel" for="skill-cost">
                  <Translate contentKey="characterSheetApp.skill.cost">Cost</Translate>
                </Label>
                <AvField id="skill-cost" data-cy="cost" type="string" className="form-control" name="cost" />
              </AvGroup>
              <AvGroup>
                <Label id="restrictionLabel" for="skill-restriction">
                  <Translate contentKey="characterSheetApp.skill.restriction">Restriction</Translate>
                </Label>
                <AvField id="skill-restriction" data-cy="restriction" type="text" name="restriction" />
              </AvGroup>
              <AvGroup>
                <Label for="skill-racialCondition">
                  <Translate contentKey="characterSheetApp.skill.racialCondition">Racial Condition</Translate>
                </Label>
                <AvInput
                  id="skill-racialCondition"
                  data-cy="racialCondition"
                  type="select"
                  className="form-control"
                  name="racialConditionId"
                >
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
                <Label for="skill-careerCondition">
                  <Translate contentKey="characterSheetApp.skill.careerCondition">Career Condition</Translate>
                </Label>
                <AvInput
                  id="skill-careerCondition"
                  data-cy="careerCondition"
                  type="select"
                  className="form-control"
                  name="careerConditionId"
                >
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
              <AvGroup>
                <Label for="skill-skillCondition">
                  <Translate contentKey="characterSheetApp.skill.skillCondition">Skill Condition</Translate>
                </Label>
                <AvInput id="skill-skillCondition" data-cy="skillCondition" type="select" className="form-control" name="skillConditionId">
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
              <Button tag={Link} id="cancel-save" to="/skill" replace color="info">
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
  races: storeState.race.entities,
  careers: storeState.career.entities,
  skills: storeState.skill.entities,
  skillEntity: storeState.skill.entity,
  loading: storeState.skill.loading,
  updating: storeState.skill.updating,
  updateSuccess: storeState.skill.updateSuccess,
});

const mapDispatchToProps = {
  getRaces,
  getCareers,
  getSkills,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SkillUpdate);
