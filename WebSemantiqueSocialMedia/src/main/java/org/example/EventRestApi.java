package org.example;

import org.example.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/events")
public class EventRestApi {
  @Autowired
  private EventService eventService;

  @GetMapping("/eventsByDate/{desiredDate}")
  @ResponseStatus
  public ResponseEntity<?> cat(@PathVariable String desiredDate) {
    return ResponseEntity.ok(eventService.getEventByDate(desiredDate));
  }

  @GetMapping("/all")
  @ResponseStatus
  public ResponseEntity<?> all() {
    return ResponseEntity.ok(eventService.getAll());
  }
}
