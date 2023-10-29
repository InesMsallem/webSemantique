package org.example.services;

import java.util.ArrayList;
import java.util.List;

import org.example.utils.JenaUtils;
import org.springframework.stereotype.Service;

@Service
public class EventService {
  public List<?> getAll() {
    String query = "SELECT ?individual ?eventName ?eventLocation ?eventDate\n" +
        "WHERE {\n" +
        "  ?individual a <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Event>.\n" +
        "  OPTIONAL { ?individual <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#eventName> ?eventName }\n"
        +
        "  OPTIONAL { ?individual <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#eventLocation> ?eventLocation }\n"
        +
        "  OPTIONAL { ?individual <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#eventDate> ?eventDate }\n"
        +
        "}";

    List<List<String>> fields = new ArrayList<>();
    // fill array on creation
    fields.add(new ArrayList<String>() {
      {
        add("eventName");
        add("eventName");
      }
    });
    fields.add(new ArrayList<String>() {
      {
        add("eventLocation");
        add("eventLocation");
      }
    });
    fields.add(new ArrayList<String>() {
      {
        add("eventDate");
        add("eventDate");
      }
    });

    return JenaUtils.get().executeSelect(query, fields);
  }
}
