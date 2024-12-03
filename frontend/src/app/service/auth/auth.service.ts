import {HttpClient} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {JogadorCreateDTO} from '../../entity/jogador';
import {Observable} from 'rxjs';
import {LoginDTO} from '../../entity/login';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private http: HttpClient = inject(HttpClient);

  private authApi = environment.URL_BASE + 'auth';



  login(user: Partial<LoginDTO>): Observable<LoginDTO> {
    return this.http.post<LoginDTO>(this.authApi+'/login', user);
  }



}
