package org.example;

import org.example.models.Page;
import org.example.services.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
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

  @GetMapping("/category/{category}")
  @ResponseStatus
  public ResponseEntity<?> cat(@PathVariable String category) {
    return ResponseEntity.ok(pageService.getByCategory(category));
  }

  @PostMapping
  @ResponseStatus
  public ResponseEntity<?> add(@RequestBody Page page) {
    return ResponseEntity.ok(pageService.add(page));
  }
}
