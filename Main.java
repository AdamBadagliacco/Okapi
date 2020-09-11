package main.java.AdamsGroupID.AdamsArtifactID;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Made to fulfill the requirements specified in the Coding Challenge
 * 
 * @author Adam Badagliacco
 * @Date 9/11/2020
 */
public class Main {

	public static void main(String[] args) throws IOException {

		// Create a HashMap to store the student data. The key is their ID #, the value
		// is a Student object
		HashMap<Integer, Student> myStudents = XLSXReader.importStudentData();

		// Setting the original test scores for each student
		XLSXReader.importTestScores(myStudents, true, "data\\Test Scores.xlsx");

		// Setting the re-take scores for students who took it
		XLSXReader.importTestScores(myStudents, false, "data\\Test Retake Scores.xlsx");

		// Get the test average saved to testAverage
		double numberOfTests = myStudents.size();
		double testTotal = 0;
		for (Student i : myStudents.values()) {
			testTotal += i.getFinalScore();
		}
		double testAverage = testTotal / numberOfTests;

		// Create an Array for all the female students majoring in computer science
		ArrayList<String> femaleCS = new ArrayList<String>();
		// Add in all females in CS
		for (Student i : myStudents.values()) {
			if (i.getMajor().equals("computer science") && i.getGender() == 'F') {
				femaleCS.add(i.getId());
			}
		}
		// Put in order based on ID
		Collections.sort(femaleCS);

		// Sends a POST request to "/challenge" with the JSON data specified in the
		// coding challenge instructions
		postRequest("adambadagliacco@gmail.com", "Adam Badagliacco", (int) testAverage, femaleCS);
	}

	public static void postRequest(String id, String name, int average, ArrayList<String> studentIds)
			throws IOException {

		// Create new JSON Object containing our data to POST
		JSONObject myJSON = new JSONObject();
		myJSON.put("id", id);
		myJSON.put("name", name);
		myJSON.put("average", average);
		// Making JSONArray for the studentIds so it can be added to myJSON
		JSONArray jsonStudentIds = new JSONArray();
		for (int i = 0; i < studentIds.size(); i++) {
			jsonStudentIds.put(studentIds.get(i));
		}
		myJSON.put("studentIds", jsonStudentIds);

		// POST myJSON to "/challenge"
		URL url = new URL("/challenge");
		URLConnection con = url.openConnection();
		HttpURLConnection http = (HttpURLConnection) con;
		http.setRequestMethod("POST");
		http.setDoOutput(true);

		byte[] out = myJSON.toString().getBytes(StandardCharsets.UTF_8);
		int length = out.length;

		http.setFixedLengthStreamingMode(length);
		http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		http.connect();
		try (OutputStream os = http.getOutputStream()) {
			os.write(out);
		}

	}
}
