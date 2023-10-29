import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TutorialService {
  private apiBaseUrl = 'http://localhost:8088';

  constructor(private http: HttpClient) {}

  getPosts() {
    return this.http.get(`${this.apiBaseUrl}/posts/all`);
  }

  addPost(post: any) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded'
      })
    };

    return this.http.post(`${this.apiBaseUrl}/posts/addPost`, post, httpOptions);
  }

  updatePost(postURI: string, newContent: string) {
    return this.http.put(`${this.apiBaseUrl}/updatePost?postURI=${postURI}&newContent=${newContent}`, {});
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
}
