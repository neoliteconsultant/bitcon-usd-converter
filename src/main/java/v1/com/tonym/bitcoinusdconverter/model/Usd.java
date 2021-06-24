package v1.com.tonym.bitcoinusdconverter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class representation of an ExchangeRate.
 */
public class Usd {
    private String code;
    private String rate;
    private String description;
    @JsonProperty("rate_float")
    private float rateFloat;

    public Usd(String code, String rate, String description, float rateFloat) {
        this.code = code;
        this.rate = rate;
        this.description = description;
        this.rateFloat = rateFloat;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRateFloat() {
        return rateFloat;
    }

    public void setRateFloat(float rateFloat) {
        this.rateFloat = rateFloat;
    }
}
