package org.example.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.example.utils.JenaUtils;
import org.springframework.stereotype.Service;

@Service
public class EventService {
  List<List<String>> fields = new ArrayList<>();
  public EventService(){
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

  }


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

    return JenaUtils.get().executeSelect(query, fields);
  }
  public List <?> getEventByDate(String eventDate) {
    String query = "SELECT ?individual ?eventName ?eventLocation ?eventDate\n" +
            "WHERE {\n" +
            "  ?individual a <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#Event>.\n" +
            "  OPTIONAL { ?individual <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#eventName> ?eventName }\n"
            +
            "  OPTIONAL { ?individual <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#eventLocation> ?eventLocation }\n"
            +
            "  OPTIONAL { ?individual <http://www.semanticweb.org/inès/ontologies/2023/9/untitled-ontology-2#eventDate> ?eventDate }\n"
            +
            "  FILTER regex(str(?eventDate), \"" + eventDate + "\", \"i\")\n"
            +

            "}";
    return JenaUtils.get().executeSelect(query, fields);
  }

}
