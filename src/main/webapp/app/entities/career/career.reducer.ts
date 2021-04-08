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

import { ICareer, defaultValue } from 'app/shared/model/career.model';

export const ACTION_TYPES = {
  FETCH_CAREER_LIST: 'career/FETCH_CAREER_LIST',
  FETCH_CAREER: 'career/FETCH_CAREER',
  CREATE_CAREER: 'career/CREATE_CAREER',
  UPDATE_CAREER: 'career/UPDATE_CAREER',
  PARTIAL_UPDATE_CAREER: 'career/PARTIAL_UPDATE_CAREER',
  DELETE_CAREER: 'career/DELETE_CAREER',
  RESET: 'career/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICareer>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CareerState = Readonly<typeof initialState>;

// Reducer

export default (state: CareerState = initialState, action): CareerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CAREER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CAREER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CAREER):
    case REQUEST(ACTION_TYPES.UPDATE_CAREER):
    case REQUEST(ACTION_TYPES.DELETE_CAREER):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CAREER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CAREER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CAREER):
    case FAILURE(ACTION_TYPES.CREATE_CAREER):
    case FAILURE(ACTION_TYPES.UPDATE_CAREER):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CAREER):
    case FAILURE(ACTION_TYPES.DELETE_CAREER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CAREER_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_CAREER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CAREER):
    case SUCCESS(ACTION_TYPES.UPDATE_CAREER):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CAREER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CAREER):
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

const apiUrl = 'api/careers';

// Actions

export const getEntities: ICrudGetAllAction<ICareer> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CAREER_LIST,
    payload: axios.get<ICareer>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICareer> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CAREER,
    payload: axios.get<ICareer>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICareer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CAREER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<ICareer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CAREER,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICareer> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CAREER,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICareer> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CAREER,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
