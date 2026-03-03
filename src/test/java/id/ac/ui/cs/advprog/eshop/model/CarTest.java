package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    void testCarGetterSetter() {
        Car car = new Car();
        car.setCarId("car-001");
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(10);

        assertEquals("car-001", car.getCarId());
        assertEquals("Toyota", car.getCarName());
        assertEquals("Red", car.getCarColor());
        assertEquals(10, car.getCarQuantity());
    }

    @Test
    void testCarDefaultValues() {
        Car car = new Car();

        assertNull(car.getCarId());
        assertNull(car.getCarName());
        assertNull(car.getCarColor());
        assertEquals(0, car.getCarQuantity());
    }
}
