import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './food-user.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FoodUserDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const foodUserEntity = useAppSelector(state => state.foodUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="foodUserDetailsHeading">FoodUser</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{foodUserEntity.id}</dd>
          <dt>
            <span id="eatenAt">Eaten At</span>
          </dt>
          <dd>{foodUserEntity.eatenAt ? <TextFormat value={foodUserEntity.eatenAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>Food</dt>
          <dd>{foodUserEntity.food ? foodUserEntity.food.id : ''}</dd>
          <dt>Food User</dt>
          <dd>{foodUserEntity.foodUser ? foodUserEntity.foodUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/food-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/food-user/${foodUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FoodUserDetail;
