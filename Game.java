import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private final Cipher cipher;
    private final String originalMessage;
    private final String encryptedMessage;
    private final Map<Character, Character> playerGuesses = new HashMap<>();
    private int correctGuesses = 0;

    public Game(String message) {
        this.originalMessage = message.toUpperCase();
        this.cipher = new Cipher();
        this.encryptedMessage = cipher.encrypt(originalMessage);
    }

    public void start() {
        System.out.println("Encrypted using SHIFT CIPHER");
        Scanner scanner = new Scanner(System.in);
        System.out.println("🔐 Encrypted Message:");
        System.out.println(encryptedMessage);

        while (true) {
            System.out.println("\n🧠 Your Current Decryption:");
            System.out.println(decryptWithGuesses());
            System.out.printf("✅ Accuracy: %.2f%%\n", calculateAccuracy());

            System.out.print("📝 Guess a mapping (e.g., A=T), or type QUIT: ");
            String input = scanner.nextLine().toUpperCase();

            if (input.equals("QUIT")) {
                System.out.println("👋 Thanks for playing!");
                break;
            }

            if (input.matches("[A-Z]=[A-Z]")) {
                char encryptedChar = input.charAt(0);
                char guess = input.charAt(2);
                playerGuesses.put(encryptedChar, guess);

                // Find all positions where this encryptedChar appears
                boolean allCorrect = true;
                for (int i = 0; i < encryptedMessage.length(); i++) {
                    if (encryptedMessage.charAt(i) == encryptedChar) {
                        if (originalMessage.charAt(i) != guess) {
                            allCorrect = false;
                            break;
                        }
                    }
                }

                if (allCorrect) {
                    correctGuesses++; // optional: counts correct guesses
                } else {
                    System.out.println("❌ Wrong guess! You lost the game.");
                    System.out.printf("🎯 Final Accuracy: %.2f%%\n", calculateAccuracy());
                    break;
                }
            } else {
                System.out.println("⚠️ Invalid format. Use A=T or type QUIT.");
            }

            if (checkWin()) {
                System.out.println("\n🎉 Congratulations! You decrypted the message:");
                System.out.println(originalMessage);
                declareWinner();
                break;
            }
        }
    }

    private String decryptWithGuesses() {
        StringBuilder result = new StringBuilder();
        for (char c : encryptedMessage.toCharArray()) {
            if (Character.isLetter(c)) {
                result.append(playerGuesses.getOrDefault(c, '_'));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    private boolean checkWin() {
        return decryptWithGuesses().equals(originalMessage);
    }

    private void declareWinner() {
        System.out.printf("🏆 You made %d correct guesses!\n", correctGuesses);
        System.out.printf("🎯 Final Accuracy: %.2f%%\n", calculateAccuracy());
    }

    private double calculateAccuracy() {
        String currentDecryption = decryptWithGuesses();
        int totalLetters = 0;
        int correct = 0;

        for (int i = 0; i < originalMessage.length(); i++) {
            char originalChar = originalMessage.charAt(i);
            char guessChar = currentDecryption.charAt(i);
            if (Character.isLetter(originalChar)) {
                totalLetters++;
                if (originalChar == guessChar) {
                    correct++;
                }
            }
        }

        return totalLetters == 0 ? 0.0 : (correct * 100.0) / totalLetters;
    }
}
