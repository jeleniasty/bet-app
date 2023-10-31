import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'betapp-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  login(): void {
    const loginFormValues = this.loginForm.value;

    if (loginFormValues.email && loginFormValues.password) {
      this.authService
        .login(loginFormValues.email, loginFormValues.password)
        .subscribe((response: any): void => {
          this.authService.storeAuthToken(response.token);
          this.router.navigateByUrl('/');
        });
    }
  }
}
