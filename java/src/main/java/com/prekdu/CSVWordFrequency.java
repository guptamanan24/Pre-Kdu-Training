package com.prekdu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

/** Reads a CSV file and prints the top 3 most repeated words. */
public final class CSVWordFrequency {

  private CSVWordFrequency() {
    // Prevent instantiation
  }

  /**
   * Main method to execute the CSV word frequency example.
   *
   * @param args Command-line arguments
   */
  public static void main(final String[] args) {
    String path = "java/src/main/resources/input.csv";
    Map<String, Integer> freq = new HashMap<>();

    try (Scanner sc = new Scanner(new File(path))) {
      while (sc.hasNextLine()) {
        String[] words = sc.nextLine().split("[,\\s]+");
        for (String w : words) {
          w = w.toLowerCase(Locale.ENGLISH);
          freq.put(w, freq.getOrDefault(w, 0) + 1);
        }
      }

      List<Map.Entry<String, Integer>> sw = new ArrayList<>(freq.entrySet());
      sw.sort((a, b) -> b.getValue().compareTo(a.getValue()));

      final int top = 3; // Magic number extracted as constant
      System.out.println("Top 3 Repeated Words:");
      for (int i = 0; i < Math.min(top, sw.size()); i++) {
        Map.Entry<String, Integer> entry = sw.get(i);
        System.out.println(entry.getKey() + ": " + entry.getValue());
      }
    } catch (FileNotFoundException e) {
      System.err.println("Error: File not found.");
      e.printStackTrace();
    }
  }
}
