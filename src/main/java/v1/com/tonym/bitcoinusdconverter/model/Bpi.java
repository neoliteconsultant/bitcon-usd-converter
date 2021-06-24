package v1.com.tonym.bitcoinusdconverter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bpi {
    @JsonProperty("USD")
    private Usd usd;

    public Usd getUsd() {
        return usd;
    }

    public void setUsd(Usd usd) {
        this.usd = usd;
    }
}
