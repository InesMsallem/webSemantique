import { Component, OnInit } from '@angular/core';
import { TutorialService } from 'src/app/services/tutorial.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-pages-add',
    templateUrl: './pages-add.component.html',
    styleUrls: ['./pages-add.component.css']
})
export class PagesAddComponent implements OnInit {

    name = '';
    url = '';
    category = 'news';
    likesCount = 0;

    public addPage() {
        let page = {
            name: this.name,
            url: this.url,
            category: this.category,
            likesCount: this.likesCount
        };
        
        this.tutorialService.addPage(page).subscribe((res) => {
            this.router.navigate(["/pages"]);
        },(err)=>{
            this.router.navigate(["/pages"]);
        });
    }

    constructor(private tutorialService: TutorialService, private router: Router) { }

    ngOnInit() {
    }

}
