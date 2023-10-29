package org.example;

import org.example.services.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pages")
public class PageRestApi {
  @Autowired
  private PageService pageService;

  @GetMapping("/all")
  @ResponseStatus
  public ResponseEntity<?> all() {
    return ResponseEntity.ok(pageService.getAll());
  }
}
