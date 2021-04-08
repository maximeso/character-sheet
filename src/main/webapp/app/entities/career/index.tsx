import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Career from './career';
import CareerDetail from './career-detail';
import CareerUpdate from './career-update';
import CareerDeleteDialog from './career-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CareerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CareerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CareerDetail} />
      <ErrorBoundaryRoute path={match.url} component={Career} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CareerDeleteDialog} />
  </>
);

export default Routes;
