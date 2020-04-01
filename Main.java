package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String text = readFileAsString(args[0]);

        CountTestData countTestData = new CountTestData(text);
        countTestData.count();

        int w = countTestData.getWords().length;
        int s = countTestData.getSentences().length;
        int c = countTestData.getCharacters().length;
        int syl = countTestData.getSyllables();
        int polysyllables = countTestData.getPolysyllables();


        System.out.println("Words: " + w);
        System.out.println("Sentences: " + s);
        System.out.println("Characters: " + c);
        System.out.println("Syllables: " + syl);
        System.out.println("Polysyllables: " + polysyllables);

        ReadabilityIndex index = new ReadabilityIndex(c, w, s, syl, polysyllables);

        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        String input = scanner.next();

        if ("ARI".equals(input)) {
            index.automatedReadabilityIndex();
        } else if ("FK".equals(input)) {
            index.fleschKincaidReadability();
        } else if ("SMOG".equals(input)) {
            index.smogIndex();
        } else if ("CL".equals(input)) {
            index.colemanLiauIndex();
        } else if ("all".equals(input)) {
            System.out.println();
            index.automatedReadabilityIndex();
            index.fleschKincaidReadability();
            index.smogIndex();
            index.colemanLiauIndex();
        }


    }

    public static String readFileAsString(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            return "";
        }
    }
}


class ReadabilityIndex {
    private int c;
    private int w;
    private int s;
    private int syl;
    private int polySyl;
    private double score;

    public ReadabilityIndex(int c, int w, int s, int syl, int polySyl) {
        this.c = c;
        this.w = w;
        this.s = s;
        this.syl = syl;
        this.polySyl = polySyl;
    }


    public void automatedReadabilityIndex() {
        score = 4.71 * (1.0 * this.c / this.w) + 0.5 * (1.0 * this.w / this.s) - 21.43;
        System.out.printf("Automated Readability Index: %.02f (about %d year olds).\n"
                ,getScore(), getAge());

    }

    public void fleschKincaidReadability() {
        score = (0.39 * (1.0 * w / s)) + (11.8 * (1.0 * syl / w)) - 15.59;
        System.out.printf("Flesch–Kincaid readability tests: %.02f (about %d year olds).\n"
                ,getScore(), getAge());
    }

    public void smogIndex() {
        score = 1.043 * (Math.sqrt(polySyl * (1.0 * 30 / s))) + 3.1291;
        System.out.printf("Simple Measure of Gobbledygook: %.02f (about %d year olds).\n"
                ,getScore() ,getAge());
    }

    public void colemanLiauIndex() {
        double l = (1.0 * c / w) * 100;
        double s = (1.0 * this.s / w) * 100;
        score = (0.0588 * l) - (0.296 * s) - 15.8;
        System.out.printf("Coleman–Liau index: %.02f (about %d year olds).\n"
                ,getScore(), getAge());
    }


    public int getAge() {
        int yearMessage;
        if (score <= 2) {
            yearMessage = 6;
        } else if (score <= 3) {
            yearMessage = 7;
        } else if (score <= 4) {
            yearMessage = 9;
        } else if (score <= 5) {
            yearMessage = 10;
        } else if (score <= 6) {
            yearMessage = 11;
        } else if (score <= 7) {
            yearMessage = 12;
        } else if (score <= 8) {
            yearMessage = 13;
        } else if (score <= 9) {
            yearMessage = 14;
        } else if (score <= 10) {
            yearMessage = 15;
        } else if (score <= 11) {
            yearMessage = 16;
        } else if (score <= 12) {
            yearMessage = 17;
        } else if (score <= 13) {
            yearMessage = 18;
        } else if (score <= 14) {
            yearMessage = 24;
        } else if (score <= 15) {
            yearMessage = 24;
        } else {
            yearMessage = 0;
        }
        return yearMessage;
    }

    public double getScore() {
        return Math.floor(score * 100) / 100.0;
    }

}

class CountTestData {
    private static int globalCounter = 0;
    private String text;
    private String[] words;
    private String[] sentences;
    private char[] characters;
    private int syllables;
    private int polysyllables;

    public CountTestData(String text) {
        this.text = text;
    }

    public void count() {
        this.sentences = this.text.split("[!.?]");
        this.words = String.join(" ", this.sentences).split("\\s+");
        this.characters = String.join("", this.text.split("\\s+")).toCharArray();

        for (String word : words) {
            this.syllables = countSyllables(word);
        }

    }

    public String[] getWords() {
        return words;
    }

    public String[] getSentences() {
        return sentences;
    }

    public char[] getCharacters() {
        return characters;
    }

    public int getSyllables() {
        return syllables;
    }

    public int getPolysyllables() {
        return polysyllables;
    }

    public int countSyllables(String word) {

        Pattern pattern = Pattern.compile("(?!e\\b)[aeiouy]{1,}|\\b[0-9qwrtplkjhgfdszxcvbnm]+\\b|\\bthe\\b");
        Matcher matcher = pattern.matcher(word.toLowerCase());

        int count = 0;

        while (matcher.find()) {
            count += 1;
        }

        globalCounter += count;

        if (count > 2) {
            polysyllables++;
        }

        return globalCounter;

    }
}
