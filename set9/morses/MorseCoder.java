package programming.set9.morses;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class MorseCoder {

	private static final String[] ALPHA = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
			"p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			" " };
	private static final String[] DOTS = { ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-",
			".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..",
			"-----", ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.", "|" };

	/**
	 * Returns a new string which is the morse code version of the input string.
	 * Each word is on a separate line. The morse representation of each
	 * character of the input string is separated from the next by a space
	 * character in the output string.
	 * 
	 * @param input
	 *            the input string.
	 * @return the morse code version of the input string, ignoring all
	 *         characters in the input string that cannot be represented in
	 *         international morse code.
	 */
	public static String encode(String input) {

		String[] words = input.split("[ \\n]+");
		ArrayList<byte[]> bytes = new ArrayList<byte[]>();

		for (int i = 0; i < words.length; i++) {
			bytes.add(words[i].getBytes(Charset.forName("US-ASCII")));
		}

		String result = "";
		for (byte[] asciiWord : bytes) {
			for (int asciiLetter : asciiWord) {
				int offset = 0;
				if (asciiLetter >= 48 && asciiLetter <= 57) {
					offset = 22;
				}
				else if (asciiLetter >= 65 && asciiLetter <= 90) {
					offset = 65;
				}
				else if (asciiLetter >= 97 && asciiLetter <= 122) {
					offset = 97;
				}
				if (offset != 0) {
					result += DOTS[asciiLetter - offset] + " ";
				}
			}
			result += "\n";
		}

		return result;
	}

	/**
	 * Returns a new string which is the natural-language version of the input
	 * string, which is assumed to be in morse code format as produced by the
	 * encode method.
	 * 
	 * @param input
	 *            morse code input string.
	 * @return natural language version or {@code null} if the input string
	 *         could not be properly parsed.
	 */
	public static String decode(String input) {

		String[] wordsMorse = input.split("\\n");
		ArrayList<String[]> charsMorse = new ArrayList<String[]>();

		for (int i = 0; i < wordsMorse.length; i++) {
			charsMorse.add(wordsMorse[i].split(" +"));
		}

		String result = "";
		for (String[] wordMorse : charsMorse) {
			for (String charMorse : wordMorse) {
				for (int i = 0; i < DOTS.length - 1; i++) {
					if (charMorse.equals(DOTS[i])) {
						result += ALPHA[i];
						break;
					}
				}
			}
			result += " ";
		}
		return result;
	}
}
