package v1.com.tonym.bitcoinusdconverter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Time {
    private String updated;
    private String updatedISO;
    @JsonProperty("updateduk")
    private String updatedUk;

    public Time(String updated, String updatedISO, String updatedUk) {
        this.updated = updated;
        this.updatedISO = updatedISO;
        this.updatedUk = updatedUk;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUpdatedISO() {
        return updatedISO;
    }

    public void setUpdatedISO(String updatedISO) {
        this.updatedISO = updatedISO;
    }

    public String getUpdatedUk() {
        return updatedUk;
    }

    public void setUpdatedUk(String updatedUk) {
        this.updatedUk = updatedUk;
    }
}
