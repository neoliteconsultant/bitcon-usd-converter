package v1.com.tonym.bitcoinusdconverter.controller;

import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import v1.com.tonym.bitcoinusdconverter.model.ExchangeRate;
import v1.com.tonym.bitcoinusdconverter.util.ApiUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * API endpoints for converting BTC to USD.
 *
 * @author <a href="tonyafula@gmail.com">Tony Afula</a>
 */
@RestController
@RequestMapping("/v1/rates")
public class USDConverterController {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Endpoint to get rates between two dates.
     *
     * @param startDate
     * @param endDate
     * @return {@link ExchangeRate}.
     */
    @GetMapping(value = "/historical", produces = { MediaType.APPLICATION_JSON_VALUE})
    public Map getDateRangeRate(@RequestParam("startDate") String startDate,
                                 @RequestParam("endDate")  String endDate) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> errorMap = new HashMap<>();
        try {
            final Date start = dateFormat.parse(startDate);
            final Date end = dateFormat.parse(endDate);


            if(start.after(end)){
                errorMap.put("error", "Start date is later than end date");
                return errorMap;
//                return ResponseEntity.badRequest()
//                        .body("Start date is later than end date");
            }
        } catch (ParseException e) {

            e.printStackTrace();
        }



        final String historicalDataURL = ApiUtil.getAbsoluteURL("historical/close.json?start={start}&end={end}");
        Map<String, String> params = new HashMap<>();
        params.put("start",startDate);
        params.put("end", endDate);

        ResponseEntity<String> exchangeRateResult = restTemplate.getForEntity(historicalDataURL, String.class,params);
        if(exchangeRateResult.getStatusCodeValue()!= HttpStatus.OK.value()){//Coindesk API is unavailable
//            return ResponseEntity.badRequest()
//                    .body("Unexpected server error, try again later");
            errorMap.clear();
            errorMap.put("error","Unexpected server error, try again later");
            return errorMap;
        }
        String resultBody = exchangeRateResult.getBody();

        //Parse the JSON response using X-Path like expressions inorder to retrieve the historical USD exchange rates
        Map usdExchangeRates = JsonPath.parse(resultBody).read("$.bpi");


        //return ResponseEntity.ok(usdExchangeRates);
        return usdExchangeRates;
    }

    /**
     * Endpoint to get the latest USD rate.
     *
     * @return  {@link ExchangeRate}.
     */
    @GetMapping("/latest")
    public  ExchangeRate getLatestRate() {
        final String latestRateURL = ApiUtil.getAbsoluteURL("currentprice/USD.json");
        ResponseEntity<String> exchangeRateResult = restTemplate.getForEntity(latestRateURL, String.class);

        String resultBody = exchangeRateResult.getBody();

        //Parse the JSON response using X-Path like expressions inorder to retrieve the USD exchange rate
        ExchangeRate usdExchangeRate = JsonPath.parse(resultBody).read("$.bpi.USD", ExchangeRate.class);

        return usdExchangeRate;
    }
}