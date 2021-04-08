import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/character">
      <Translate contentKey="global.menu.entities.character" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/character-skill">
      <Translate contentKey="global.menu.entities.characterSkill" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/item">
      <Translate contentKey="global.menu.entities.item" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/deity">
      <Translate contentKey="global.menu.entities.deity" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/career">
      <Translate contentKey="global.menu.entities.career" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/race">
      <Translate contentKey="global.menu.entities.race" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/skill">
      <Translate contentKey="global.menu.entities.skill" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
