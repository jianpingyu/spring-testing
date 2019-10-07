package example.weather;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringRunner.class)
public class WeatherClientTest {

    private WeatherClient subject;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        subject = new WeatherClient(restTemplate, "http://localhost:8089");
    }

    @Test
    public void shouldCallWeatherService() throws Exception {
        String summary = "light rain";
        WeatherResponse expectedResponse = new WeatherResponse(summary);
        CnWeatherResponse cnResponse = new CnWeatherResponse();
        CnWeatherResponse.Info info = new CnWeatherResponse.Info();
        info.setTemp(summary);
        cnResponse.setWeatherinfo(info);
        given(restTemplate.getForObject("http://localhost:8089/101190101.html", CnWeatherResponse.class))
                .willReturn(cnResponse);

        Optional<WeatherResponse> actualResponse = subject.fetchWeather();

        assertThat(actualResponse, is(Optional.of(expectedResponse)));
    }

    @Test
    public void shouldReturnEmptyOptionalIfWeatherServiceIsUnavailable() throws Exception {
        given(restTemplate.getForObject("http://localhost:8089/101190101.html", CnWeatherResponse.class))
                .willThrow(new RestClientException("something went wrong"));

        Optional<WeatherResponse> actualResponse = subject.fetchWeather();

        assertThat(actualResponse, is(Optional.empty()));

    }

}