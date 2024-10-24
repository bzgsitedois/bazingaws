import {bootstrapApplication, BrowserModule, provideClientHydration} from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi} from "@angular/common/http";
import {importProvidersFrom} from "@angular/core";
import {NoPreloading, provideRouter, withPreloading} from "@angular/router";
import {provideAnimations} from "@angular/platform-browser/animations";
import {MAT_DATE_LOCALE} from "@angular/material/core";
import {provideToastr} from "ngx-toastr";
import {APP_ROUTES} from "./app/app.routes";
import {JwtModule} from "@auth0/angular-jwt";
import { register as registerSwiperElements} from 'swiper/element/bundle';

registerSwiperElements();
bootstrapApplication(AppComponent, {
  providers: [
    {provide: MAT_DATE_LOCALE, useValue: 'pt-BR'},
    // importProvidersFrom(BrowserModule, LottieModule.forRoot({ player: playerFactory })),
    provideHttpClient(withInterceptorsFromDi()),
    provideAnimations(),
    provideToastr(),
    provideRouter(APP_ROUTES, withPreloading(NoPreloading)),
    provideClientHydration(),
    importProvidersFrom(
      JwtModule.forRoot({
        config: {
          allowedDomains: ["localhost:4200"],
          disallowedRoutes: ["http://example.com/examplebadroute/"],
        },
      }),
    ),
    // AuthGuard,
    // { provide: HTTP_INTERCEPTORS, useClass: LoginInterceptor, multi: true }
  ]
})
  .catch((err) => console.error(err));

