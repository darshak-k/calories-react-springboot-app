import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Food from './food';
import UserInfo from './user-info';
import FoodUser from './food-user';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}food`} component={Food} />
      <ErrorBoundaryRoute path={`${match.url}user-info`} component={UserInfo} />
      <ErrorBoundaryRoute path={`${match.url}food-user`} component={FoodUser} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
