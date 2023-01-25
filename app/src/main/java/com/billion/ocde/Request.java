package com.billion.ocde;

public class Request {
    private String createBy;
    private String emailAddress;
    private String deviceID;
    private String count;
    private String name;
    private String story;


    private String location;

    public String getCreateBy() {
	return createBy;
    }

    public void setCreateBy(String createBy) {
	this.createBy = createBy;
    }

    public String getEmailAddress() {
	return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
	this.emailAddress = emailAddress;
    }

    public String getDeviceID() {
	return deviceID;
    }

    public void setDeviceID(String deviceID) {
	this.deviceID = deviceID;
    }

    public String getCount() {
	return count;
    }

    public void setCount(String count) {
	this.count = count;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getStory() {
	return story;
    }

    public void setStory(String story) {
	this.story = story;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
