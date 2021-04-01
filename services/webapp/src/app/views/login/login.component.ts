import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, ValidationErrors, Validators} from '@angular/forms';
import {MemberService} from '../../service/member.service';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {ErrorStateMatcher} from '@angular/material/core';
import {UserDto} from '../../model/userDto';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';
import {
  fadeInLeftOnEnterAnimation, tadaOnEnterAnimation,
  zoomInOnEnterAnimation
} from 'angular-animations';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  animations: [
    fadeInLeftOnEnterAnimation(),
    tadaOnEnterAnimation(),
    zoomInOnEnterAnimation({anchor: 'enter', duration: 700})
  ]
})
export class LoginComponent implements OnInit, OnDestroy {

  step = 0;
  hidePasswordLogin = true;
  hidePassword = true;
  hidePasswordRepeat = true;
  loginForm: FormGroup;
  createAccountForm: FormGroup;
  passwordErrorStateMatcher = new PasswordErrorStateMatcher();
  accountCreated = false;
  private componentDestroyed$ = new Subject<void>();

  constructor(private fb: FormBuilder, private memberService: MemberService, private toastr: ToastrService, private router: Router) {
  }

  ngOnInit(): void {


  }

  changeStep(step: number): void {
    switch (step) {
      case 0:
        break;
      case 1:
        this.initLoginForm();
        break;
      case 2:
        this.initCreateAccountForm();
        break;
    }
    this.step = step;
  }

  initLoginForm(): void {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  initCreateAccountForm(): void {
    this.accountCreated = false;
    this.createAccountForm = this.fb.group({
      username: ['', [
        Validators.required,
        Validators.pattern('[a-zA-Z0-9]*'),
        Validators.minLength(6),
        Validators.maxLength(16)]
      ],
      password: ['', [
        Validators.required,
        Validators.pattern('[-_!$\*\.\(\)@#a-zA-Z0-9]*'),
        Validators.minLength(6),
        Validators.maxLength(16)]
      ],
      passwordRepeat: ['', [Validators.required]]
    }, {validators: this.passwordsSameValidation});
    this.createAccountForm.get('username').valueChanges.pipe(takeUntil(this.componentDestroyed$)).subscribe(value => {
      if (value) {
        this.checkUsername(value);
      }
    });
  }

  checkUsername(username: string): void {
    this.memberService.checkUsernameAvailable(username).pipe(takeUntil(this.componentDestroyed$)).subscribe(value => {
      if (!value) {
        this.createAccountForm.get('username').setErrors({alreadyTaken: true});
      } else {
        // this.createAccountForm.get('username').setErrors(null);
      }
    });
  }

  passwordsSameValidation(group: FormGroup): ValidationErrors | null {
    const password = group.get('password').value;
    const repeatPassword = group.get('passwordRepeat').value;
    if (password === null || repeatPassword === null || password === '' || repeatPassword === '') {
      return null;
    }
    if (password !== repeatPassword) {
      return ({notSame: true});
    } else {
      return null;
    }
  }

  createAccount(): void {
    const user: UserDto = new UserDto();
    user.username = this.createAccountForm.get('username').value;
    user.password = this.createAccountForm.get('password').value;
    this.memberService.createUser(user).pipe(takeUntil(this.componentDestroyed$)).subscribe(value => {
      if (value?.id) {
        this.accountCreated = true;
        this.changeStep(1);
        this.toastr.success('Account created! Please sign-in!');
      } else {
        this.toastr.error('Something went wrong! Please try again later.');
      }
    });
  }

  login(): void {
    const user: UserDto = new UserDto();
    user.username = this.loginForm.get('username').value;
    user.password = this.loginForm.get('password').value;
    this.memberService.login(user).pipe(takeUntil(this.componentDestroyed$)).subscribe(value => {
        if (value) {
          this.memberService.loggedInUser = value;
          this.router.navigate(['/member']);
          this.toastr.success('Login successful');
        } else {
          this.toastr.error('Could not login, please try again later.');
        }
      }, (error) => {
        if (error?.error?.errorCode === 'C001') {
          this.toastr.error('Bad Credentials');
          this.loginForm.setErrors({badCredentials: true});
        } else {
          this.toastr.error('Could not login, please try again later.');
        }
      }
    );
  }

  ngOnDestroy(): void {
    this.componentDestroyed$.next();
    this.componentDestroyed$.complete();
  }

}

export class PasswordErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const invalidCtrl = !!(control && control.invalid && control.dirty);
    const hasNotSame = control.parent.hasError('notSame');
    return (invalidCtrl || hasNotSame);
  }
}
