import { ICharacter } from 'app/shared/model/character.model';
import { ISkill } from 'app/shared/model/skill.model';

export interface ICharacterSkill {
  id?: number;
  event?: string;
  realCost?: number;
  owner?: ICharacter | null;
  skill?: ISkill | null;
}

export const defaultValue: Readonly<ICharacterSkill> = {};
