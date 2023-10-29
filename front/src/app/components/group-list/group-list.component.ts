import { Component, OnInit } from '@angular/core';
import { TutorialService } from 'src/app/services/tutorial.service';

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrls: ['./group-list.component.css']
})
export class GroupListComponent implements OnInit {
filteredGroups: any[] = [];
allGroups: any[] = [];
searchTerm: string = '';
groups: any[] = [];

constructor(private tutorialService: TutorialService) {}

ngOnInit() {
  this.getGroups();
}

getGroups() {
  this.tutorialService.getGroups().subscribe(
    (data: any) => {
      this.allGroups = data;
      this.filteredGroups = this.allGroups; // Initialize filteredGroups
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

filterByType(type: string) {
  if (type === 'public') {
    this.filteredGroups = this.allGroups.filter((group) => group.type === 'http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#publicGroup');
  } else if (type === 'private') {
    this.filteredGroups = this.allGroups.filter((group) => group.type === 'http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#privateGroup');
  }
}

showAllGroups() {
  this.filteredGroups = this.allGroups; // Show all groups
}

getTypeName(type: string): string {
  // You can implement a method to extract the type name from the URL here
  if (type.includes('privateGroup')) {
    return 'Private';
  } else if (type.includes('publicGroup')) {
    return 'Public';
  } else {
    return 'Unknown';
  }
}

}
