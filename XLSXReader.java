package main.java.AdamsGroupID.AdamsArtifactID;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSXReader {

	private static XSSFWorkbook wb;

	/*
	 * Returns a HashMap containing Student data read in from Excel
	 */
	public static HashMap<Integer, Student> importStudentData() throws IOException {

		HashMap<Integer, Student> toBeReturned = new HashMap<Integer, Student>();

		wb = new XSSFWorkbook(new FileInputStream(new File("data\\Student Info.xlsx")));
		XSSFSheet sheet = wb.getSheetAt(0);
		Iterator<Row> itr = sheet.iterator(); // Used to iterate through the rows and cells

		itr.next(); // Skip the heading row

		while (itr.hasNext()) {// Continues to iterate through each row until no more data is present in excel
			Row row = itr.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			while (cellIterator.hasNext()) { // Creates a new student in our array out of the data in each row
				Cell cell = cellIterator.next();
				int id = (int) cell.getNumericCellValue();

				cell = cellIterator.next();
				String major = cell.getStringCellValue();

				cell = cellIterator.next();
				String gender = cell.getStringCellValue();

				toBeReturned.put(id, new Student(Integer.toString(id), major, gender.charAt(0)));
			}
		}
		return toBeReturned;
	}

	/*
	 * Updates each students Test Score with the data from the Excel Sheet
	 * 
	 * testType is true for the original test and false for the reTake
	 */
	public static void importTestScores(HashMap<Integer, Student> myStudents, boolean testType, String filePath)
			throws FileNotFoundException, IOException {

		wb = new XSSFWorkbook(new FileInputStream(new File(filePath)));
		XSSFSheet sheet = wb.getSheetAt(0);
		Iterator<Row> itr = sheet.iterator(); // Used to iterate through the rows and cells

		itr.next(); // Skip the heading row

		while (itr.hasNext()) {// Continues to iterate through each row until no more data is present in excel
			Row row = itr.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			while (cellIterator.hasNext()) { // Creates a new student in our array out of the data in each row
				Cell cell = cellIterator.next();
				int id = (int) cell.getNumericCellValue();

				cell = cellIterator.next();
				double score = cell.getNumericCellValue();

				if (testType) {
					myStudents.get(id).setTestScore(score);
				} else {
					myStudents.get(id).setReTakeScore(score);
				}
			}
		}

	}
}