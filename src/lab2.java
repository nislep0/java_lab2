import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public class lab2 {

    static void main() {
        try {
            String text = "Lorem ipsum felis sit amet, placerat adipiscing leo. "
                    + "Duis posuere lacinia arcu, vitae convallis eros tincidunt a. "
                    + "Vestibulum in lacinia metus. "
                    + "Integer eleifend enim eget mollis posuere. Aenean vitae ligula ac quam molestie semper viverra id odio. "
                    + "In orci diam, convallis vitae egestas ut, mattis eget felis. "
                    + "Mauris luctus, eros vel ultricies ornare, urna leo consequat ligula, sit amet placerat nibh arcu id felis. "
                    + "Sed ac metus non enim tincidunt blandit.";

            Set<String> result = findUniqueWordsInFirstSentence(text);

            System.out.println("Text:\n" + text);
            System.out.println("\nResult:");

            if (result.isEmpty()) {
                System.out.println("No unique words found in the first sentence.");
            } else {
                System.out.println("Unique words from the first sentence:");
                for (String word : result) {
                    System.out.println(word);
                }
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Input error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Set<String> findUniqueWordsInFirstSentence(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Text is null.");
        }

        String trimmed = text.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Text is empty.");
        }

        String[] sentences = splitIntoSentences(trimmed);
        if (sentences.length == 0) {
            throw new IllegalArgumentException("No sentences found in the text.");
        }

        Set<String> firstSentenceWords = extractWords(sentences[0]);
        if (firstSentenceWords.isEmpty()) {
            throw new IllegalArgumentException("First sentence has no words.");
        }

        Set<String> otherWords = new LinkedHashSet<>();
        for (int i = 1; i < sentences.length; i++) {
            otherWords.addAll(extractWords(sentences[i]));
        }

        Set<String> uniqueWords = new LinkedHashSet<>();
        for (String word : firstSentenceWords) {
            if (!otherWords.contains(word)) {
                uniqueWords.add(word);
            }
        }

        return uniqueWords;
    }

    private static String[] splitIntoSentences(String text) {
        String[] raw = text.split("[.!?]+");
        int count = 0;

        for (String s : raw) {
            if (!s.trim().isEmpty()) {
                count++;
            }
        }

        String[] sentences = new String[count];
        int index = 0;

        for (String s : raw) {
            String trimmed = s.trim();
            if (!trimmed.isEmpty()) {
                sentences[index++] = trimmed;
            }
        }
        return sentences;
    }

    private static Set<String> extractWords(String sentence) {
        Set<String> words = new LinkedHashSet<>();
        if (sentence == null) {
            return words;
        }

        String normalized = sentence
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^\\p{L}\\p{N}']+", " ")
                .trim();

        if (normalized.isEmpty()) {
            return words;
        }

        String[] parts = normalized.split("\\s+");
        for (String part : parts) {
            if (!part.isEmpty()) {
                words.add(part);
            }
        }
        return words;
    }
}