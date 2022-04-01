export interface IFood {
  id?: number;
  foodName?: string;
  calorieValue?: number | null;
}

export const defaultValue: Readonly<IFood> = {};
