import { ICharacter } from 'app/shared/model/character.model';

export interface IItem {
  id?: number;
  reference?: string;
  name?: string;
  comment?: number;
  owner?: ICharacter | null;
}

export const defaultValue: Readonly<IItem> = {};
