import { Component } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'betapp-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  loginForm: FormGroup;
  showAuthenticationError: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(5)]],
    });
  }

  get email(): AbstractControl | null {
    return this.loginForm.get('email');
  }

  get password(): AbstractControl | null {
    return this.loginForm.get('password');
  }

  login(): void {
    const loginFormValues = this.loginForm.value;

    if (loginFormValues.email && loginFormValues.password) {
      this.authService
        .login(loginFormValues.email, loginFormValues.password)
        .subscribe({
          next: (response: any): void => {
            this.authService.storeAuthToken(response.token);
            this.router.navigateByUrl('/');
          },
          error: (error: any): void => {
            if (error.status === 401) {
              this.showAuthenticationError = true;
            }
          },
        });
    }
  }

  isFormControlValid(formControl: AbstractControl | null): boolean {
    return formControl ? formControl.invalid && formControl.touched : false;
  }
}
