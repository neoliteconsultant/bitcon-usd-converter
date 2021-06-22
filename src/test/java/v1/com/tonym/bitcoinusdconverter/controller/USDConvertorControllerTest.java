package v1.com.tonym.bitcoinusdconverter.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import v1.com.tonym.bitcoinusdconverter.service.ExchangeRateService;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(USDConverterController.class)
public class USDConvertorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateService exchangeRateService;


    @Test
    public void shouldReturnRatesLatestUSD() throws Exception {
        Map latestRates = new HashMap<>();
        latestRates.put("code", "USD");
        latestRates.put("rate", "35,320.1867");
        latestRates.put("description", "United States Dollar");
        latestRates.put("rate_float", 35320.1867);
        when(exchangeRateService.getLatestExchangeRate()).thenReturn(latestRates);

        this.mockMvc.perform(get("/v1/rates/latest")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("USD"))
                .andExpect(jsonPath("$.rate").value("35,320.1867"))
                .andExpect(jsonPath("$.description").value("United States Dollar"))
                .andExpect(jsonPath("$.rate_float").value(35320.1867));

    }


    @Test
    public void shouldReturnRatesBetweenDate() throws Exception {
        String startDate = "2021-01-25";
        String endDate = "2021-01-27";

        Map historicalRates = new HashMap();
        historicalRates.put("2021-01-25", 32255.35);
        historicalRates.put("2021-01-26", 32518.3583);
        historicalRates.put("2021-01-27", 30425.3933);


        when(exchangeRateService.getHistoricalRates(startDate, endDate)).thenReturn(historicalRates);

        this.mockMvc.perform(get("/v1/rates/historical")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.2021-01-25").value(32255.35))
                .andExpect(jsonPath("$.2021-01-26").value(32518.3583))
                .andExpect(jsonPath("$.2021-01-27").value(30425.3933));

    }

}
