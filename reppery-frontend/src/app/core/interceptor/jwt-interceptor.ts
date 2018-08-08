import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "../../../../node_modules/@angular/common/http";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";

@Injectable()
export class JwtInterceptor implements HttpInterceptor{
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const jwtToken = localStorage.getItem('jwtToken');
    if (jwtToken) {
      const cloned = req.clone({
        headers: req.headers.set('Authorization', 'Bearer ' + jwtToken)
      });

      return next.handle(cloned);
    }
    return next.handle(req);
  }
}
