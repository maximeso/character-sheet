import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Character from './character';
import CharacterSkill from './character-skill';
import Item from './item';
import Deity from './deity';
import Career from './career';
import Race from './race';
import Skill from './skill';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}character`} component={Character} />
      <ErrorBoundaryRoute path={`${match.url}character-skill`} component={CharacterSkill} />
      <ErrorBoundaryRoute path={`${match.url}item`} component={Item} />
      <ErrorBoundaryRoute path={`${match.url}deity`} component={Deity} />
      <ErrorBoundaryRoute path={`${match.url}career`} component={Career} />
      <ErrorBoundaryRoute path={`${match.url}race`} component={Race} />
      <ErrorBoundaryRoute path={`${match.url}skill`} component={Skill} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
