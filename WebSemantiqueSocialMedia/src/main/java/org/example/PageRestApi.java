package org.example;

import org.example.models.Page;
import org.example.services.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

  @PostMapping
  @ResponseStatus
  public ResponseEntity<?> add(@RequestBody Page page) {
    System.out.println(page.name + " " + page.pageUrl + " " + page.likesCount);
    return ResponseEntity.ok(pageService.add(page.name, page.pageUrl, page.likesCount));
  }
}
