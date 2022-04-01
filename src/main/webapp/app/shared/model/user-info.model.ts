import { IFoodUser } from 'app/shared/model/food-user.model';

export interface IUserInfo {
  id?: number;
  userId?: string | null;
  foodUsers?: IFoodUser[] | null;
}

export const defaultValue: Readonly<IUserInfo> = {};
