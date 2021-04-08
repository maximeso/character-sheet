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

import { IDeity, defaultValue } from 'app/shared/model/deity.model';

export const ACTION_TYPES = {
  FETCH_DEITY_LIST: 'deity/FETCH_DEITY_LIST',
  FETCH_DEITY: 'deity/FETCH_DEITY',
  CREATE_DEITY: 'deity/CREATE_DEITY',
  UPDATE_DEITY: 'deity/UPDATE_DEITY',
  PARTIAL_UPDATE_DEITY: 'deity/PARTIAL_UPDATE_DEITY',
  DELETE_DEITY: 'deity/DELETE_DEITY',
  RESET: 'deity/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDeity>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type DeityState = Readonly<typeof initialState>;

// Reducer

export default (state: DeityState = initialState, action): DeityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DEITY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DEITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_DEITY):
    case REQUEST(ACTION_TYPES.UPDATE_DEITY):
    case REQUEST(ACTION_TYPES.DELETE_DEITY):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_DEITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_DEITY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DEITY):
    case FAILURE(ACTION_TYPES.CREATE_DEITY):
    case FAILURE(ACTION_TYPES.UPDATE_DEITY):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_DEITY):
    case FAILURE(ACTION_TYPES.DELETE_DEITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEITY_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_DEITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_DEITY):
    case SUCCESS(ACTION_TYPES.UPDATE_DEITY):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_DEITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_DEITY):
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

const apiUrl = 'api/deities';

// Actions

export const getEntities: ICrudGetAllAction<IDeity> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DEITY_LIST,
    payload: axios.get<IDeity>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IDeity> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DEITY,
    payload: axios.get<IDeity>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IDeity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DEITY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IDeity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DEITY,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IDeity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_DEITY,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDeity> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DEITY,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
