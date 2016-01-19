package programming.set9.morses;

import acm.program.ConsoleProgram;

public class TestClass extends ConsoleProgram {

	public void run() {
		setSize(500, 500);
		String str = "Unsere Mitglieder aus allen Altersklassen sind in zahlreichen Sparten sportlich aktiv";

		String encoded = MorseCoder.encode(str);

		print(encoded);

		String decoded = MorseCoder.decode(encoded);

		print(decoded);
	}
}
