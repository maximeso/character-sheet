import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './character.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICharacterDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CharacterDetail = (props: ICharacterDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { characterEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="characterDetailsHeading">
          <Translate contentKey="characterSheetApp.character.detail.title">Character</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{characterEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="characterSheetApp.character.name">Name</Translate>
            </span>
          </dt>
          <dd>{characterEntity.name}</dd>
          <dt>
            <span id="alignment">
              <Translate contentKey="characterSheetApp.character.alignment">Alignment</Translate>
            </span>
          </dt>
          <dd>{characterEntity.alignment}</dd>
          <dt>
            <span id="experience">
              <Translate contentKey="characterSheetApp.character.experience">Experience</Translate>
            </span>
          </dt>
          <dd>{characterEntity.experience}</dd>
          <dt>
            <span id="party">
              <Translate contentKey="characterSheetApp.character.party">Party</Translate>
            </span>
          </dt>
          <dd>{characterEntity.party}</dd>
          <dt>
            <Translate contentKey="characterSheetApp.character.deity">Deity</Translate>
          </dt>
          <dd>{characterEntity.deity ? characterEntity.deity.name : ''}</dd>
          <dt>
            <Translate contentKey="characterSheetApp.character.blood">Blood</Translate>
          </dt>
          <dd>{characterEntity.blood ? characterEntity.blood.name : ''}</dd>
          <dt>
            <Translate contentKey="characterSheetApp.character.race">Race</Translate>
          </dt>
          <dd>{characterEntity.race ? characterEntity.race.name : ''}</dd>
          <dt>
            <Translate contentKey="characterSheetApp.character.career">Career</Translate>
          </dt>
          <dd>{characterEntity.career ? characterEntity.career.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/character" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/character/${characterEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ character }: IRootState) => ({
  characterEntity: character.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CharacterDetail);
