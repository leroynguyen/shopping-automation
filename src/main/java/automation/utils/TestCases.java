package automation.utils;

public enum TestCases {
    T1("Testing the authentication"),
    T2("Testing the purchase of one item");

    private String testName;

    TestCases(String value){
        this.testName = value;
    }

    public String getTestName() {
        return testName;
    }

}
