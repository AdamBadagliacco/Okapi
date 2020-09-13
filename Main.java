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

		// Stores the student data as studentCollection. The key is their ID #, the value is a Student object
		HashMap<Integer, Student> studentCollection = XLSXReader.importStudentData();
		
		XLSXReader.importTestScores(studentCollection, true, "data\\Test Scores.xlsx");// Setting the original test scores for each student
		
		XLSXReader.importTestScores(studentCollection, false, "data\\Test Retake Scores.xlsx");// Setting the re-take scores for students who took it

		int testScoreAverage = getAverageTestScore(studentCollection);
		
		ArrayList<String> femaleCSStudentIDs = getFemaleCSStudentIDs(studentCollection);

		JSONObject jsonToPOST = createJSON("adambadagliacco@gmail.com", "Adam Badagliacco", (int) testScoreAverage, femaleCSStudentIDs);
		
		postJSONtoURL(jsonToPOST, new URL("/challenge"));

	}

	/**
	 * Creates and returns a JSON of the data specified from the coding test
	 * instructions
	 */
	public static JSONObject createJSON(String id, String name, int average, ArrayList<String> studentIds) throws IOException {

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

		return myJSON;
	}

	/**
	 * Sends the myJSONtoPOST as a POST request to urlToPostAt
	 */
	public static void postJSONtoURL(JSONObject myJSONtoPOST, URL urlToPostAt) throws IOException {

		URLConnection con = urlToPostAt.openConnection();
		HttpURLConnection http = (HttpURLConnection) con;
		http.setRequestMethod("POST");
		http.setDoOutput(true);

		byte[] jsonAsByteStream = myJSONtoPOST.toString().getBytes(StandardCharsets.UTF_8);
		int length = jsonAsByteStream.length;

		http.setFixedLengthStreamingMode(length);
		http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		http.connect();
		try (OutputStream os = http.getOutputStream()) {
			os.write(jsonAsByteStream);
		}
	}

	public static ArrayList<String> getFemaleCSStudentIDs(HashMap<Integer, Student> studentCollection) {

		ArrayList<String> femaleCSStudentIDs = new ArrayList<String>();

		// Add in all females in CS from our studentCollection
		for (Student i : studentCollection.values()) {
			if (i.getMajor().equals("computer science") && i.getGender() == 'F') {
				femaleCSStudentIDs.add(i.getId());
			}
		}
		// Put in order based on ID
		Collections.sort(femaleCSStudentIDs);

		return femaleCSStudentIDs;
	}

	public static int getAverageTestScore(HashMap<Integer, Student> studentsToGetScoresFrom) {

		double totalNumberOfTests = studentsToGetScoresFrom.size();
		double sumOfTestScores = 0;
		for (Student eachStudent : studentsToGetScoresFrom.values()) {
			sumOfTestScores += eachStudent.getFinalScore();
		}
		double testAverage = sumOfTestScores / totalNumberOfTests;
		return (int) testAverage;
	}
}
