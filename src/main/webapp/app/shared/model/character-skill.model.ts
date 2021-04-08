import { ISkill } from 'app/shared/model/skill.model';

export interface ICharacterSkill {
  id?: number;
  event?: string | null;
  realCost?: number | null;
  skills?: ISkill | null;
  skills?: ISkill | null;
}

export const defaultValue: Readonly<ICharacterSkill> = {};
