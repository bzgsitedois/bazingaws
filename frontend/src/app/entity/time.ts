import {Jogos} from './enum/jogos';

export interface TimeProjectionDTO{
  id:number;
  nome:string;
  descricao:string;
  jogadoresId: number[];
  jogos: Jogos[];
}

export interface TimeListAllDTO{
 id: number,
   nome: string,
 descricao: string,
 foto_path: string,
  lideres: string[],
   num_jogadores: number,
}
