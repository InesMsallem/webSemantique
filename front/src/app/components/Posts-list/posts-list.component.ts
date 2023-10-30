import { Component, OnInit } from "@angular/core";
import { TutorialService } from "src/app/services/tutorial.service";
import { DomSanitizer, SafeResourceUrl } from "@angular/platform-browser";

@Component({
  selector: "app-tutorials-list",
  templateUrl: "./posts-list.component.html",
  styleUrls: ["./posts-list.component.css"],
})
export class TutorialsListComponent implements OnInit {
  allPosts: any[] = [];
  videoPosts: any[] = [];
  articlePosts: any[] = [];
  picturePosts: any[] = [];
  newPostContent: string = "";
  selectedPostType: string = "All";

  constructor(
    private tutorialService: TutorialService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.retrievePosts();
  }
  getFilteredPosts() {
    if (this.selectedPostType === "All") {
      return this.allPosts;
    } else if (this.selectedPostType === "Videos") {
      return this.videoPosts;
    } else if (this.selectedPostType === "Articles") {
      return this.articlePosts;
    } else if (this.selectedPostType === "Pictures") {
      return this.picturePosts;
    }
  }

  getVideoEmbedUrl(url: string): SafeResourceUrl {
    const videoId = this.getVideoId(url);
    const embedUrl = `https://www.youtube.com/embed/${videoId}`;
    return this.sanitizer.bypassSecurityTrustResourceUrl(embedUrl);
  }

  getVideoId(url: string): string {
    // Extract the video ID from the YouTube URL
    const videoId = url.split("v=")[1];
    return videoId;
  }
  onPostTypeChange() {
    if (this.selectedPostType === "All") {
      this.retrievePosts(); // Reload all posts
    } else if (this.selectedPostType === "Videos") {
      this.retrieveVideos();
    } else if (this.selectedPostType === "Articles") {
      this.retrieveArticles();
    } else if (this.selectedPostType === "Pictures") {
      this.retrievePictures();
    }
  }

  retrievePosts() {
    this.tutorialService.getPosts().subscribe(
      (data: any) => {
        this.allPosts = data;
        console.log(data);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  retrieveVideos() {
    this.tutorialService.getVideos().subscribe(
      (data: any) => {
        this.videoPosts = data;
        console.log(data);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  retrieveArticles() {
    this.tutorialService.getArticles().subscribe(
      (data: any) => {
        this.articlePosts = data;
        console.log(data);
      },
      (error) => {
        console.log(error);
      }
    );
  }

  retrievePictures() {
    this.tutorialService.getPictures().subscribe(
      (data: any) => {
        this.picturePosts = data;
        console.log(data);
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
        console.log("New post added:", data);
        this.newPostContent = "";
        this.retrievePosts();
      },
      (error) => {
        console.error("Error adding new post:", error);

        if (error.status === 400) {
        } else {
        }
      }
    );
  }

  updatePost(postURI: string, newContent: string) {
    this.tutorialService.updatePost(postURI, newContent).subscribe(
      (data: any) => {
        console.log("Post updated:", data);
        this.retrievePosts();
      },
      (error) => {
        console.error("Error updating post:", error);

        if (error.status === 400) {
        } else {
        }
      }
    );
  }

  deletePost(postURI: string) {
    this.tutorialService.deletePost(postURI).subscribe(
      (data: any) => {
        console.log("Post deleted:", data);
        this.retrievePosts();
      },
      (error) => {
        console.error("Error deleting post:", error);

        if (error.status === 400) {
        } else {
        }
      }
    );
  }

  toggleComments(post: any) {
    post.showComments = !post.showComments;
  }
}
