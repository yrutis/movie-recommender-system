import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, ValidationErrors, Validators} from '@angular/forms';
import {MemberService} from '../../service/member.service';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {ErrorStateMatcher} from '@angular/material/core';
import {UserDto} from '../../model/userDto';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {

  step = 0;
  hidePassword = true;
  hidePasswordRepeat = true;
  createAccountForm: FormGroup;
  passwordErrorStateMatcher = new PasswordErrorStateMatcher();
  accountCreated = false;
  private componentDestroyed$ = new Subject<void>();

  constructor(private fb: FormBuilder, private memberService: MemberService, private toastr: ToastrService) {
  }

  ngOnInit(): void {
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
      if (value?.id){
        this.accountCreated = true;
        this.createAccountForm.reset();
        this.step = 1;
        this.toastr.success('Account created! Please sign-in!');
      } else {
        this.toastr.error('Something went wrong! Please try again later.');
      }
    });
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
