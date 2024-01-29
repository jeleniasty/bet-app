import { Component } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'betapp-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  registerForm: FormGroup;
  backendUrl: String = environment.backendUrl;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(5)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  get username(): AbstractControl | null {
    return this.registerForm.get('username');
  }

  get email(): AbstractControl | null {
    return this.registerForm.get('email');
  }
  get password(): AbstractControl | null {
    return this.registerForm.get('password');
  }

  register(): void {
    const formValues = this.registerForm.value;

    if (formValues.username && formValues.email && formValues.password) {
      this.authService
        .register(formValues.username, formValues.email, formValues.password)
        .subscribe(() => {
          this.router.navigateByUrl('/login');
        });
    }
  }

  isFormControlValid(formControl: AbstractControl | null): boolean {
    return formControl ? formControl.invalid && formControl.touched : false;
  }
}
