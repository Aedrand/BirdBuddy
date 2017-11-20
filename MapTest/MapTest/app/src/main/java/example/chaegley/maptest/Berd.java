package example.chaegley.maptest;

/**
 * Created by chaegley on 11/20/17.
 */

public class Berd {
    boolean lng;
    String locName;
    int howMany;
    String sciName;
    boolean obsValid;
    boolean locationPrivate;
    String obsDt;
    boolean obsReviewed;
    String comName;
    double lat;
    String locID;


    public Berd(boolean lng, String locName, int howMany, String sciName, boolean obsValid, boolean locationPrivate, String obsDt, boolean obsReviewed, String comName, double lat, String locID) {
        this.lng = lng;
        this.locName = locName;
        this.howMany = howMany;
        this.sciName = sciName;
        this.obsValid = obsValid;
        this.locationPrivate = locationPrivate;
        this.obsDt = obsDt;
        this.obsReviewed = obsReviewed;
        this.comName = comName;
        this.lat = lat;
        this.locID = locID;
    }

    public boolean isLng() {
        return lng;
    }

    public void setLng(boolean lng) {
        this.lng = lng;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public int getHowMany() {
        return howMany;
    }

    public void setHowMany(int howMany) {
        this.howMany = howMany;
    }

    public String getSciName() {
        return sciName;
    }

    public void setSciName(String sciName) {
        this.sciName = sciName;
    }

    public boolean isObsValid() {
        return obsValid;
    }

    public void setObsValid(boolean obsValid) {
        this.obsValid = obsValid;
    }

    public boolean isLocationPrivate() {
        return locationPrivate;
    }

    public void setLocationPrivate(boolean locationPrivate) {
        this.locationPrivate = locationPrivate;
    }

    public String getObsDt() {
        return obsDt;
    }

    public void setObsDt(String obsDt) {
        this.obsDt = obsDt;
    }

    public boolean isObsReviewed() {
        return obsReviewed;
    }

    public void setObsReviewed(boolean obsReviewed) {
        this.obsReviewed = obsReviewed;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getLocID() {
        return locID;
    }

    public void setLocID(String locID) {
        this.locID = locID;
    }



}
