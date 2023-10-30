import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";

@Injectable({
  providedIn: "root",
})
export class TutorialService {
  private apiBaseUrl = "http://localhost:8088";

  constructor(private http: HttpClient) {}

  getPosts() {
    return this.http.get(`${this.apiBaseUrl}/posts/all`);
  }

  getPictures() {
    return this.http.get(`${this.apiBaseUrl}/posts/pictures`);
  }

  getVideos() {
    return this.http.get(`${this.apiBaseUrl}/posts/videos`);
  }

  getArticles() {
    return this.http.get(`${this.apiBaseUrl}/posts/articles`);
  }

  addPost(post: any) {
    const httpOptions = {
      headers: new HttpHeaders({
        "Content-Type": "application/x-www-form-urlencoded",
      }),
    };

    return this.http.post(
      `${this.apiBaseUrl}/posts/addPost`,
      post,
      httpOptions
    );
  }

  updatePost(postURI: string, newContent: string) {
    return this.http.put(
      `${this.apiBaseUrl}/updatePost?postURI=${postURI}&newContent=${newContent}`,
      {}
    );
  }

  deletePost(postURI: string) {
    return this.http.delete(`${this.apiBaseUrl}/deletePost?postURI=${postURI}`);
  }

  //Groups
  getGroups() {
    return this.http.get(`${this.apiBaseUrl}/groups/all`);
  }
  searchGroups(name: string) {
    return this.http.get(`${this.apiBaseUrl}/groups/all?groupName=${name}`);
  }

  getPages() {
    return this.http.get(`${this.apiBaseUrl}/pages/all`);
  }

  //Comments
  getComments() {
    return this.http.get(`${this.apiBaseUrl}/comments/all`);
  }
  getCommentsByUserName(username: string) {
    return this.http.get(
      `${this.apiBaseUrl}/comments/byUser?username=${username}`
    );
  }

  getUsers() {
    return this.http.get(`${this.apiBaseUrl}/users/all`);
  }

  getEvents() {
    return this.http.get(`${this.apiBaseUrl}/events/all`);
  }
}
