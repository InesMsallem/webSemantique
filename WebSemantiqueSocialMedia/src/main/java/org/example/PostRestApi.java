package org.example;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
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
@RequestMapping("/posts/")
public class PostRestApi {


    //GET
    @GetMapping("/all")
    public ResponseEntity<List<Map<String, String>>> getPostsData() {
        // Load RDF data from a file
        Model model = ModelFactory.createDefaultModel();
        model.read("src/main/java/org/example/socialMedia.rdf");

        // Create an OntModel that performs inference
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

        // Define your SPARQL query
        String sparqlQuery = "SELECT ?post ?commentContent ?postContent ?userUsername\n" +
                "WHERE {\n" + "  ?post a <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Post>.\n" +
                "  ?post <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#content> ?postContent.\n" +
                "  OPTIONAL { ?post <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#hasComment> ?comment.\n" +
                "    ?comment <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#content> ?commentContent }.\n" +
                "  OPTIONAL { ?post <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#postedBy> ?user.\n" +
                "    ?user <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#username> ?userUsername }.\n" + "}";

        QueryExecution queryExecution = QueryExecutionFactory.create(QueryFactory.create(sparqlQuery), ontModel);
        ResultSet resultSet = queryExecution.execSelect();

        List<Map<String, String>> resultList = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.nextSolution();
            String commentContent = solution.get("commentContent") != null ? solution.get("commentContent").toString() : null;
            String postContent = solution.get("postContent") != null ? solution.get("postContent").toString() : null;
            String userUsername = solution.get("userUsername") != null ? solution.get("userUsername").toString() : null;

            Map<String, String> postMap = new HashMap<>();
            if (commentContent != null) postMap.put("comment", commentContent);
            if (postContent != null) postMap.put("post", postContent);
            if (userUsername != null) postMap.put("user", userUsername);
            resultList.add(postMap);
        }

        return ResponseEntity.ok(resultList);
    }

    // only videos
    @GetMapping("/videos")
    public ResponseEntity<List<Map<String, String>>> getVideosData() {
        // Load RDF data from a file
        Model model = ModelFactory.createDefaultModel();
        model.read("src/main/java/org/example/socialMedia.rdf");

        // Create an OntModel that performs inference
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

        // Define your SPARQL query to get only Video posts
        String sparqlQuery = "SELECT ?video ?content ?userUsername\n" +
                "WHERE {\n" + "  ?video a <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Video>.\n" +
                "  ?video <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#content> ?content.\n" +
                "  OPTIONAL { ?video <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#postedBy> ?user.\n" +
                "    ?user <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#username> ?userUsername }.\n" + "}";

        QueryExecution queryExecution = QueryExecutionFactory.create(QueryFactory.create(sparqlQuery), ontModel);
        ResultSet resultSet = queryExecution.execSelect();

        List<Map<String, String>> resultList = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.nextSolution();
            String content = solution.get("content") != null ? solution.get("content").toString() : null;
            String userUsername = solution.get("userUsername") != null ? solution.get("userUsername").toString() : null;

            Map<String, String> postMap = new HashMap<>();
            if (content != null) postMap.put("content", content);
            if (userUsername != null) postMap.put("user", userUsername);
            resultList.add(postMap);
        }

        return ResponseEntity.ok(resultList);
    }

    //only pictures
    @GetMapping("/pictures")
    public ResponseEntity<List<Map<String, String>>> getPicturesData() {
        // Load RDF data from a file
        Model model = ModelFactory.createDefaultModel();
        model.read("src/main/java/org/example/socialMedia.rdf");

        // Create an OntModel that performs inference
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

        // Define your SPARQL query to get only Picture posts
        String sparqlQuery = "SELECT ?picture ?content ?userUsername\n" +
                "WHERE {\n" + "  ?picture a <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Picture>.\n" +
                "  ?picture <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#content> ?content.\n" +
                "  OPTIONAL { ?picture <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#postedBy> ?user.\n" +
                "    ?user <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#username> ?userUsername }.\n" + "}";

        QueryExecution queryExecution = QueryExecutionFactory.create(QueryFactory.create(sparqlQuery), ontModel);
        ResultSet resultSet = queryExecution.execSelect();

        List<Map<String, String>> resultList = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.nextSolution();
            String content = solution.get("content") != null ? solution.get("content").toString() : null;
            String userUsername = solution.get("userUsername") != null ? solution.get("userUsername").toString() : null;

            Map<String, String> postMap = new HashMap<>();
            if (content != null) postMap.put("content", content);
            if (userUsername != null) postMap.put("user", userUsername);
            resultList.add(postMap);
        }

        return ResponseEntity.ok(resultList);
    }

    //articles
    @GetMapping("/articles")
    public ResponseEntity<List<Map<String, String>>> getArticlesData() {
        // Load RDF data from a file
        Model model = ModelFactory.createDefaultModel();
        model.read("src/main/java/org/example/socialMedia.rdf");

        // Create an OntModel that performs inference
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

        // Define your SPARQL query to get only Article posts
        String sparqlQuery = "SELECT ?article ?content ?userUsername\n" + "WHERE {\n" +
                "  ?article a <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Article>.\n" +
                "  ?article <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#content> ?content.\n" +
                "  OPTIONAL { ?article <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#postedBy> ?user.\n" +
                "    ?user <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#username> ?userUsername }.\n" + "}";

        QueryExecution queryExecution = QueryExecutionFactory.create(QueryFactory.create(sparqlQuery), ontModel);
        ResultSet resultSet = queryExecution.execSelect();

        List<Map<String, String>> resultList = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.nextSolution();
            String content = solution.get("content") != null ? solution.get("content").toString() : null;
            String userUsername = solution.get("userUsername") != null ? solution.get("userUsername").toString() : null;

            Map<String, String> postMap = new HashMap<>();
            if (content != null) postMap.put("content", content);
            if (userUsername != null) postMap.put("user", userUsername);
            resultList.add(postMap);
        }

        return ResponseEntity.ok(resultList);
    }

    //ADD
    @PostMapping("/addPost")
    public ResponseEntity<String> addPost(@RequestParam("content") String content) {
        // Load RDF data from a file
        Model model = ModelFactory.createDefaultModel();
        model.read("src/main/java/org/example/socialMedia.rdf");

        // Create an OntModel that performs inference
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

        // Create a new individual representing the post
        Individual postIndividual = ontModel.createIndividual("http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Post_" + System.currentTimeMillis(), ontModel.getOntClass("http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Post"));

        // Set the content of the post
        postIndividual.addProperty(ontModel.getDatatypeProperty("http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#content"), content);


        // Save the updated RDF data to your file or database
        try (OutputStream outputStream = new FileOutputStream("src/main/java/org/example/socialMedia.rdf")) {
            ontModel.write(outputStream, "RDF/XML-ABBREV");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add the post.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Post added successfully.");
    }

    //DELETE
    @DeleteMapping("/deletePost")
    public ResponseEntity<String> deletePost(@RequestParam("postURI") String postURI) {
        // Load RDF data from a file
        Model model = ModelFactory.createDefaultModel();
        model.read("src/main/java/org/example/socialMedia.rdf");

        // Create an OntModel that performs inference
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

        // Find the post individual based on the provided URI
        Individual postIndividual = ontModel.getIndividual(postURI);

        if (postIndividual != null) {
            // Delete the post individual
            postIndividual.remove();

            // Save the updated RDF data to your file or database
            try (OutputStream outputStream = new FileOutputStream("src/main/java/org/example/socialMedia.rdf")) {
                ontModel.write(outputStream, "RDF/XML-ABBREV");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the post.");
            }

            return ResponseEntity.status(HttpStatus.OK).body("Post deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
        }
    }


    //UPDATE
    @PutMapping("/updatePost")
    public ResponseEntity<String> updatePost(@RequestParam("postURI") String postURI, @RequestParam("newContent") String newContent) {
        // Load RDF data from a file
        Model model = ModelFactory.createDefaultModel();
        model.read("src/main/java/org/example/socialMedia.rdf");

        // Create an OntModel that performs inference
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

        // Find the post individual based on the provided URI
        Individual postIndividual = ontModel.getIndividual(postURI);

        if (postIndividual != null) {
            // Update the content of the post
            postIndividual.setPropertyValue(ontModel.getDatatypeProperty("http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#content"), ontModel.createTypedLiteral(newContent));

            // Save the updated RDF data to your file or database
            try (OutputStream outputStream = new FileOutputStream("src/main/java/org/example/socialMedia.rdf")) {
                ontModel.write(outputStream, "RDF/XML-ABBREV");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update the post.");
            }

            return ResponseEntity.status(HttpStatus.OK).body("Post updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
        }
    }

}




