package org.example.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public class JenaUtils {
  private static JenaUtils _instance = null;
  public Model model;
  public OntModel ontModel;
  private QueryExecution queryExec;

  public JenaUtils() {
    model = ModelFactory.createDefaultModel();
    model.read("src/main/java/org/example/socialMedia.rdf");
    ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF, model);

  }

  public static JenaUtils get() {
    if (_instance == null) {
      _instance = new JenaUtils();
    }
    return _instance;
  }

  public List<Map<String, String>> executeSelect(String selectQuery, List<List<String>> fields) {
    queryExec = QueryExecutionFactory.create(selectQuery, ontModel);
    ResultSet results = queryExec.execSelect();
    List<Map<String, String>> resultList = new ArrayList<>();
    while (results.hasNext()) {
      Map<String, String> resultMap = new HashMap<>();
      QuerySolution solution = results.nextSolution();
      for (List<String> field : fields) {
        // check for null
        if (solution.get(field.get(0)) != null) {
          resultMap.put(field.get(1), solution.get(field.get(0)).toString());
        }
      }
      resultList.add(resultMap);
    }
    queryExec.close();
    return resultList;
  }
}
