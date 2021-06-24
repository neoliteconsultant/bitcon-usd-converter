
package v1.com.tonym.bitcoinusdconverter.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import v1.com.tonym.bitcoinusdconverter.service.ExchangeRateService;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * A class that periodically checks for exchange rates
 */
@Component
public class ScheduleExchangeRateTask {

  /**
   * interval to call exchange rate in seconds.
   */
  @Value("${rate.period}")
  private int ratePeriod; // read value from properties file

  private static final Logger log = LoggerFactory.getLogger(ScheduleExchangeRateTask.class);

  @Autowired
  private ExchangeRateService exchangeRateService;

  @PostConstruct
  public void initialize() {
    long exchangeRateInterval = ratePeriod * 1000;

    System.setProperty("exchangeRateInterval", String.valueOf(exchangeRateInterval));

//    log.info("Exchange rate is {} seconds", ratePeriod);

  }

  @Scheduled(fixedRateString = "${exchangeRateInterval}")
  public void checkExchangeRate() {
    final Map latestExchangeRate = exchangeRateService.getLatestExchangeRate();
    if (latestExchangeRate != null) {
      log.info("Latest exchange rate is {}", latestExchangeRate);
    }

  }
}
