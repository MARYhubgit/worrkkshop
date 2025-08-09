package io.github.some_example_name.model.intraction;

public class MarriageRequest {
    private String proposerUsername;
    private String targetUsername;
    private String ringName;

    public MarriageRequest(String proposerUsername, String targetUsername, String ringName) {
        this.proposerUsername = proposerUsername;
        this.targetUsername = targetUsername;
        this.ringName = ringName;
    }

    public String getProposerUsername() {
        return proposerUsername;
    }

    public String getTargetUsername() {
        return targetUsername;
    }

    public String getRingName() {
        return ringName;
    }
}
