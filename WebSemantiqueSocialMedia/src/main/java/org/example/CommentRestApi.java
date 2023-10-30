package org.example;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  @GetMapping("/dataByResourceType")
  public ResponseEntity<List<Map<String, String>>> getDataByResourceType(
          @RequestParam("resourceType") String resourceType) {

    Model model = ModelFactory.createDefaultModel();
    model.read("src/main/java/org/example/socialMedia.rdf");

    OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

    // Define SPARQL prefixes
    String prefixes = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
            + "PREFIX ex: <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#>\n";

    // Define a SPARQL query template
    String sparqlQueryTemplate = "SELECT ?resource ?content ?userUsername\n" +
            "WHERE {\n" +
            "  ?resource a ex:{{resourceType}}.\n" +
            "  ?resource ex:content ?content.\n" +
            "  OPTIONAL { ?resource ex:postedBy ?user.\n" +
            "             ?user ex:username ?userUsername }.\n" +
            "}";

    // Replace {{resourceType}} with the actual resource type
    String sparqlQuery = sparqlQueryTemplate.replace("{{resourceType}}", resourceType);

    QueryExecution queryExecution = QueryExecutionFactory.create(prefixes + sparqlQuery, ontModel);
    ResultSet resultSet = queryExecution.execSelect();

    List<Map<String, String>> resultList = new ArrayList<>();
    while (resultSet.hasNext()) {
      QuerySolution solution = resultSet.nextSolution();
      String resourceURI = solution.get("resource") != null ? solution.get("resource").toString() : null;
      String content = solution.get("content") != null ? solution.get("content").toString() : null;
      String userUsername = solution.get("userUsername") != null ? solution.get("userUsername").toString() : null;

      Map<String, String> resourceMap = new HashMap<>();
      if (resourceURI != null) resourceMap.put("resourceURI", resourceURI);
      if (content != null) resourceMap.put("content", content);
      if (userUsername != null) resourceMap.put("user_name", userUsername);
      resultList.add(resourceMap);
    }

    return ResponseEntity.ok(resultList);
  }



}
