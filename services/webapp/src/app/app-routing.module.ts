import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginComponent} from './views/login/login.component';
import {MemberAreaComponent} from './views/member-area/member-area.component';
import {AuthGuardService} from './service/auth-guard.service';
import {FreeAreaComponent} from './views/free-area/free-area.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'free',
    component: FreeAreaComponent
  },
  {
    path: 'member',
    canActivate: [AuthGuardService],
    component: MemberAreaComponent
  },
  {path: '', redirectTo: '/login', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
