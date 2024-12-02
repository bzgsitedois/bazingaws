import {DefaultMenuList} from "../shared/types/default-menu-list";
import {environment} from "../../environments/environment-dev";



export const menuOptions: DefaultMenuList[] = [
  {
    menuName: "Inicio",
    route: environment.PREFIX_BASE + "/inicio",
  } ,
  {
    menuName: "Jogadores",
    route: environment.PREFIX_BASE + "/jogadores",
  },
  {
    menuName: "Times",
    route: environment.PREFIX_BASE + "/times",
  },
  {
    menuName: "Produtos",
    route: environment.PREFIX_BASE + "/produtos",
  },
];
