package org.example.services;

import java.util.ArrayList;
import java.util.List;

import org.example.utils.JenaUtils;
import org.springframework.stereotype.Service;

@Service
public class PageService {
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

}
