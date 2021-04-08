import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './skill.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISkillDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SkillDetail = (props: ISkillDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { skillEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="skillDetailsHeading">
          <Translate contentKey="characterSheetApp.skill.detail.title">Skill</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{skillEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="characterSheetApp.skill.name">Name</Translate>
            </span>
          </dt>
          <dd>{skillEntity.name}</dd>
          <dt>
            <span id="cost">
              <Translate contentKey="characterSheetApp.skill.cost">Cost</Translate>
            </span>
          </dt>
          <dd>{skillEntity.cost}</dd>
          <dt>
            <span id="restriction">
              <Translate contentKey="characterSheetApp.skill.restriction">Restriction</Translate>
            </span>
          </dt>
          <dd>{skillEntity.restriction}</dd>
          <dt>
            <Translate contentKey="characterSheetApp.skill.racialCondition">Racial Condition</Translate>
          </dt>
          <dd>{skillEntity.racialCondition ? skillEntity.racialCondition.name : ''}</dd>
          <dt>
            <Translate contentKey="characterSheetApp.skill.careerCondition">Career Condition</Translate>
          </dt>
          <dd>{skillEntity.careerCondition ? skillEntity.careerCondition.name : ''}</dd>
          <dt>
            <Translate contentKey="characterSheetApp.skill.skillCondition">Skill Condition</Translate>
          </dt>
          <dd>{skillEntity.skillCondition ? skillEntity.skillCondition.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/skill" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/skill/${skillEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ skill }: IRootState) => ({
  skillEntity: skill.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SkillDetail);
