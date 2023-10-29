package org.example;

import org.example.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/comments")
public class CommentRestApi {
  @Autowired
  private CommentService commentService;

  @GetMapping("/all")
  @ResponseStatus
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok(commentService.getAll());
  }

  @GetMapping("/byUser")
  @ResponseStatus
  public ResponseEntity<?> getByUser(@RequestParam String username) {
    return ResponseEntity.ok(commentService.getByUser(username));
  }

  // @PostMapping
  // @ResponseStatus
  // public ResponseEntity<?> addComment(@RequestParam String content,
  // @RequestParam String userName,
  // @RequestParam String commentedOnURI) {
  // return ResponseEntity.ok(commentService.addComment(content, userName,
  // commentedOnURI));
  // }

}
