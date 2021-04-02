import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './views/login/login.component';
import {MatButtonModule} from '@angular/material/button';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {MatFormFieldModule} from '@angular/material/form-field';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HttpClientModule} from '@angular/common/http';
import {ToastrModule} from 'ngx-toastr';
import { MemberAreaComponent } from './views/member-area/member-area.component';
import { MovieRaterComponent } from './shared/movie-rater/movie-rater.component';
import { FreeAreaComponent } from './views/free-area/free-area.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MemberAreaComponent,
    MovieRaterComponent,
    FreeAreaComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    MatButtonModule,
    ReactiveFormsModule,
    FormsModule,
    MatInputModule,
    MatFormFieldModule,
    FontAwesomeModule,
    ToastrModule.forRoot({
      timeOut: 4000,
      positionClass: 'toast-top-center',
      preventDuplicates: true,
      toastClass: 'custom-toast'
    }),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
