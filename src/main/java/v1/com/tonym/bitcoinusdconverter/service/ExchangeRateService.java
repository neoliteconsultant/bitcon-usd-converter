package v1.com.tonym.bitcoinusdconverter.service;

import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import v1.com.tonym.bitcoinusdconverter.util.ApiUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeRateService {
    @Autowired
    private RestTemplate restTemplate;

    public Map getHistoricalRates(String startDate, String endDate){
        final String historicalDataURL = ApiUtil.getAbsoluteURL("historical/close.json?start={start}&end={end}");
        Map<String, String> params = new HashMap<>();
        params.put("start",startDate);
        params.put("end", endDate);

        Map usdExchangeRates=null;
        ResponseEntity<String> exchangeRateResult = restTemplate.getForEntity(historicalDataURL, String.class,params);
        if(exchangeRateResult.getStatusCodeValue()== HttpStatus.OK.value()){

            String resultBody = exchangeRateResult.getBody();

            //Parse the JSON response using X-Path like expressions inorder to retrieve the historical USD exchange rates
            usdExchangeRates = JsonPath.parse(resultBody).read("$.bpi");
        }

        return usdExchangeRates;

    }

    public Map getLatestExchangeRate(){
        final String latestRateURL = ApiUtil.getAbsoluteURL("currentprice/USD.json");
        ResponseEntity<String> exchangeRateResult = restTemplate.getForEntity(latestRateURL, String.class);

        Map usdExchangeRate = null;
        if(exchangeRateResult.getStatusCodeValue()== HttpStatus.OK.value()){//Coindesk API is unavailable
            String resultBody = exchangeRateResult.getBody();
            usdExchangeRate = JsonPath.parse(resultBody).read("$.bpi.USD");
        }

        return usdExchangeRate;
    }


}
