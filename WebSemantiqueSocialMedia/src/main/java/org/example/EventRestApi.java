package org.example;

import org.example.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/events")
public class EventRestApi {
  @Autowired
  private EventService eventService;

  @GetMapping("/all")
  @ResponseStatus
  public ResponseEntity<?> all() {
    return ResponseEntity.ok(eventService.getAll());
  }
}
