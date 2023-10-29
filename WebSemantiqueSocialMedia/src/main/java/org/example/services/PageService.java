package org.example.services;

import java.util.ArrayList;
import java.util.List;

import org.example.utils.JenaUtils;
import org.springframework.stereotype.Service;

@Service
public class PageService {
  String prefix = JenaUtils.getPrefix();

  public List<?> getAll() {
    String query = "SELECT ?individual ?pageUrl ?name ?likesCount\n" +
        "WHERE {\n" +
        "  ?individual a <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Page>.\n" +
        "  OPTIONAL { ?individual <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#pageUrl> ?pageUrl }\n"
        +
        "  OPTIONAL { ?individual <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#name> ?name }\n"
        +
        "  OPTIONAL { ?individual <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#likesCount> ?likesCount }\n"
        +
        "}";

    List<List<String>> fields = new ArrayList<>();
    // fill array on creation
    fields.add(new ArrayList<String>() {
      {
        add("pageUrl");
        add("url");
      }
    });
    fields.add(new ArrayList<String>() {
      {
        add("name");
        add("name");
      }
    });
    fields.add(new ArrayList<String>() {
      {
        add("likesCount");
        add("likesCount");
      }
    });

    return JenaUtils.get().executeSelect(query, fields);
  }

  public String add(String name, String pageUrl, String likesCount) {
    String query = "PREFIX untitled-ontology-2: <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#>\n"
        + "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
        + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n\n" +
        "INSERT DATA { \n" +
        prefix + ":newPage a " + prefix + ":Page ; \n" +
        prefix + ":likesCount \"" + likesCount + "\"^^xsd:decimal ;\n" +
        prefix + ":name \"" + name + "\" ;\n" +
        prefix + ":pageUrl \"" + pageUrl + "\" .\n" +
        "}";

    return JenaUtils.get().executeInsert(query);
  }
}
