import {Jogos} from './enum/jogos';

export interface TimeProjectionDTO{
  id:number;
  nome:string;
  descricao:string;
  jogadoresId: number[];
  jogos: Jogos[];
}
