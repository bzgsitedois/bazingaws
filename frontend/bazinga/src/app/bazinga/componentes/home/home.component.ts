import { Component } from '@angular/core';
import {DefaultMenuList} from "../../../shared/types/default-menu-list";
import {menuOptions} from "../../menu-list";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  public menuOptions: (DefaultMenuList)[] = menuOptions;
}
