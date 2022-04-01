import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FoodUser from './food-user';
import FoodUserDetail from './food-user-detail';
import FoodUserUpdate from './food-user-update';
import FoodUserDeleteDialog from './food-user-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FoodUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FoodUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FoodUserDetail} />
      <ErrorBoundaryRoute path={match.url} component={FoodUser} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FoodUserDeleteDialog} />
  </>
);

export default Routes;
