import {HttpClient} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {JogadorCreateDTO, JogadorProjectionDTO} from '../../entity/jogador';
import {Observable} from 'rxjs';
import {TimeProjectionDTO} from '../../entity/time';

@Injectable({
  providedIn: 'root'
})
export class TimeService {

  private http: HttpClient = inject(HttpClient);

  private api = environment.URL_BASE + 'time';


  buscar(id: number): Observable<TimeProjectionDTO> {
    return this
      .http
      .get<TimeProjectionDTO>(`${this.api}/${id}`)
  }

  criarTime(user: Partial<TimeProjectionDTO>): Observable<TimeProjectionDTO> {
    return this.http.post<TimeProjectionDTO>(this.api, user);
  }

  listarTimes(page: number, size: number): Observable<any> {
    return this.http.post<any>(`${this.api+"/listAll"}?page=${page}&size=${size}`, {});
  }



}
