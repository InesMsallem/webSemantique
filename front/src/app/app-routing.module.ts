import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { TutorialsListComponent } from "./components/Posts-list/posts-list.component";
import { GroupListComponent } from "./components/group-list/group-list.component";
import { PagesListComponent } from "./components/pages-list/pages-list.component";
import { CommentListComponent } from "./components/comment-list/comment-list.component";
import { EventListComponent } from "./components/event-list/event-list.component";

const routes: Routes = [
  { path: "", redirectTo: "home", pathMatch: "full" },
  { path: "group", component: GroupListComponent },
  { path: "comments", component: CommentListComponent },
  { path: "pages", component: PagesListComponent },
  { path: "home", component: TutorialsListComponent },
  { path: "event", component: EventListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
})
export class AppRoutingModule {}
