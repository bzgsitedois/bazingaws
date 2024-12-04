import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface TokenValidationResponse {
  id: number;
  subject: string;
  perfil: string;
}

@Injectable({
  providedIn: 'root',
})
export class TokenService {
  private readonly tokenKey = 'token';
  private validateUrl = environment.URL_BASE + 'auth/validate';

  constructor(private http: HttpClient) {}

  saveToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  clearToken(): void {
    localStorage.removeItem(this.tokenKey);
  }

  validateToken(): Observable<TokenValidationResponse | null> {
    const token = this.getToken();
    if (!token) {
      return of(null);
    }

    return this.http.post<TokenValidationResponse>(this.validateUrl, { token });
  }

  decodeToken(): any | null {
    const token = this.getToken();
    if (!token) return null;

    const payload = token.split('.')[1];
    try {
      return JSON.parse(atob(payload));
    } catch (e) {
      console.error('Erro ao decodificar o token:', e);
      return null;
    }
  }
  getTokenAttribute(attribute: string): any | null {
    const decodedToken = this.decodeToken();
    return decodedToken ? decodedToken[attribute] : null;
  }

}
