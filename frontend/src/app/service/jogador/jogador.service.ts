import {HttpClient} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {JogadorCreateDTO} from '../../entity/jogador';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JogadorService {

  private http: HttpClient = inject(HttpClient);

  private loginApi = environment.URL_BASE + 'jogador';



  criarUsuario(user: Partial<JogadorCreateDTO>): Observable<JogadorCreateDTO> {
    return this.http.post<JogadorCreateDTO>(this.loginApi, user);
  }



}
