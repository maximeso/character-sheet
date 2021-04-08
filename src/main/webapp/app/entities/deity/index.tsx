import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Deity from './deity';
import DeityDetail from './deity-detail';
import DeityUpdate from './deity-update';
import DeityDeleteDialog from './deity-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DeityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DeityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DeityDetail} />
      <ErrorBoundaryRoute path={match.url} component={Deity} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DeityDeleteDialog} />
  </>
);

export default Routes;
