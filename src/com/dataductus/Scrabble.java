package com.dataductus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Scrabble {
	
	public String start() {

        List<Word> listOfShakespeareWords = new ArrayList<>();

        int[] letterScores = {
             // a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p,  q, r, s, t, u, v, w, x, y, z?
                1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10
        };

        char[] alfabet = {
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        };

        
        //Hashmap to store value of each letter.
        HashMap<Character, Integer> hm = new HashMap<>();
        for (int i = 0; i < alfabet.length; i++) {
            hm.put(alfabet[i], letterScores[i]);
        }
        System.out.println(hm);

        //reading files to Streams.
        String dictionary = "ospd.txt";
        String shakespeareFilePath = "words.shakespeare2.txt";
        try {
            Stream<String> streamOfDictionary = Files.lines(Paths.get(dictionary));
            List<String> listOfDictionary = streamOfDictionary.collect(Collectors.toList());
            Stream<String> streamOfShakespeareWords = Files.lines(Paths.get(shakespeareFilePath));
            System.out.println("Files successfully read");
            //Adding Shakespeare words to Object list to calculate validity and points per word.
            streamOfShakespeareWords.forEach(word -> listOfShakespeareWords.add(new Word(calculatePoints(word, hm), isValid(word, listOfDictionary), word)));
            streamOfDictionary.close();
            streamOfShakespeareWords.close();   

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //Final print of words for highscore
        
        System.out.println("Highscore - allowed words:");
        List<Word> result = listOfShakespeareWords.stream()
        						.filter(w -> !false == (w.getValid()))
        						.sorted(Comparator.comparingInt(Word::getPoints).reversed())
        						.collect(Collectors.toList());
        
        result.forEach(System.out::println);
        
        System.out.println("\nHighscore - all words:");
        result = listOfShakespeareWords.stream()
        						.sorted(Comparator.comparingInt(Word::getPoints).reversed())
        						.collect(Collectors.toList());
        
        result.forEach(System.out::println);
        return "Successful";
    }			


    

    private boolean isValid(String word, List<String> streamOfDictionary) {

        boolean match = streamOfDictionary.stream().anyMatch(w -> streamOfDictionary.contains(word));
        System.out.println(match + "\n");
        return match;
    }

    private int calculatePoints(String word, HashMap<Character, Integer> hm) {

        int[] scrabbleLetterDistribution = {
             // a, b, c, d,  e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
                9, 2, 2, 1, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1
        };
        
        int points = 0;
        System.out.println("Looping through word: " + word);
        word = word.toLowerCase();
        
        for (int i = 0; i < word.length(); i++) {
        	System.out.println("\nCurrent letter: '" + word.charAt(i) + "'");
        	char currentLetter = word.charAt(i);
        	int letterPosition = 0;
        	                
                //Update available letters
                for (Entry<Character, Integer> entry : hm.entrySet()) {
                	
                	letterPosition++;
                	if(entry.getKey().equals(currentLetter)) {
                	letterPosition--;
        		    System.out.println("Letter '" + entry.getKey() + "' is worth " + entry.getValue() + " points");
                	//Check if there are letters left for current letter.
                    if (scrabbleLetterDistribution[letterPosition] != 0) {
                    System.out.println("Letter position in alfabet: " + (letterPosition + 1));
                    System.out.println("Available letters: " + (scrabbleLetterDistribution[letterPosition]));
                	scrabbleLetterDistribution[letterPosition] -= 1;
                	//Update total points for current word
                    points += (int) hm.get(word.charAt(i));
                	break;
                	}
                    else {
                    	System.out.println("Available letters: " + (scrabbleLetterDistribution[letterPosition]));
                    	System.out.println("Not enough of letter '" + currentLetter + "'.");
                    }
        		}
            }
        }
        return points;
    }

}
