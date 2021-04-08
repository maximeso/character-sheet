import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CharacterSkill from './character-skill';
import CharacterSkillDetail from './character-skill-detail';
import CharacterSkillUpdate from './character-skill-update';
import CharacterSkillDeleteDialog from './character-skill-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CharacterSkillUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CharacterSkillUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CharacterSkillDetail} />
      <ErrorBoundaryRoute path={match.url} component={CharacterSkill} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CharacterSkillDeleteDialog} />
  </>
);

export default Routes;
