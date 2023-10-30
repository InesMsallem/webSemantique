import { Component, OnInit } from "@angular/core";
import { TutorialService } from "src/app/services/tutorial.service";

@Component({
  selector: "app-event-list",
  templateUrl: "./event-list.component.html",
  styleUrls: ["./event-list.component.css"],
})
export class EventListComponent implements OnInit {
  events: any[] = [];

  constructor(private eventService: TutorialService) {}

  ngOnInit(): void {
    this.fetchEvents();
  }

  fetchEvents() {
    this.eventService.getEvents().subscribe(
      (data: any) => {
        this.events = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
