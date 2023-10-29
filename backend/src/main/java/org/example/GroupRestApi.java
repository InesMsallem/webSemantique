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
@RequestMapping("/groups/")
public class GroupRestApi {

    @GetMapping("/all")
    public ResponseEntity<List<Map<String, String>>> getAllGroups(@RequestParam(value = "groupName", required = false) String groupNameFilter) {
        // Load RDF data from a file
        Model model = ModelFactory.createDefaultModel();
        model.read("src/main/java/org/example/socialMedia.rdf");

        // Create an OntModel that performs inference
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

        // Define your SPARQL query to retrieve all groups and their subclass information
        String sparqlQuery = "PREFIX ex: <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#> " +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
                "SELECT DISTINCT ?group ?groupName ?description ?groupType " +
                "WHERE { " +
                "  ?group a ?groupType; " +
                "          ex:name ?groupName; " +
                "          ex:description ?description. " +
                "  FILTER (?groupType = ex:privateGroup || ?groupType = ex:publicGroup)" +
                "  OPTIONAL { ?group a ?subClass. } " +
                (groupNameFilter != null ? "FILTER (str(?groupName) = '" + groupNameFilter + "')." : "") +
                "}";

        QueryExecution queryExecution = QueryExecutionFactory.create(QueryFactory.create(sparqlQuery), ontModel);
        ResultSet resultSet = queryExecution.execSelect();

        List<Map<String, String>> resultList = new ArrayList<>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.nextSolution();
            String groupName = solution.get("groupName") != null ? solution.get("groupName").toString() : null;
            String description = solution.get("description") != null ? solution.get("description").toString() : null;
            String groupType = solution.get("groupType") != null ? solution.get("groupType").toString() : null;

            Map<String, String> groupMap = new HashMap<>();
            if (groupName != null) groupMap.put("name", groupName);
            if (description != null) groupMap.put("description", description);
            if (groupType != null) groupMap.put("type", groupType);
            resultList.add(groupMap);
        }

        return ResponseEntity.ok(resultList);
    }


    @PostMapping("/addGroup")
    public ResponseEntity<String> addGroup(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("groupType") String groupType) {
        // Load RDF data from a file
        Model model = ModelFactory.createDefaultModel();
        model.read("src/main/java/org/example/socialMedia.rdf");

        // Create an OntModel that performs inference
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

        try {
            String groupIndividualURI = "http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Group_" + System.currentTimeMillis();

            // Determine the class of the group based on the groupType
            String groupClassURI = "";
            if ("public".equalsIgnoreCase(groupType)) {
                groupClassURI = "http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#publicGroup";
            } else if ("private".equalsIgnoreCase(groupType)) {
                groupClassURI = "http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#privateGroup";
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid group type.");
            }

            // Create a new individual representing the group
            Individual groupIndividual = ontModel.createIndividual(groupIndividualURI, ontModel.getOntClass(groupClassURI));

            // Set the name and description of the group
            groupIndividual.addProperty(ontModel.getDatatypeProperty("http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#name"), name);
            groupIndividual.addProperty(ontModel.getDatatypeProperty("http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#description"), description);

            // Save the updated RDF data to your file or database
            try (OutputStream outputStream = new FileOutputStream("src/main/java/org/example/socialMedia.rdf")) {
                ontModel.write(outputStream, "RDF/XML-ABBREV");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Group added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add the group.");
        }
    }


    @DeleteMapping("/deleteGroup")
    public ResponseEntity<String> deleteGroup(@RequestParam("groupURI") String groupURI) {
        // Load RDF data from a file
        Model model = ModelFactory.createDefaultModel();
        model.read("src/main/java/org/example/socialMedia.rdf");

        // Create an OntModel that performs inference
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

        // Find the group individual based on the provided URI
        Individual groupIndividual = ontModel.getIndividual(groupURI);

        if (groupIndividual != null) {
            // Delete the group individual
            groupIndividual.remove();

            // Save the updated RDF data to your file or database
            try (OutputStream outputStream = new FileOutputStream("src/main/java/org/example/socialMedia.rdf")) {
                ontModel.write(outputStream, "RDF/XML-ABBREV");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the group.");
            }

            return ResponseEntity.status(HttpStatus.OK).body("Group deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Group not found.");
        }
    }


    @PutMapping("/updateGroup")
    public ResponseEntity<String> updateGroup(@RequestParam("groupURI") String groupURI, @RequestParam("newDescription") String newDescription) {
        // Load RDF data from a file
        Model model = ModelFactory.createDefaultModel();
        model.read("src/main/java/org/example/socialMedia.rdf");

        // Create an OntModel that performs inference
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

        // Find the group individual based on the provided URI
        Individual groupIndividual = ontModel.getIndividual(groupURI);

        if (groupIndividual != null) {
            // Update the description of the group
            groupIndividual.setPropertyValue(ontModel.getDatatypeProperty("http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#description"), ontModel.createTypedLiteral(newDescription));

            // Save the updated RDF data to your file or database
            try (OutputStream outputStream = new FileOutputStream("src/main/java/org/example/socialMedia.rdf")) {
                ontModel.write(outputStream, "RDF/XML-ABBREV");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update the group.");
            }

            return ResponseEntity.status(HttpStatus.OK).body("Group updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Group not found.");
        }
    }


}
