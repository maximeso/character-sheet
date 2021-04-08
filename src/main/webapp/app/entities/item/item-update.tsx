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
import { getEntity, updateEntity, createEntity, reset } from './item.reducer';
import { IItem } from 'app/shared/model/item.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IItemUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ItemUpdate = (props: IItemUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { itemEntity, characters, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/item');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getCharacters();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...itemEntity,
        ...values,
        owner: characters.find(it => it.id.toString() === values.ownerId.toString()),
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
          <h2 id="characterSheetApp.item.home.createOrEditLabel" data-cy="ItemCreateUpdateHeading">
            <Translate contentKey="characterSheetApp.item.home.createOrEditLabel">Create or edit a Item</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : itemEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="item-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="item-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="referenceLabel" for="item-reference">
                  <Translate contentKey="characterSheetApp.item.reference">Reference</Translate>
                </Label>
                <AvField
                  id="item-reference"
                  data-cy="reference"
                  type="text"
                  name="reference"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="item-name">
                  <Translate contentKey="characterSheetApp.item.name">Name</Translate>
                </Label>
                <AvField
                  id="item-name"
                  data-cy="name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="commentLabel" for="item-comment">
                  <Translate contentKey="characterSheetApp.item.comment">Comment</Translate>
                </Label>
                <AvField
                  id="item-comment"
                  data-cy="comment"
                  type="string"
                  className="form-control"
                  name="comment"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="item-owner">
                  <Translate contentKey="characterSheetApp.item.owner">Owner</Translate>
                </Label>
                <AvInput id="item-owner" data-cy="owner" type="select" className="form-control" name="ownerId">
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
              <Button tag={Link} id="cancel-save" to="/item" replace color="info">
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
  itemEntity: storeState.item.entity,
  loading: storeState.item.loading,
  updating: storeState.item.updating,
  updateSuccess: storeState.item.updateSuccess,
});

const mapDispatchToProps = {
  getCharacters,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ItemUpdate);
