import { ICharacter } from 'app/shared/model/character.model';

export interface IItem {
  id?: number;
  reference?: string | null;
  name?: string | null;
  comment?: number | null;
  owner?: ICharacter | null;
}

export const defaultValue: Readonly<IItem> = {};
