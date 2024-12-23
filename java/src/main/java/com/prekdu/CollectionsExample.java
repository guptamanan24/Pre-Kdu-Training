package com.prekdu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public final class CollectionsExample {

  private CollectionsExample() {
    // Prevent instantiation
  }

  /**
   * Main method to execute the collections example.
   *
   * @param args Command-line arguments
   */
  public static void main(final String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      List<String> wordList = new ArrayList<>();
      Set<String> uniqueWords = new HashSet<>();
      Map<String, Integer> wordFrequency = new HashMap<>();

      System.out.println("Enter 10 words:");
      final int totalWords = 10; // Magic number extracted as constant

      for (int i = 0; i < totalWords; i++) {
        String word = scanner.nextLine();
        wordList.add(word);
        uniqueWords.add(word);
        wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
      }

      System.out.println("\nArrayList Content:");
      for (String word : wordList) {
        System.out.println(word);
      }

      System.out.println("\nHashSet Content:");
      for (String word : uniqueWords) {
        System.out.println(word);
      }
      System.out.println("\nHashMap Content:");
      for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
        System.out.println(entry.getKey() + " " + entry.getValue());
      }
    }
  }
}
