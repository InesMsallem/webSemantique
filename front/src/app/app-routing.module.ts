import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TutorialsListComponent } from './components/Posts-list/posts-list.component';
import { GroupListComponent } from './components/group-list/group-list.component';
import { PagesListComponent } from './components/pages-list/pages-list.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'group', component: GroupListComponent },
  { path: 'pages', component: PagesListComponent },
  { path: 'home', component: TutorialsListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
