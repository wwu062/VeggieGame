package veggie.textReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import veggie.model.Move;

public class FileIO {

	public static final String fileSep = System.getProperty("file.separator");
	public static final String lineSep = System.getProperty("line.separator");

	/**
	 * Reads in a file into an ArrayList of Strings
	 * @param filename the source of the file
	 * @return an Arraylist of the file with each line on another index
	 * @throws IOException if the file is not a text file
	 */
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
	
	/**
	 * translates the movelist text file into the Moves class
	 * @param line the line being read from in the text file
	 * @return an object of the Moves class
	 */
	public Move translateMoveList(String line) {
		String[] tokens = line.split(",");

		String move = tokens[0];
		int attack = Integer.parseInt(tokens[1]);
		String effect = tokens[2];
		
		Move m = new Move(move, attack, effect);
		
		return m;
	}

}