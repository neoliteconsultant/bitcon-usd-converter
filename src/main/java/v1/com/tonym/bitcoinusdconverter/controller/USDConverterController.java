package v1.com.tonym.bitcoinusdconverter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import v1.com.tonym.bitcoinusdconverter.model.ExchangeRate;
import v1.com.tonym.bitcoinusdconverter.service.ExchangeRateService;

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
    private ExchangeRateService exchangeRateService;

    /**
     * Endpoint to get the latest USD rate.
     *
     * @return  {@link ExchangeRate}.
     */
    @GetMapping("/latest")
    public ResponseEntity<Map> getLatestRate() {
        Map exchangeRateResult = exchangeRateService.getLatestExchangeRate();
        if(exchangeRateResult == null){

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error","Unexpected server error, try again later");

            return ResponseEntity.badRequest()
                    .body(errorMap);
        }

        return ResponseEntity.ok(exchangeRateResult);
    }

    /**
     * Endpoint to get rates between two dates.
     *
     * @param startDate
     * @param endDate
     * @return {@link ExchangeRate}.
     */
    @GetMapping("/historical")
    public ResponseEntity<Map> getDateRangeRate(@RequestParam("startDate") String startDate,
                                 @RequestParam("endDate") String endDate) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> errorMap = new HashMap<>();
        try {
            final Date start = dateFormat.parse(startDate);
            final Date end = dateFormat.parse(endDate);


            if(start.after(end)){
                errorMap.put("error", "Start date is later than end date");
                return ResponseEntity.badRequest()
                        .body(errorMap);
            }
        } catch (ParseException e) {

            e.printStackTrace();
        }



        Map exchangeRateResult = exchangeRateService.getHistoricalRates(startDate, endDate);
        if(exchangeRateResult == null){//Coindesk API is unavailable

            errorMap.clear();
            errorMap.put("error","Unexpected server error, try again later");

            return ResponseEntity.badRequest()
                    .body(errorMap);
        }
        return ResponseEntity.ok(exchangeRateResult);
    }


}