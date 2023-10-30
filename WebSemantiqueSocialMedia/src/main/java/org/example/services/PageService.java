package org.example.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.example.models.Page;
import org.example.utils.JenaUtils;
import org.example.utils.RdfUtils;
import org.springframework.stereotype.Service;

@Service
public class PageService {
  String prefix = JenaUtils.getPrefix();

  List<List<String>> fields = new ArrayList<>();

  public PageService() {
    // fill array on creation
    fields.add(new ArrayList<String>() {
      {
        add("pageUrl");
        add("url");
      }
    });
    fields.add(new ArrayList<String>() {
      {
        add("pageName");
        add("name");
      }
    });
    fields.add(new ArrayList<String>() {
      {
        add("pageLikesCount");
        add("likesCount");
      }
    });
    fields.add(new ArrayList<String>() {
      {
        add("pageCategory");
        add("category");
      }
    });
    fields.add(new ArrayList<String>() {
      {
        add("pageId");
        add("id");
      }
    });
  }

  public List<?> getAll() {
    String query = "SELECT ?individual ?pageId ?pageUrl ?pageName ?pageLikesCount ?pageCategory\n" +
        "WHERE {\n" +
        "  ?individual a <http://www.semanticweb.org/ines/ontologies/2023/9/untitled-ontology-2#Page>.\n"
        +
        "  OPTIONAL { ?individual <http://www.semanticweb.org/ines/ontologies/2023/9/untitled-ontology-2#pageId> ?pageId }\n"
        +
        "  OPTIONAL { ?individual <http://www.semanticweb.org/ines/ontologies/2023/9/untitled-ontology-2#pageUrl> ?pageUrl }\n"
        +
        "  OPTIONAL { ?individual <http://www.semanticweb.org/ines/ontologies/2023/9/untitled-ontology-2#pageName> ?pageName }\n"
        +
        "  OPTIONAL { ?individual <http://www.semanticweb.org/ines/ontologies/2023/9/untitled-ontology-2#pageCategory> ?pageCategory }\n"
        +
        "  OPTIONAL { ?individual <http://www.semanticweb.org/ines/ontologies/2023/9/untitled-ontology-2#pageLikesCount> ?pageLikesCount }\n"
        +
        "}";

    return JenaUtils.get().executeSelect(query, fields);
  }

  public List<?> getByCategory(String pageCategory) {
    String query = "SELECT ?individual ?pageId ?pageUrl ?pageName ?pageLikesCount ?pageCategory\n" +
        "WHERE {\n" +
        "  ?individual a <http://www.semanticweb.org/ines/ontologies/2023/9/untitled-ontology-2#Page>.\n"
        +
        "  OPTIONAL { ?individual <http://www.semanticweb.org/ines/ontologies/2023/9/untitled-ontology-2#pageId> ?pageId }\n"
        +
        "  OPTIONAL { ?individual <http://www.semanticweb.org/ines/ontologies/2023/9/untitled-ontology-2#pageUrl> ?pageUrl }\n"
        +
        "  OPTIONAL { ?individual <http://www.semanticweb.org/ines/ontologies/2023/9/untitled-ontology-2#pageName> ?pageName }\n"
        +
        "  OPTIONAL { ?individual <http://www.semanticweb.org/ines/ontologies/2023/9/untitled-ontology-2#pageCategory> ?pageCategory }\n"
        +
        "  OPTIONAL { ?individual <http://www.semanticweb.org/ines/ontologies/2023/9/untitled-ontology-2#pageLikesCount> ?pageLikesCount }\n"
        +
        "  FILTER regex(str(?pageCategory), \"" + pageCategory + "\", \"i\")\n" +
        "}";

    return JenaUtils.get().executeSelect(query, fields);
  }

  public String add(Page page) {
    String pageId = UUID.randomUUID().toString();
    String template = templatePage(pageId, page.url, page.name, page.category, page.likesCount);
    RdfUtils.get().appendContentToRDF(template);
    return "Content added successfully.";
  }

  private String templatePage(String pageId, String pageUrl, String pageName, String pageCategory, int pageLikesCount) {
    String template = "<owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/ines/ontologies/2023/9/untitled-ontology-2#"
        + pageId.substring(0, 4) + "\">\n" +
        "\t<rdf:type rdf:resource=\"http://www.semanticweb.org/ines/ontologies/2023/9/untitled-ontology-2#Page\"/>\n" +
        "\t<untitled-ontology-2:pageId>" + pageId + "</untitled-ontology-2:pageId>\n" +
        "\t<untitled-ontology-2:pageLikesCount>" + pageLikesCount + "</untitled-ontology-2:pageLikesCount>\n" +
        "\t<untitled-ontology-2:pageCategory>" + pageCategory + "</untitled-ontology-2:pageCategory>\n" +
        "\t<untitled-ontology-2:pageName>" + pageName + "</untitled-ontology-2:pageName>\n" +
        "\t<untitled-ontology-2:pageUrl>" + pageUrl + "</untitled-ontology-2:pageUrl>\n" +
        "</owl:NamedIndividual>";

    return template;
  }

}
