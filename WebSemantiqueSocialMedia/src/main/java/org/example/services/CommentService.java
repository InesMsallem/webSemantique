package org.example.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.example.utils.JenaUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class CommentService {
  public List<?> getAll() {
    String query = "SELECT ?comment ?commentContent ?userUsername WHERE {\n" +
        "  ?comment a <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Comment>.\n" +
        "  ?comment <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#content> ?commentContent.\n"
        +
        "  OPTIONAL { ?comment <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#commentedBy> ?user.\n"
        +
        "             ?user <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#username> ?userUsername }.\n"
        +
        "}";

    List<List<String>> fields = new ArrayList<>();
    // fill array on creation
    fields.add(new ArrayList<String>() {
      {
        add("comment");
        add("commentURI");
      }
    });
    fields.add(new ArrayList<String>() {
      {
        add("commentContent");
        add("content");
      }
    });
    fields.add(new ArrayList<String>() {
      {
        add("userUsername");
        add("user_name");
      }
    });

    return JenaUtils.get().executeSelect(query, fields);
  }

  public List<?> getByUser(String username) {
    String query = "SELECT ?comment ?commentContent ?userUsername WHERE {\n" +
        "  ?comment a <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Comment>.\n" +
        "  ?comment <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#content> ?commentContent.\n"
        +
        "  ?comment <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#commentedBy> ?user.\n" +
        "  ?user <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#username> ?userUsername.\n" +
        "  FILTER (str(?userUsername) = '" + username + "').\n" +
        "}";

    List<List<String>> fields = new ArrayList<>();
    // fill array on creation
    fields.add(new ArrayList<String>() {
      {
        add("comment");
        add("commentURI");
      }
    });
    fields.add(new ArrayList<String>() {
      {
        add("commentContent");
        add("content");
      }
    });
    fields.add(new ArrayList<String>() {
      {
        add("userUsername");
        add("user_name");
      }
    });

    return JenaUtils.get().executeSelect(query, fields);
  }

  // @PostMapping("/addComment")
  // public ResponseEntity<String> addComment(
  // @RequestParam("content") String content,
  // @RequestParam("user_name") String userName,
  // @RequestParam("commentedOn") String commentedOnURI) {

  // Model model = ModelFactory.createDefaultModel();
  // model.read("src/main/java/org/example/socialMedia.rdf");

  // OntModel ontModel =
  // ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

  // // Create a new comment individual
  // Individual commentIndividual = ontModel.createIndividual(
  // "http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Comment_"
  // + System.currentTimeMillis(),
  // ontModel.getOntClass("http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Comment"));

  // // Add content property to the comment
  // commentIndividual.addProperty(
  // ontModel.getDatatypeProperty("http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#content"),
  // content);

  // // Use SPARQL query to retrieve the user individual based on user_name
  // String sparqlUserQuery = "SELECT ?user WHERE { ?user
  // <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#username>
  // \""
  // + userName + "\". }";
  // QueryExecution userQueryExecution =
  // QueryExecutionFactory.create(sparqlUserQuery, ontModel);
  // ResultSet userResultSet = userQueryExecution.execSelect();

  // if (userResultSet.hasNext()) {
  // QuerySolution userSolution = userResultSet.nextSolution();
  // RDFNode userNode = userSolution.get("user");

  // // Link the comment to the user (commentedBy property)
  // commentIndividual.addProperty(ontModel.getObjectProperty(
  // "http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#commentedBy"),
  // userNode);

  // // Link the comment to the commentedOn resource (commentedOn property)
  // commentIndividual.addProperty(
  // ontModel
  // .getObjectProperty("http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#commentedOn"),
  // ontModel.getResource(commentedOnURI));

  // try (OutputStream outputStream = new
  // FileOutputStream("src/main/java/org/example/socialMedia.rdf")) {
  // ontModel.write(outputStream, "RDF/XML-ABBREV");
  // } catch (IOException e) {
  // e.printStackTrace();
  // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed
  // to add the comment.");
  // }
  // return ResponseEntity.status(HttpStatus.CREATED).body("Comment added
  // successfully.");
  // } else {
  // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found.");
  // }
  // }

  // @DeleteMapping("/deleteComment")
  // public ResponseEntity<String> deleteComment(@RequestParam("commentURI")
  // String commentURI) {
  // Model model = ModelFactory.createDefaultModel();
  // model.read("src/main/java/org/example/socialMedia.rdf");

  // OntModel ontModel =
  // ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

  // Individual commentIndividual = ontModel.getIndividual(commentURI);

  // if (commentIndividual != null) {
  // commentIndividual.remove();

  // try (OutputStream outputStream = new
  // FileOutputStream("src/main/java/org/example/socialMedia.rdf")) {
  // ontModel.write(outputStream, "RDF/XML-ABBREV");
  // } catch (IOException e) {
  // e.printStackTrace();
  // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed
  // to delete the comment.");
  // }

  // return ResponseEntity.status(HttpStatus.OK).body("Comment deleted
  // successfully.");
  // } else {
  // return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not
  // found.");
  // }
  // }
}
