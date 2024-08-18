package com.app.entity;
import lombok.Getter;
import lombok.ToString;

@Getter
public enum Slot {
	 MORNING("Morning", "9:00 AM - 12:00 PM"),
	 AFTERNOON("Afternoon", "1:00 PM - 4:00 PM"),
	 EVENING("Evening", "4:00 PM - 8:00 PM");

	 private final String description;
	    private final String time;

	    Slot(String description, String time) {
	        this.description = description;
	        this.time = time;
	  }
    //aadded the to string to show time also
    @Override
    public String toString() {
        return description + " (" + time + ")";
    }

}
