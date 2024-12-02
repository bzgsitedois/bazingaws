import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  isRightPanelActive = false;

  togglePanel(isActive: boolean): void {
    this.isRightPanelActive = isActive;
  }
}
