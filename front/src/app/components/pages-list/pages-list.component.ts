import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TutorialService } from 'src/app/services/tutorial.service';

@Component({
  selector: 'app-pages-list',
  templateUrl: './pages-list.component.html',
  styleUrls: ['./pages-list.component.css']
})
export class PagesListComponent implements OnInit {

  pages = [];

  keyword = '';

  category = 'news';

  constructor(private tutorialService: TutorialService, private router: Router) { }

  ngOnInit() {
    // this.getPages();
  }

  goToAdd() {
    this.router.navigate(["/pages/add"])
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

  getByCategory() {
    if (this.category == '')
        return alert("Please put category");
    this.tutorialService.getPageByCat(this.category).subscribe(
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
