import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class FileExemle {
	final static Logger logger = Logger.getLogger(FileExemle.class.getCanonicalName());

	public static void main(final String[] args) {
		logger.log(Level.INFO, "in FileExemple");
		String fullPathString = System.getProperty("user.dir");
		final Path fullPath = Paths.get(fullPathString);
		final Path thisDirPath = fullPath.getFileName();
		// String thisPathDir = System.getProperty(".");
		logger.log(Level.INFO, fullPath + " hiii " + thisDirPath);
		
		if(Files.isDirectory(fullPath)) {
			logger.log(Level.INFO, fullPath + " isDirectory ");
		}
		try {
			ls(fullPath);
		}catch (IOException ex) {
         // I/O error encounted during the iteration, the cause is an IOException
     }
		
		trayCode("firstFile.txt");
		trayCodeWrite("firstFile.txt");
		if(args.length > 1) {
			final String functinName = args[0];
			final String functionFlags = args[1];
			String functionData = "";
			for (int i = 2; i < args.length; ++i) {
				functionData += args[i]+" ";	
			}
			diractFunction(functinName,functionFlags,functionData);
		}
	}

	private static void ls(Path fullPath) throws IOException{
		logger.log(Level.INFO, "in ls"+fullPath);
		List<Path> result = new ArrayList<>();
      try (DirectoryStream<Path> stream = Files.newDirectoryStream(fullPath, "*")) {
      	//((Collection<Path>) stream).stream().filter(p -> p.endsWith("txt"));
          for (Path entry: stream) {
              result.add(entry);
          }
      } catch (DirectoryIteratorException ex) {
          throw ex.getCause();
      }
      for(Path p :result) {
      	System.out.println(p.getFileName());
      }      
	}

	private static void trayCodeWrite(String fileName) {
		final Path path = Paths.get(fileName);
		final Path pathEndsWith = Paths.get(".txt");
		if (path.endsWith(pathEndsWith)) {
			logger.log(Level.INFO, path.toString());
		}
		try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"))) {
			writer.write("hi\n");
			writer.newLine();
			writer.write("hi");
		} catch (IOException ex) {
			ex.printStackTrace(); // handle an exception here
		}

	}

	private static void trayCode(final String fileName) {
		final Path path = Paths.get(fileName);
		final Path pathEndsWith = Paths.get(".txt");
		if (path.endsWith(pathEndsWith)) {
			logger.log(Level.INFO, path.toString());
		}
		try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
			String currentLine = null;
			while ((currentLine = reader.readLine()) != null) {// while there is content on the current line
				System.out.println(currentLine); // print the current line
			}
		} catch (IOException ex) {
			ex.printStackTrace(); // handle an exception here
		}
		// logger.log(Level.INFO, path.toString());

	}

	private static void diractFunction(final String functinName, final String functionFlags, final String functionData) {
		if (functinName.equals("write")) {
			createAndWriteToFile(functionFlags, functionData);
		}
		if (functinName.equals("type")) {
			cat(functionFlags);
		}
		if (functinName.equals("dir")) {
			ls(functionFlags);
		}
	}

	private static void ls(final String fileName) {

	}

	private static void createAndWriteToFile(final String fileName, final String fileText) {
		final Path path = Paths.get(fileName);
		final Path pathEndsWith = Paths.get(".txt");
		if (!path.endsWith(pathEndsWith)) {
			logger.log(Level.INFO, path.toString()+"is not a valid file to open");
		}
		try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"))) {
			writer.write(fileText);
		} catch (IOException ex) {
			ex.printStackTrace(); // handle an exception here
		}
	}

	private static void cat(String fileName) {
		try {
			File myObj = new File(fileName);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				logger.log(Level.INFO, data);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
