import {Classes} from './enum/classes';

export interface JogadorCreateDTO {
  id?: number;
  nome?: string;
  email?: string;
  senha?: string;
  classesId?: number[];
}


export interface JogadorProjectionDTO{
  id: number;
  nome: string;
  fotoPath: string;
  classes: Classes[];
  time_id: number;
  liderTime: boolean;
}
