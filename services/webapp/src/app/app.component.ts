import { Component } from '@angular/core';
import {fas} from '@fortawesome/free-solid-svg-icons';
import {far} from '@fortawesome/free-regular-svg-icons';
import {FaIconLibrary} from '@fortawesome/angular-fontawesome';
import {StarRatingComponent} from 'ng-starrating';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'webapp';
  constructor(lib: FaIconLibrary) {
    lib.addIconPacks(far);
    lib.addIconPacks(fas);
  }
}
