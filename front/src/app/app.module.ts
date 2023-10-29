import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TutorialsListComponent } from './components/Posts-list/posts-list.component';
import { GroupListComponent } from './components/group-list/group-list.component';

@NgModule({
  declarations: [
    AppComponent,
    TutorialsListComponent,
    GroupListComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
