export interface IBaseApiResponse<T> {
  message: string;
  data?: T;
}
