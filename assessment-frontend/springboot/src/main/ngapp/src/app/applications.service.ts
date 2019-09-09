import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginauthService } from './service/loginauth.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApplicationsService {

  apps:string[]
  constructor(private http:HttpClient,
    private loginauth:LoginauthService) { }

  storeApps(apps){ //Store work items in database
    this.apps = apps;
    let id = this.loginauth.getCurrentSessionID(); //determine identifier in database via Session ID

    return this.http.put<string>(environment.apiTarget+`/session/demographic/works/${id}`, [apps]);
  }
}
