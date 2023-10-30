package org.example.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RdfUtils {
  private static RdfUtils _instance = null;

  private String rdfFile = "src/main/java/org/example/socialMedia2.rdf";

  public static RdfUtils get() {
    if (_instance == null) {
      return new RdfUtils();
    }
    return _instance;
  }

  public void appendContentToRDF(String contentToAdd) {
    try {
      // Read the RDF/XML file
      BufferedReader reader = new BufferedReader(new FileReader(rdfFile));
      String line;
      StringBuilder fileContent = new StringBuilder();

      while ((line = reader.readLine()) != null) {
        fileContent.append(line).append("\n");

        // Look for the "<!-- content here -->" line to append content
        if (line.trim().equals("<!-- content here -->")) {
          fileContent.append(contentToAdd).append("\n");
        }
      }
      reader.close();

      // Save the updated content to the file
      BufferedWriter writer = new BufferedWriter(new FileWriter(rdfFile));
      writer.write(fileContent.toString());
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
