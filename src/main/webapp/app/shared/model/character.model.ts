import { IDeity } from 'app/shared/model/deity.model';
import { IRace } from 'app/shared/model/race.model';
import { ICareer } from 'app/shared/model/career.model';
import { IItem } from 'app/shared/model/item.model';
import { ICharacterSkill } from 'app/shared/model/character-skill.model';
import { Alignment } from 'app/shared/model/enumerations/alignment.model';

export interface ICharacter {
  id?: number;
  name?: string;
  alignment?: Alignment;
  experience?: number;
  party?: string | null;
  deity?: IDeity | null;
  blood?: IDeity | null;
  race?: IRace | null;
  career?: ICareer | null;
  inventories?: IItem[] | null;
  skills?: ICharacterSkill[] | null;
}

export const defaultValue: Readonly<ICharacter> = {};
