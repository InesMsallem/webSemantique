import { Component, OnInit } from '@angular/core';
import { TutorialService } from 'src/app/services/tutorial.service';

@Component({
  selector: 'app-pages-list',
  templateUrl: './pages-list.component.html',
  styleUrls: ['./pages-list.component.css']
})
export class PagesListComponent implements OnInit {

  pages = [];

  keyword = '';

  constructor(private tutorialService: TutorialService) { }

  ngOnInit() {
    // this.getPages();
  }

  get filteredPages() {
    if (this.keyword == '') {
      return this.pages;
    }
    return this.pages.filter(page => {
      let name = page.name.toLowerCase();
      let url = page.url.toLowerCase();
      let keyword = this.keyword.toLowerCase();
      return name.includes(keyword) || url.includes(keyword);
    });
  }

  likes(likesCount) {
    // likesCount attr is in the form:  2^^http://www.w3.org/2001/XMLSchema#decimal, remove the ^^http://www.w3.org/2001/XMLSchema#decimal
    return likesCount.split('^^')[0];
  }

  getPages() {
    this.tutorialService.getPages().subscribe(
      (data: any) => {
        // some pages have no data, remove them
        data = data.filter(page => page.name != null);
        this.pages = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }

}
