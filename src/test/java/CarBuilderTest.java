import org.junit.jupiter.api.Test;
import org.team1.app.Car;

import static org.junit.jupiter.api.Assertions.*;

class CarBuilderTest {

    @Test
    void shouldBuildCarWithValidData() {
        Car car = new Car.CarBuilder()
                .setType("car")
                .setModel("Toyota")
                .setPower(150)
                .setMileage(20000)
                .build();

        assertEquals("car", car.getType());
        assertEquals("Toyota", car.getModel());
        assertEquals(150, car.getPower());
        assertEquals(20000, car.getMileage());
    }

    @Test
    void shouldThrowWhenModelIsEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
                new Car.CarBuilder()
                        .setType("car")
                        .setModel("")
                        .setPower(150)
                        .setMileage(20000)
                        .build());
    }

    @Test
    void shouldThrowWhenModelIsNotAllowed() {
        assertThrows(IllegalArgumentException.class, () ->
                new Car.CarBuilder()
                        .setType("car")
                        .setModel("Bugatti")
                        .setPower(150)
                        .setMileage(20000)
                        .build());
    }

    @Test
    void shouldThrowWhenPowerIsNotPositive() {
        assertThrows(IllegalArgumentException.class, () ->
                new Car.CarBuilder()
                        .setType("car")
                        .setModel("Toyota")
                        .setPower(0)
                        .setMileage(20000)
                        .build());
    }

    @Test
    void shouldThrowWhenMileageIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new Car.CarBuilder()
                        .setType("car")
                        .setModel("Toyota")
                        .setPower(150)
                        .setMileage(-1)
                        .build());
    }

    @Test
    void shouldHaveEqualsAndHashCode() {
        Car first = new Car.CarBuilder()
                .setType("car").setModel("Toyota")
                .setPower(150).setMileage(20000).build();

        Car second = new Car.CarBuilder()
                .setType("car").setModel("Toyota")
                .setPower(150).setMileage(20000).build();

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }
}