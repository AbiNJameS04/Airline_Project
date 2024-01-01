import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { SearchComponent } from './search/search.component';
import { RoundComponent } from './round/round.component';

const routes: Routes = [
  {path:'', component:HomeComponent},
  {path:'search',component:SearchComponent},
  {path:'round',component:RoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
