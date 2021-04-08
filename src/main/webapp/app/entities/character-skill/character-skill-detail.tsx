import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './character-skill.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICharacterSkillDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CharacterSkillDetail = (props: ICharacterSkillDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { characterSkillEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="characterSkillDetailsHeading">
          <Translate contentKey="characterSheetApp.characterSkill.detail.title">CharacterSkill</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{characterSkillEntity.id}</dd>
          <dt>
            <span id="event">
              <Translate contentKey="characterSheetApp.characterSkill.event">Event</Translate>
            </span>
          </dt>
          <dd>{characterSkillEntity.event}</dd>
          <dt>
            <span id="realCost">
              <Translate contentKey="characterSheetApp.characterSkill.realCost">Real Cost</Translate>
            </span>
          </dt>
          <dd>{characterSkillEntity.realCost}</dd>
          <dt>
            <Translate contentKey="characterSheetApp.characterSkill.skills">Skills</Translate>
          </dt>
          <dd>{characterSkillEntity.skills ? characterSkillEntity.skills.name : ''}</dd>
          <dt>
            <Translate contentKey="characterSheetApp.characterSkill.skills">Skills</Translate>
          </dt>
          <dd>{characterSkillEntity.skills ? characterSkillEntity.skills.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/character-skill" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/character-skill/${characterSkillEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ characterSkill }: IRootState) => ({
  characterSkillEntity: characterSkill.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CharacterSkillDetail);
