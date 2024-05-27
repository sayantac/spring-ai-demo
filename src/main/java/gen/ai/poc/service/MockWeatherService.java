package gen.ai.poc.service;

import java.util.function.Function;

public class MockWeatherService implements Function<MockWeatherService.Request, MockWeatherService.Response> {

    public enum Unit { C, F }
    public record Request(String location, Unit unit) {}
    public record Response(double temp, Unit unit) {}

    @Override
    public MockWeatherService.Response apply(MockWeatherService.Request request) {
        return new MockWeatherService.Response(30.0, Unit.C);
    }
}
