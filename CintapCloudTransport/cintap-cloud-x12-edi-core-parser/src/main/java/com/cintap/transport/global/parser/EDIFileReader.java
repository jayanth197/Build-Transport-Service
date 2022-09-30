/**
 * 
 */
package com.cintap.transport.global.parser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author SurenderMogiloju
 *
 */
public class EDIFileReader {
	
	public static void main(String as[]) throws Exception {
		EDI204Parser parser = new EDI204Parser();
		EDIFileReader reader = new EDIFileReader();
		parser.parseEDI204Message(reader.readFromFile());
		
	}
	
	private List<String> readFromFile() throws Exception {
		Path path = Paths.get("C:\\appsdata\\204_edi.edi");
		//String content = Files.readString(path);
		//return content;
        List<String> fileLines = Files.readAllLines(path);
		return fileLines;
		
	}
}
