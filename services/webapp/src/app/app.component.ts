import { Component } from '@angular/core';
import {fas} from '@fortawesome/free-solid-svg-icons';
import {far} from '@fortawesome/free-regular-svg-icons';
import {FaIconLibrary} from '@fortawesome/angular-fontawesome';


/**
 * Wrapper component
 */
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Movie Match - Movie Recommendation System';
  constructor(lib: FaIconLibrary) {
    lib.addIconPacks(far);
    lib.addIconPacks(fas);
  }
}
