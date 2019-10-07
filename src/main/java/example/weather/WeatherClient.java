package example.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class WeatherClient {

    private static final String CITY_CODE = "101190101";
    private final RestTemplate restTemplate;
    private final String weatherServiceUrl;

    @Autowired
    public WeatherClient(final RestTemplate restTemplate,
                         @Value("${weather.url}") final String weatherServiceUrl) {
        this.restTemplate = restTemplate;
        this.restTemplate.getMessageConverters().add(new WeatherMappingJackson2HttpMessageConverter());
        this.weatherServiceUrl = weatherServiceUrl;
    }

    /**
     * 查询天气
     * @return
     */
    public Optional<WeatherResponse> fetchWeather() {
        String url = String.format("%s/%s.html", weatherServiceUrl, CITY_CODE);

        try {
            WeatherResponse response = null;
            CnWeatherResponse cnResponse = restTemplate.getForObject(url, CnWeatherResponse.class);
            if (cnResponse != null && cnResponse.getWeatherinfo() != null) {
                CnWeatherResponse.Info info = cnResponse.getWeatherinfo();
                String summary = String.format("%s", info.getTemp());
                response = new WeatherResponse(summary);
            }
            return Optional.ofNullable(response);
        } catch (RestClientException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
