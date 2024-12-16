import { Injectable } from "@angular/core";
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Observable } from "rxjs";
import {TokenService} from '../../token/token.service';


@Injectable({
  providedIn: 'root'
})
export class AuthInterceptor implements HttpInterceptor {

  constructor(private tokenService: TokenService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (req.url.includes('/login')) {
      return next.handle(req);
    }

    const token = this.tokenService.getToken();

    if (token && token.trim() !== '') {
      const authReq = req.clone({
        setHeaders: { 'Authorization': `Bearer ${token}` }
      });
      return next.handle(authReq);  // Adiciona o token na requisição
    }

    return next.handle(req);
  }
}
