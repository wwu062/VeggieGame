package veggie.textReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import veggie.model.Moves;

public class FileIO {

	public static final String fileSep = System.getProperty("file.separator");
	public static final String lineSep = System.getProperty("line.separator");

	public static ArrayList<String> readFile(String filename) throws IOException {

		Scanner scan = null;

		try {
			ArrayList<String> output = new ArrayList<String>();

			FileReader reader = new FileReader(filename);
			scan = new Scanner(reader);

			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				output.add(line);
			}

			return output;

		} finally {
			if (scan != null)
				scan.close();
		}

	}
	
	public Moves translateMoveList(String line) {
		String[] tokens = line.split(",");

		String move = tokens[0];
		int attack = Integer.parseInt(tokens[1]);
		
		Moves m = new Moves(move, attack);
		
		return m;
	}

}