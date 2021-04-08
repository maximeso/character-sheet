import { ISkill } from 'app/shared/model/skill.model';
import { ICharacter } from 'app/shared/model/character.model';

export interface ICharacterSkill {
  id?: number;
  event?: string;
  realCost?: number;
  skills?: ISkill[] | null;
  owners?: ICharacter[] | null;
}

export const defaultValue: Readonly<ICharacterSkill> = {};
