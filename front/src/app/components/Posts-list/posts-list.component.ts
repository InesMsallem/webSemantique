import { Component, OnInit } from '@angular/core';
import { TutorialService } from 'src/app/services/tutorial.service';

@Component({
  selector: 'app-tutorials-list',
  templateUrl: './posts-list.component.html',
  styleUrls: ['./posts-list.component.css']
})
export class TutorialsListComponent implements OnInit {
  posts: any[] = [];
  newPostContent: string = ''; 

  constructor(private tutorialService: TutorialService) {}

  ngOnInit(): void {
    this.retrievePosts();
  }

  retrievePosts() {
    this.tutorialService.getPosts().subscribe(
      (data: any) => {
        this.posts = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  addPost() {
    const newPost = {
      content: this.newPostContent,
    };

    this.tutorialService.addPost(newPost).subscribe(
      (data: any) => {
        console.log('New post added:', data);
        this.newPostContent = ''; 
        this.retrievePosts(); 
      },
      (error) => {
        console.error('Error adding new post:', error);

        if (error.status === 400) {
        } else {
        }
      }
    );
  }

  updatePost(postURI: string, newContent: string) {
    this.tutorialService.updatePost(postURI, newContent).subscribe(
      (data: any) => {
        console.log('Post updated:', data);
        this.retrievePosts(); 
      },
      (error) => {
        console.error('Error updating post:', error);

        if (error.status === 400) {
        } else {
        }
      }
    );
  }

  deletePost(postURI: string) {
    this.tutorialService.deletePost(postURI).subscribe(
      (data: any) => {
        console.log('Post deleted:', data);
        this.retrievePosts(); 
      },
      (error) => {
        console.error('Error deleting post:', error);

        if (error.status === 400) {
        } else {
        }
      }
    );
  }
}
