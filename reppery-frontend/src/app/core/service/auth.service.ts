import { Injectable } from '@angular/core';
import * as jwt_decode from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  public setAuthentication(jwtToken: string): void {
    if (jwtToken) {
      localStorage.setItem('jwtToken', jwtToken);
    }
  }

  public removeAuthentication(): void {
    localStorage.removeItem('jwtToken');
  }

  public isAuthenticated(): boolean {
    const token = localStorage.getItem('jwtToken');
    if (token && !this.isJwtTokenExpired(token)) {
      return true;
    }
    return false;
  }

  private isJwtTokenExpired(token: string): boolean {
    const utcNow = Date.now().valueOf() / 1000;
    let expiryDate = jwt_decode(token).exp;
    return expiryDate < utcNow;
  }
}
