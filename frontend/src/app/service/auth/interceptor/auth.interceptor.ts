import { Injectable } from "@angular/core";
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Observable } from "rxjs";
import {TokenService} from '../../token/token.service';


@Injectable({
  providedIn: 'root'
})
export class AuthInterceptor implements HttpInterceptor {


  constructor(private tokenService : TokenService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (req.headers.has('Skip-Interceptor') && req.headers.get('Skip-Interceptor') === 'true') {
      const headers = req.headers.delete('Skip-Interceptor');
      const clonedRequest = req.clone({ headers });
      return next.handle(clonedRequest);
    }

    const token = this.tokenService.getToken();
    const authReq = req.clone({
      setHeaders: { 'Authorization': `Bearer ${token}` }
    });
    return next.handle(authReq);
  }
}
