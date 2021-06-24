package v1.com.tonym.bitcoinusdconverter.service;


import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import v1.com.tonym.bitcoinusdconverter.util.ApiUtil;
import v1.com.tonym.bitcoinusdconverter.util.FileUtil;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRateServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExchangeRateService exchangeRateService;



    @Test
    public void shouldReturnRatesLatestUSD() {
        String apiOutput = FileUtil.readFile("latest_rates.json");
        final String latestRateURL = ApiUtil.getAbsoluteURL("currentprice/USD.json");
        //Mock Rest Template
        when(restTemplate.getForEntity(latestRateURL, String.class)).thenReturn(new ResponseEntity(apiOutput, HttpStatus.OK));


        Map expectedRates = new HashMap<>();
        expectedRates.put("code", "USD");
        expectedRates.put("rate", "35,320.1867");
        expectedRates.put("description", "United States Dollar");
        expectedRates.put("rate_float", 35320.1867);



         /*
        ExchangeRate latestExchangeRate = new ExchangeRate();
        Time time = new Time("Jun 23, 2021 12:03:00 UTC","2021-06-23T12:03:00+00:00","Jun 23, 2021 at 13:03 BST");
        latestExchangeRate.setTime(time);
        latestExchangeRate.setDisclaimer("This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org");

        Bpi bpi = new Bpi();
        bpi.setUsd(new Usd("USD","34,008.4200","United States Dollar",34008.42f));
        latestExchangeRate.setBpi(bpi);*/

        Map actualRates = exchangeRateService.getLatestExchangeRate();
        assertEquals(expectedRates, actualRates);

    }


    @Test
    public void shouldReturnRatesBetweenDate() {
        String startDate = "2021-01-25";
        String endDate = "2021-01-27";

        final String historicalDataURL = ApiUtil.getAbsoluteURL("historical/close.json?start={start}&end={end}");
        Map<String, String> params = new HashMap<>();
        params.put("start", startDate);
        params.put("end", endDate);

        String apiOutput = FileUtil.readFile("historical_rates.json");
        //Mock Rest Template
        when(restTemplate.getForEntity(historicalDataURL, String.class, params)).thenReturn(new ResponseEntity(apiOutput, HttpStatus.OK));

        Map expectedRates = new HashMap();
        expectedRates.put("2021-01-25", 32255.35);
        expectedRates.put("2021-01-26", 32518.3583);
        expectedRates.put("2021-01-27", 30425.3933);

        Map actualRates = exchangeRateService.getHistoricalRates(startDate, endDate);
        assertEquals(expectedRates, actualRates);
    }


}
