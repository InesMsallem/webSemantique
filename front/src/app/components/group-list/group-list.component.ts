import { Component, OnInit } from '@angular/core';
import { TutorialService } from 'src/app/services/tutorial.service';

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrls: ['./group-list.component.css']
})
export class GroupListComponent implements OnInit {
  groups: any[] = [];
  searchTerm: string = '';
  constructor(private tutorialService: TutorialService) {}

  ngOnInit() {
    this.getGroups();
  }

  getGroups() {
    this.tutorialService.getGroups().subscribe(
      (data: any) => {
        this.groups = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }

  searchGroups() {
    if (this.searchTerm) {
      this.tutorialService.searchGroups(this.searchTerm).subscribe(
        (data: any) => {
          this.groups = data;
        },
        (error) => {
          console.log(error);
        }
      );
    } else {
      this.getGroups(); 
    }
  }
  

}
