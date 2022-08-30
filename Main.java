import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

class Main {
  
  private static String word = "";
  private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static String[] bottomBar = new String[26];
  private static String[][] array = new String[12][5];
  private static int guessNum = 0;
  private static String guess = ""; 
  
  public static void main(String[] args) {
    // create an arrayList of allTheWords
    // Construct things in here
    ArrayList<String> allTheWords = new ArrayList<>();
    allTheWords = importFile("englishwords.txt");
    allTheWords = fiveLetterWords(allTheWords);
    beginWordle(allTheWords);
  }

  public static ArrayList<String> importFile(String fileName) {
    ArrayList<String> words = new ArrayList<>();
    try {
      File myObj = new File(fileName);
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        words.add(data);
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    return words;
  }

  public static ArrayList<String> fiveLetterWords(ArrayList<String> words) {
    // Creates an arrayList containing only 5 letter word
    ArrayList<String> fiveLetterWords = new ArrayList<>();
    for (int i = 0; i < words.size(); i++) {
      if (words.get(i).length() == 5) {
        fiveLetterWords.add(words.get(i));
      }
    }
    return fiveLetterWords;
  }

  public static void beginWordle(ArrayList<String> allTheWords) {
    // Begins the wordle game and collects user input
    Scanner keyboard = new Scanner(System.in);
    System.out.print(
        "******Welcome to Wordle!******\nType in a five letter word in all caps that will be guessed by another person: ");
    word = keyboard.nextLine();
    for (int i = 0; i < 50; i++) {
      System.out.println();
    }
    bottomBarFOne(word); // change the method name but continue the call
  }

  public static void bottomBarFOne(String word) {
    for (int i = 0; i < bottomBar.length; i++) {
      bottomBar[i] = alphabet.substring(i, i + 1);
    }
    prompt();
  }
  // call to the method below prompt();

  public static void prompt() {
    Scanner keyboard = new Scanner(System.in);
    System.out.print(" Enter in your guess (all caps): ");
    guess = keyboard.nextLine(); // String guess
    game();
  }

  public static int game() {
    while (guessNum < 5) {
      for (int col = 0; col < 5; col++) {
        array[guessNum * 2][col] = guess.substring(col, col + 1);
      }
      hints(word, guess, guessNum, array, bottomBar); // keep hints the same
      if (guess.equals(word)) {
        congrats(); 
        return guessNum;
      }
      for (String[] row : array) {
        System.out.println(Arrays.toString(row));
      }
      System.out.println(Arrays.toString(bottomBar));
      Scanner console = new Scanner(System.in);
      System.out.print("Try again: ");
      guess = console.nextLine();
      guessNum++;
      if (guessNum == 6) {
        lose(word);
        return 0;
      }
    }
    System.out.println();
    System.out.println();
    lose(word);
    return 0;
  }

  public static void congrats(){
    System.out.println("Congragulations! You guessed correctly!\nYou took " + (1 + guessNum) + " guesses");
  }
  
  public static void hints(String word, String guess, int guessNum, String[][] array, String[] bottomBar) {
    // Various character functions @ * x inculding the already guessed wordes
    String letter = "";
    for (int i = 0; i < guess.length(); i++) {
      letter = guess.substring(i, i + 1);
      if (word.indexOf(letter) == -1) {
        array[guessNum * 2 + 1][i] = "x";
      } else if (word.indexOf(letter, i) == i) {
        array[guessNum * 2 + 1][i] = "*";
      } else {
        array[guessNum * 2 + 1][i] = "@";
      }
      for (int j = 0; j < bottomBar.length; j++) {
        if (bottomBar[j].equals(letter)) {
          bottomBar[j] = "#";
        }
      }
    }

    for (int i = 0; i < 50; i++) {
      System.out.println();
    }
  }

  public static void lose(String word) {
    System.out.println("\n\nSorry, you lost\n\nBThe correct word is " + word);
  }

}

