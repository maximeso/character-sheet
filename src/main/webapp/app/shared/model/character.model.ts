import { ICharacterSkill } from 'app/shared/model/character-skill.model';
import { IDeity } from 'app/shared/model/deity.model';
import { IRace } from 'app/shared/model/race.model';
import { ICareer } from 'app/shared/model/career.model';
import { IItem } from 'app/shared/model/item.model';
import { Alignment } from 'app/shared/model/enumerations/alignment.model';

export interface ICharacter {
  id?: number;
  name?: string | null;
  alignment?: Alignment | null;
  experience?: number | null;
  party?: string | null;
  skills?: ICharacterSkill | null;
  deity?: IDeity | null;
  blood?: IDeity | null;
  race?: IRace | null;
  career?: ICareer | null;
  inventories?: IItem[] | null;
}

export const defaultValue: Readonly<ICharacter> = {};
