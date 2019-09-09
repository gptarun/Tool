import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '../../../node_modules/@angular/router';
import { LoginauthService } from './loginauth.service';


//Handles redirecting and makes sure users progress through the assessment correctly.
@Injectable({
  providedIn: 'root'
})
export class RouteGuardService implements CanActivate{

  constructor(private loginAuth: LoginauthService,
  private router: Router) { }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
    if(localStorage.getItem('authSession')){
      let routeUrl = this.formatUrl(route.url);
      if(routeUrl == this.loginAuth.getSession().url){
        return true;
      }
      this.router.navigate([this.loginAuth.getSession().url])
      return false;
     
    }

    if(localStorage.getItem('lastPage')){
      let routeUrl = this.formatUrl(route.url);
      if(routeUrl == localStorage.getItem('lastPage')){
        return true;
      }
      this.router.navigate([localStorage.getItem('lastPage')]);
      return false;
    }

    if(state.url == '/bg')
      return true;
    this.router.navigate(['bg'])
    return false;
  }

  formatUrl(url){
    let formattedURL = '/'
    url.forEach((segment)=>{
      formattedURL = formattedURL+segment+'/'
    })
    
    formattedURL = formattedURL.slice(0,-1);
    return decodeURIComponent(formattedURL);

  }
}
