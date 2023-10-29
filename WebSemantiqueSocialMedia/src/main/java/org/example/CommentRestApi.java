package org.example;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/comments/")
public class CommentRestApi {

    @GetMapping("/all")
    public ResponseEntity<List<Map<String, String>>> getCommentsData() {
        Model model = ModelFactory.createDefaultModel();
        model.read("src/main/java/org/example/socialMedia.rdf");

        // Create an OntModel that performs inference
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

        // Define your SPARQL query to retrieve comments
        String sparqlQuery = "SELECT ?comment ?commentContent ?userUsername WHERE {\n" +
                "  ?comment a <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Comment>.\n" +
                "  ?comment <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#content> ?commentContent.\n" +
                "  OPTIONAL { ?comment <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#commentedBy> ?user.\n" +
                "             ?user <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#username> ?userUsername }.\n" +
                "}";

        QueryExecution queryExecution = QueryExecutionFactory.create(QueryFactory.create(sparqlQuery), ontModel);
        ResultSet resultSet = queryExecution.execSelect();

        List<Map<String, String>> resultList = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.nextSolution();
            String commentURI = solution.get("comment") != null ? solution.get("comment").toString() : null; // Extract commentURI
            String commentContent = solution.get("commentContent") != null ? solution.get("commentContent").toString() : null;
            String userUsername = solution.get("userUsername") != null ? solution.get("userUsername").toString() : null;

            Map<String, String> commentMap = new HashMap<>();
            if (commentURI != null) commentMap.put("commentURI", commentURI); // Include commentURI in the response
            if (commentContent != null) commentMap.put("content", commentContent);
            if (userUsername != null) commentMap.put("user_name", userUsername);
            resultList.add(commentMap);
        }

        return ResponseEntity.ok(resultList);
    }

    @PostMapping("/addComment")
    public ResponseEntity<String> addComment(
            @RequestParam("content") String content,
            @RequestParam("user_name") String userName,
            @RequestParam("commentedOn") String commentedOnURI) {

        Model model = ModelFactory.createDefaultModel();
        model.read("src/main/java/org/example/socialMedia.rdf");

        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

        // Create a new comment individual
        Individual commentIndividual = ontModel.createIndividual("http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Comment_" + System.currentTimeMillis(), ontModel.getOntClass("http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Comment"));

        // Add content property to the comment
        commentIndividual.addProperty(ontModel.getDatatypeProperty("http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#content"), content);

        // Use SPARQL query to retrieve the user individual based on user_name
        String sparqlUserQuery = "SELECT ?user WHERE { ?user <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#username> \"" + userName + "\". }";
        QueryExecution userQueryExecution = QueryExecutionFactory.create(sparqlUserQuery, ontModel);
        ResultSet userResultSet = userQueryExecution.execSelect();

        if (userResultSet.hasNext()) {
            QuerySolution userSolution = userResultSet.nextSolution();
            RDFNode userNode = userSolution.get("user");

            // Link the comment to the user (commentedBy property)
            commentIndividual.addProperty(ontModel.getObjectProperty("http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#commentedBy"), userNode);

            // Link the comment to the commentedOn resource (commentedOn property)
            commentIndividual.addProperty(ontModel.getObjectProperty("http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#commentedOn"), ontModel.getResource(commentedOnURI));

            try (OutputStream outputStream = new FileOutputStream("src/main/java/org/example/socialMedia.rdf")) {
                ontModel.write(outputStream, "RDF/XML-ABBREV");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add the comment.");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Comment added successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found.");
        }
    }




    @DeleteMapping("/deleteComment")
    public ResponseEntity<String> deleteComment(@RequestParam("commentURI") String commentURI) {
        Model model = ModelFactory.createDefaultModel();
        model.read("src/main/java/org/example/socialMedia.rdf");

        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

        Individual commentIndividual = ontModel.getIndividual(commentURI);

        if (commentIndividual != null) {
            commentIndividual.remove();

            try (OutputStream outputStream = new FileOutputStream("src/main/java/org/example/socialMedia.rdf")) {
                ontModel.write(outputStream, "RDF/XML-ABBREV");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the comment.");
            }

            return ResponseEntity.status(HttpStatus.OK).body("Comment deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found.");
        }
    }

    @GetMapping("/commentsByUser")
    public ResponseEntity<List<Map<String, String>>> getCommentsByUser(@RequestParam("userName") String userName) {
        // Load RDF data from a file
        Model model = ModelFactory.createDefaultModel();
        model.read("src/main/java/org/example/socialMedia.rdf");

        // Create an OntModel that performs inference
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

        // Define your SPARQL query to retrieve comments by user
        String sparqlQuery = "SELECT ?comment ?commentContent ?userUsername WHERE {\n" +
                "  ?comment a <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Comment>.\n" +
                "  ?comment <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#content> ?commentContent.\n" +
                "  ?comment <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#commentedBy> ?user.\n" +
                "  ?user <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#username> ?userUsername.\n" +
                "  FILTER (str(?userUsername) = '" + userName + "').\n" +
                "}";

        QueryExecution queryExecution = QueryExecutionFactory.create(QueryFactory.create(sparqlQuery), ontModel);
        ResultSet resultSet = queryExecution.execSelect();

        List<Map<String, String>> resultList = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.nextSolution();
            String commentURI = solution.get("comment") != null ? solution.get("comment").toString() : null; // Extract commentURI
            String commentContent = solution.get("commentContent") != null ? solution.get("commentContent").toString() : null;
            String userUsername = solution.get("userUsername") != null ? solution.get("userUsername").toString() : null;

            Map<String, String> commentMap = new HashMap<>();
            if (commentURI != null) commentMap.put("commentURI", commentURI); // Include commentURI in the response
            if (commentContent != null) commentMap.put("content", commentContent);
            if (userUsername != null) commentMap.put("user_name", userUsername);
            resultList.add(commentMap);
        }

        return ResponseEntity.ok(resultList);
    }







}
