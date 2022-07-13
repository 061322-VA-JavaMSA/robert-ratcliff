import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { TtaalComponent } from './ttaal/ttaal.component';

const routes: Routes = [{
  path: '',
  component: HomeComponent
},{
  path: 'ttaal',
  component:TtaalComponent
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
