package org.example;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@CrossOrigin("*")

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @RestController
    @RequestMapping("/api/")
    class NetworkController {

        @GetMapping("/users")
        public ResponseEntity<List<Map<String, String>>> getUserData() {
            // Load RDF data from a file
            Model model = ModelFactory.createDefaultModel();
            model.read("src/main/java/org/example/socialMedia.rdf");
            // Create an OntModel that performs inference
            OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);
            String sparqlQuery =
                    "SELECT ?individual ?bio ?email ?username\n" +
                            "WHERE {\n" +
                            "  ?individual a ?type.\n" +
                            "  FILTER (?type = <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Member> || ?type = <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Administrator>).\n" +
                            "  OPTIONAL { ?individual <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#bio> ?bio }\n" +
                            "  OPTIONAL { ?individual <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#email> ?email }\n" +
                            "  OPTIONAL { ?individual <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#username> ?username }\n" +
                            "}";

            QueryExecution queryExecution = QueryExecutionFactory.create(QueryFactory.create(sparqlQuery), ontModel);
            ResultSet resultSet = queryExecution.execSelect();

            List<Map<String, String>> resultList = new ArrayList<>();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                String bio = solution.get("bio") != null ? solution.get("bio").toString() : null;
                String email = solution.get("email") != null ? solution.get("email").toString() : null;
                String username = solution.get("username") != null ? solution.get("username").toString() : null;

                if (bio != null || email != null || username != null) {
                    Map<String, String> userMap = new HashMap<>();
                    if (bio != null) userMap.put("bio", bio);
                    if (email != null) userMap.put("email", email);
                    if (username != null) userMap.put("username", username);
                    resultList.add(userMap);
                }
            }
            return ResponseEntity.ok(resultList);
        }

        @GetMapping("/events")
        public ResponseEntity<List<Map<String, String>>> getEventData() {
            // Load RDF data from a file
            Model model = ModelFactory.createDefaultModel();
            model.read("src/main/java/org/example/socialMedia.rdf");
            // Create an OntModel that performs inference
            OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);
            String sparqlQuery =
                    "SELECT ?individual ?eventName ?eventLocation ?eventDate\n" +
                            "WHERE {\n" +
                            "  ?individual a <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Event>.\n" +
                            "  OPTIONAL { ?individual <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#eventName> ?eventName }\n" +
                            "  OPTIONAL { ?individual <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#eventLocation> ?eventLocation }\n" +
                            "  OPTIONAL { ?individual <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#eventDate> ?eventDate }\n" +
                            "}";

            QueryExecution queryExecution = QueryExecutionFactory.create(QueryFactory.create(sparqlQuery), ontModel);
            ResultSet resultSet = queryExecution.execSelect();

            List<Map<String, String>> resultList = new ArrayList<>();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                String eventName = solution.get("eventName") != null ? solution.get("eventName").toString() : null;
                String eventLocation = solution.get("eventLocation") != null ? solution.get("eventLocation").toString() : null;
                String eventDate = solution.get("eventDate") != null ? solution.get("eventDate").toString() : null;

                if (eventName != null || eventLocation != null || eventDate != null) {
                    Map<String, String> eventMap = new HashMap<>();
                    if (eventName != null) eventMap.put("eventName", eventName);
                    if (eventLocation != null) eventMap.put("eventLocation", eventLocation);
                    if (eventDate != null) eventMap.put("eventDate", eventDate);
                    resultList.add(eventMap);
                }
            }
            return ResponseEntity.ok(resultList);
        }


        @GetMapping("/pages")
        public ResponseEntity<List<Map<String, String>>> getPageData() {
            // Load RDF data from a file
            Model model = ModelFactory.createDefaultModel();
            model.read("src/main/java/org/example/socialMedia.rdf");
            // Create an OntModel that performs inference
            OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);
            String sparqlQuery =
                    "SELECT ?individual ?pageUrl ?name ?likesCount\n" +
                            "WHERE {\n" +
                            "  ?individual a <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Page>.\n" +
                            "  OPTIONAL { ?individual <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#pageUrl> ?pageUrl }\n" +
                            "  OPTIONAL { ?individual <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#name> ?name }\n" +
                            "  OPTIONAL { ?individual <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#likesCount> ?likesCount }\n" +
                            "}";

            QueryExecution queryExecution = QueryExecutionFactory.create(QueryFactory.create(sparqlQuery), ontModel);
            ResultSet resultSet = queryExecution.execSelect();

            List<Map<String, String>> resultList = new ArrayList<>();
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                String pageUrl = solution.get("pageUrl") != null ? solution.get("pageUrl").toString() : null;
                String name = solution.get("name") != null ? solution.get("name").toString() : null;
                String likesCount = solution.get("likesCount") != null ? solution.get("likesCount").toString() : null;

                if (pageUrl != null || name != null || likesCount != null) {
                    Map<String, String> pageMap = new HashMap<>();
                    if (pageUrl != null) pageMap.put("pageUrl", pageUrl);
                    if (name != null) pageMap.put("name", name);
                    if (likesCount != null) pageMap.put("likesCount", likesCount);
                    resultList.add(pageMap);
                }
            }
            return ResponseEntity.ok(resultList);
        }


    }}
