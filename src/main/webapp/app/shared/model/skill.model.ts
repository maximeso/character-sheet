import { IRace } from 'app/shared/model/race.model';
import { ICareer } from 'app/shared/model/career.model';
import { ICharacterSkill } from 'app/shared/model/character-skill.model';

export interface ISkill {
  id?: number;
  name?: string;
  cost?: number;
  restriction?: string;
  racialCondition?: IRace | null;
  careerCondition?: ICareer | null;
  skillCondition?: ISkill | null;
  characterSkill?: ICharacterSkill | null;
}

export const defaultValue: Readonly<ISkill> = {};
