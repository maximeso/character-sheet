import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction,
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICharacterSkill, defaultValue } from 'app/shared/model/character-skill.model';

export const ACTION_TYPES = {
  FETCH_CHARACTERSKILL_LIST: 'characterSkill/FETCH_CHARACTERSKILL_LIST',
  FETCH_CHARACTERSKILL: 'characterSkill/FETCH_CHARACTERSKILL',
  CREATE_CHARACTERSKILL: 'characterSkill/CREATE_CHARACTERSKILL',
  UPDATE_CHARACTERSKILL: 'characterSkill/UPDATE_CHARACTERSKILL',
  PARTIAL_UPDATE_CHARACTERSKILL: 'characterSkill/PARTIAL_UPDATE_CHARACTERSKILL',
  DELETE_CHARACTERSKILL: 'characterSkill/DELETE_CHARACTERSKILL',
  RESET: 'characterSkill/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICharacterSkill>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CharacterSkillState = Readonly<typeof initialState>;

// Reducer

export default (state: CharacterSkillState = initialState, action): CharacterSkillState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CHARACTERSKILL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CHARACTERSKILL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CHARACTERSKILL):
    case REQUEST(ACTION_TYPES.UPDATE_CHARACTERSKILL):
    case REQUEST(ACTION_TYPES.DELETE_CHARACTERSKILL):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CHARACTERSKILL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CHARACTERSKILL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CHARACTERSKILL):
    case FAILURE(ACTION_TYPES.CREATE_CHARACTERSKILL):
    case FAILURE(ACTION_TYPES.UPDATE_CHARACTERSKILL):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CHARACTERSKILL):
    case FAILURE(ACTION_TYPES.DELETE_CHARACTERSKILL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CHARACTERSKILL_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_CHARACTERSKILL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CHARACTERSKILL):
    case SUCCESS(ACTION_TYPES.UPDATE_CHARACTERSKILL):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CHARACTERSKILL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CHARACTERSKILL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/character-skills';

// Actions

export const getEntities: ICrudGetAllAction<ICharacterSkill> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CHARACTERSKILL_LIST,
    payload: axios.get<ICharacterSkill>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICharacterSkill> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CHARACTERSKILL,
    payload: axios.get<ICharacterSkill>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICharacterSkill> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CHARACTERSKILL,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<ICharacterSkill> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CHARACTERSKILL,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICharacterSkill> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CHARACTERSKILL,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICharacterSkill> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CHARACTERSKILL,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
