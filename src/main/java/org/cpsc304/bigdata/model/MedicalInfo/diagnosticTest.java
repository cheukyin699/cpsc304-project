package org.cpsc304.bigdata.model.MedicalInfo;

public class diagnosticTest {

    private String testName;
    private int accuracy;

    public diagnosticTest(String testName, int accuracy) {
        this.testName = testName;
        this.accuracy = accuracy;
    }

    public String getTestName() { return testName; }
    public int getAccuracy() { return accuracy; }

    public void setTestName(String testName) { this.testName = testName; }
    public void setAccuracy(int accuracy) {this.accuracy = accuracy; }
}
