import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IFood } from 'app/shared/model/food.model';
import { getEntities as getFoods } from 'app/entities/food/food.reducer';
import { IUserInfo } from 'app/shared/model/user-info.model';
import { getEntities as getUserInfos } from 'app/entities/user-info/user-info.reducer';
import { getEntity, updateEntity, createEntity, reset } from './food-user.reducer';
import { IFoodUser } from 'app/shared/model/food-user.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FoodUserUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const foods = useAppSelector(state => state.food.entities);
  const userInfos = useAppSelector(state => state.userInfo.entities);
  const foodUserEntity = useAppSelector(state => state.foodUser.entity);
  const loading = useAppSelector(state => state.foodUser.loading);
  const updating = useAppSelector(state => state.foodUser.updating);
  const updateSuccess = useAppSelector(state => state.foodUser.updateSuccess);
  const handleClose = () => {
    props.history.push('/food-user' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getFoods({}));
    dispatch(getUserInfos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.eatenAt = convertDateTimeToServer(values.eatenAt);

    const entity = {
      ...foodUserEntity,
      ...values,
      food: foods.find(it => it.id.toString() === values.food.toString()),
      foodUser: userInfos.find(it => it.id.toString() === values.foodUser.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          eatenAt: displayDefaultDateTime(),
        }
      : {
          ...foodUserEntity,
          eatenAt: convertDateTimeFromServer(foodUserEntity.eatenAt),
          food: foodUserEntity?.food?.id,
          foodUser: foodUserEntity?.foodUser?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="caloriesappApp.foodUser.home.createOrEditLabel" data-cy="FoodUserCreateUpdateHeading">
            Create or edit a FoodUser
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="food-user-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Eaten At"
                id="food-user-eatenAt"
                name="eatenAt"
                data-cy="eatenAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="food-user-food" name="food" data-cy="food" label="Food" type="select">
                <option value="" key="0" />
                {foods
                  ? foods.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="food-user-foodUser" name="foodUser" data-cy="foodUser" label="Food User" type="select">
                <option value="" key="0" />
                {userInfos
                  ? userInfos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/food-user" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default FoodUserUpdate;
