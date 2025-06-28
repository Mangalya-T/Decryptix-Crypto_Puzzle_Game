import java.util.*;

public class Cipher {
    private Map<Character, Character> key = new HashMap<>();
    private Map<Character, Character> inverseKey = new HashMap<>();

    public Cipher() {
        generateKey();
    }

    private void generateKey() {
        List<Character> alphabet = new ArrayList<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            alphabet.add(c);
        }

        List<Character> shuffled = new ArrayList<>(alphabet);
        Collections.shuffle(shuffled);

        for (int i = 0; i < 26; i++) {
            key.put(alphabet.get(i), shuffled.get(i));
            inverseKey.put(shuffled.get(i), alphabet.get(i));
        }
    }

    public String encrypt(String plainText) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : plainText.toUpperCase().toCharArray()) {
            if (Character.isLetter(c)) {
                encrypted.append(key.get(c));
            } else {
                encrypted.append(c);
            }
        }
        return encrypted.toString();
    }

    public Map<Character, Character> getKey() {
        return key;
    }

    public Map<Character, Character> getInverseKey() {
        return inverseKey;
    }
}
