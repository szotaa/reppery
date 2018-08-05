import { Injectable } from '@angular/core';

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

  public getAuthentication(): string {
    const jwtToken = localStorage.getItem('jwtToken');
    if (jwtToken) {
      return jwtToken;
    }
    return null;
  }

  public isAuthenticated(): boolean {
    if (localStorage.getItem('jwtToken') == null || localStorage.getItem('jwtToken') === '') {
      return false;
    }
    return true;
  }
}
