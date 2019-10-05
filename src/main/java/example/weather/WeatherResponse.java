package example.weather;

import lombok.Data;

@Data
public class WeatherResponse {
    private WeatherInfo weatherInfo;

    public WeatherResponse(String currentSummary) {
        this.weatherInfo = new WeatherInfo(currentSummary);
    }

    public String getSummary() {
        return weatherInfo.getSummary();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherResponse response = (WeatherResponse) o;

        return weatherInfo != null ? weatherInfo.equals(response.weatherInfo) : response.weatherInfo == null;
    }

    @Override
    public int hashCode() {
        return weatherInfo != null ? weatherInfo.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "weatherinfo=" + weatherInfo +
                '}';
    }

    @Data
    public static class WeatherInfo {
        private String summary;

        public WeatherInfo(String summary) {
            this.summary = summary;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            WeatherInfo weatherInfo = (WeatherInfo) o;

            return summary != null ? summary.equals(weatherInfo.summary) : weatherInfo.summary == null;
        }

        @Override
        public int hashCode() {
            return summary != null ? summary.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "WeatherInfo{" +
                    "summary='" + summary + '\'' +
                    '}';
        }
    }
}
