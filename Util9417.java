/* PANSARE KSHITIJA DILIP cs610 9417 prp */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Util9417 {

	public static byte[] readBinaryFile(String filename) throws IOException{
		Path path = Paths.get(filename);
		return Files.readAllBytes(path);
	}

	public static void writeBinaryFile(byte[] aBytes, String fileName) throws IOException {
		Path path = Paths.get(fileName);
		Files.write(path, aBytes);//, StandardOpenOption.CREATE,StandardOpenOption.APPEND); //creates, overwrites
	}
	
}
