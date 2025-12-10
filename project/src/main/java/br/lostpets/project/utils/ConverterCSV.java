package br.lostpets.project.utils;

import java.io.*;
import jxl.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.lostpets.project.model.PetPerdido;

/**
 * Utility class for converting Excel files to CSV format and importing animal data.
 * 
 * Refactoring improvements:
 * - Added proper logging instead of System.err.println
 * - Added file existence checks
 * - Improved exception handling
 * - Added JavaDoc documentation
 */
@Component
public class ConverterCSV {
	
	private static final Logger logger = LoggerFactory.getLogger(ConverterCSV.class);
	private static final String INPUT_DIR = "animaisPerdidos";
	private static final String INPUT_FILE = "animaisPerdidos/entrada.xls";
	private static final String OUTPUT_FILE = "animaisPerdidos/saida.txt";
	
	public ConverterCSV() {
		try {
			ensureDirectoryExists();
			convertExcelToCsv();
			importAnimals();
		} catch (Exception e) {
			logger.error("Error in ConverterCSV initialization", e);
		}
	}

	/**
	 * Ensures the output directory exists, creating it if necessary
	 */
	private void ensureDirectoryExists() throws IOException {
		Path dirPath = Paths.get(INPUT_DIR);
		if (!Files.exists(dirPath)) {
			Files.createDirectories(dirPath);
			logger.info("Created directory: {}", INPUT_DIR);
		}
	}

	/**
	 * Converts Excel file to CSV format
	 */
	private void convertExcelToCsv() throws IOException {
		File inputFile = new File(INPUT_FILE);
		
		if (!inputFile.exists()) {
			logger.error("Input file does not exist: {}", INPUT_FILE);
			throw new FileNotFoundException("Input file not found: " + INPUT_FILE);
		}
		
		if (!inputFile.canRead()) {
			logger.error("Cannot read input file: {}", INPUT_FILE);
			throw new IOException("Input file is not readable: " + INPUT_FILE);
		}

		File outputFile = new File(OUTPUT_FILE);
		
		try (OutputStream os = new FileOutputStream(outputFile);
			 OutputStreamWriter osw = new OutputStreamWriter(os, "UTF8");
			 BufferedWriter bw = new BufferedWriter(osw)) {

			WorkbookSettings ws = new WorkbookSettings();
			ws.setLocale(new Locale("pt", "BR"));
			Workbook w = Workbook.getWorkbook(inputFile, ws);

			for (int sheet = 0; sheet < w.getNumberOfSheets(); sheet++) {
				Sheet s = w.getSheet(sheet);

				for (int i = 0; i < s.getRows(); i++) {
					Cell[] row = s.getRow(i);

					if (row.length > 0) {
						bw.write(row[0].getContents());
						for (int j = 1; j < row.length; j++) {
							bw.write(';');
							bw.write(row[j].getContents());
						}
					}
					bw.newLine();
				}
			}
			
			w.close();
			logger.info("Successfully converted Excel to CSV: {}", OUTPUT_FILE);
			
		} catch (UnsupportedEncodingException e) {
			logger.error("Unsupported encoding", e);
			throw new IOException("Encoding error", e);
		} catch (Exception e) {
			logger.error("Error converting Excel to CSV", e);
			throw new IOException("Conversion error", e);
		}
	}

	/**
	 * Imports animal data from the CSV file
	 */
	public void importAnimals() {
		File file = new File(OUTPUT_FILE);
		
		if (!file.exists()) {
			logger.error("Output file does not exist for import: {}", OUTPUT_FILE);
			return;
		}

		Scanner in = null;

		try {
			in = new Scanner(new FileReader(file)).useDelimiter(";|\\r\\n");
			
			PetPerdido pet = new PetPerdido(null, null, null, null, null, null, null, 0, 0);

			while (in.hasNext()) {
				pet.setNomeAnimal(in.next());
				pet.setDescricao(in.next());
				pet.setTipoAnimal(in.next());
				pet.setCep(in.next());
			}
			
			logger.info("Successfully imported animal data from {}", OUTPUT_FILE);
			
		} catch (FileNotFoundException e) {
			logger.error("File not found when importing animals: {}", OUTPUT_FILE, e);
		} catch (NoSuchElementException e) {
			logger.error("File format error - missing expected data", e);
		} catch (IllegalStateException e) {
			logger.error("Scanner error while reading file", e);
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}
}