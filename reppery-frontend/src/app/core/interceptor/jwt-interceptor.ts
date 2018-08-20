import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "../../../../node_modules/@angular/common/http";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {AuthService} from "../service/auth.service";

@Injectable()
export class JwtInterceptor implements HttpInterceptor{

  constructor(
    private auth: AuthService
  ){}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const jwtToken = localStorage.getItem('jwtToken');
    if (this.auth.isAuthenticated()) {
      const cloned = req.clone({
        headers: req.headers.set('Authorization', 'Bearer ' + jwtToken)
      });

      return next.handle(cloned);
    }
    return next.handle(req);
  }
}
