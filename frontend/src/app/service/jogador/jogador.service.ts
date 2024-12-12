import {HttpClient} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {JogadorCreateDTO, JogadorProjectionDTO} from '../../entity/jogador';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JogadorService {

  private http: HttpClient = inject(HttpClient);

  private api = environment.URL_BASE + 'jogador';


  buscar(id: number): Observable<JogadorProjectionDTO> {
    return this
      .http
      .get<JogadorProjectionDTO>(`${this.api}/${id}`)
  }

  criarUsuario(user: Partial<JogadorCreateDTO>): Observable<JogadorCreateDTO> {
    return this.http.post<JogadorCreateDTO>(this.api, user);
  }

  listarJogadores(page: number, size: number, filtro: any ): Observable<any> {
    const params = `?page=${page}&size=${size}`;
    return this.http.post<any>(`${this.api}/listAll${params}`, filtro);
  }

  exLider(id: number ): Observable<string>{
    const body = {  };
    return this.http.put<string>(`${this.api}/exLider/${id}` , body)
  }
  novoLider(id: number ): Observable<string>{
    const body = {  };
    return this.http.put<string>(`${this.api}/novoLider/${id}` , body)
  }


}
