import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TutorialsListComponent } from './components/Posts-list/posts-list.component';
import { GroupListComponent } from './components/group-list/group-list.component';
import { PagesListComponent } from './components/pages-list/pages-list.component';
import { CommentListComponent } from './components/comment-list/comment-list.component';
import { EventListComponent } from './components/event-list/event-list.component';

@NgModule({
  declarations: [
    AppComponent,
    TutorialsListComponent,
    GroupListComponent,
    PagesListComponent,
    CommentListComponent,
    EventListComponent,
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
