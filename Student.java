package main.java.AdamsGroupID.AdamsArtifactID;

public class Student {

	private String id;
	private String major;
	private char gender; // Typically 'M' or 'F' for male or female

	private double testScore;
	private double reTakeScore;

	public Student(String id, String major, char gender) {
		this.id = id;
		this.major = major;
		this.gender = gender;

		// Set the test scores to 0 so they aren't null; They will be assigned through
		// the setter methods
		testScore = 0;
		reTakeScore = 0;
	}

	public String toString() {
		return id + " " + major + " " + gender + " " + testScore + " " + reTakeScore + " ";
	}

	public double getFinalScore() {
		if (testScore > reTakeScore) {
			return testScore;
		} else {
			return reTakeScore;
		}
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the major
	 */
	public String getMajor() {
		return major;
	}

	/**
	 * @param major the major to set
	 */
	public void setMajor(String major) {
		this.major = major;
	}

	/**
	 * @return the gender
	 */
	public char getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(char gender) {
		this.gender = gender;
	}

	public double getTestScore() {
		return testScore;
	}

	public void setTestScore(double testScore) {
		this.testScore = testScore;
	}

	public double getReTakeScore() {
		return reTakeScore;
	}

	public void setReTakeScore(double reTakeScore) {
		this.reTakeScore = reTakeScore;
	}

}
