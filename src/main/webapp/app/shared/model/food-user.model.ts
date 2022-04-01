import dayjs from 'dayjs';
import { IFood } from 'app/shared/model/food.model';
import { IUserInfo } from 'app/shared/model/user-info.model';

export interface IFoodUser {
  id?: number;
  eatenAt?: string | null;
  food?: IFood | null;
  foodUser?: IUserInfo | null;
}

export const defaultValue: Readonly<IFoodUser> = {};
