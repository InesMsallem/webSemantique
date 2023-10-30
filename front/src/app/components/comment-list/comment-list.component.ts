import { Component, OnInit } from "@angular/core";
import { TutorialService } from "src/app/services/tutorial.service";

@Component({
  selector: "app-comment-list",
  templateUrl: "./comment-list.component.html",
  styleUrls: ["./comment-list.component.css"],
})
export class CommentListComponent implements OnInit {
  comments: any[] = [];
  users: any[] = [];

  usernameFilter: string = "";
  filteredComments: any[] = [];

  constructor(private commentService: TutorialService) {}

  ngOnInit(): void {
    // Initialize comments with all comments
    this.commentService.getComments().subscribe(
      (data: any) => {
        this.comments = data;
        this.filteredComments = this.comments;
      },
      (error) => {
        console.log(error);
      }
    );

    this.fetchUsers();
  }

  fetchUsers() {
    this.commentService.getUsers().subscribe(
      (data: any) => {
        this.users = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  filterComments() {
    if (this.usernameFilter) {
      this.filteredComments = this.comments.filter((comment) => {
        return (
          comment.user_name.toLowerCase() === this.usernameFilter.toLowerCase()
        );
      });
    } else {
      this.filteredComments = this.comments;
    }
  }
}
