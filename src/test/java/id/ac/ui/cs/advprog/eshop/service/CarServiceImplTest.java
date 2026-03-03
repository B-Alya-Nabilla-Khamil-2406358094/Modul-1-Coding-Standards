package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("car-123");
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);
    }

    @Test
    void testCreate() {
        when(carRepository.create(any(Car.class))).thenReturn(car);

        Car result = carService.create(car);

        assertNotNull(result);
        assertEquals("Toyota", result.getCarName());
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAll() {
        List<Car> carList = Arrays.asList(car);
        Iterator<Car> iterator = carList.iterator();
        when(carRepository.findAll()).thenReturn(iterator);

        List<Car> result = carService.findAll();

        assertEquals(1, result.size());
        assertEquals("Toyota", result.get(0).getCarName());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindAllEmpty() {
        when(carRepository.findAll()).thenReturn(List.<Car>of().iterator());

        List<Car> result = carService.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindById() {
        when(carRepository.findById("car-123")).thenReturn(car);

        Car result = carService.findById("car-123");

        assertNotNull(result);
        assertEquals("car-123", result.getCarId());
        verify(carRepository, times(1)).findById("car-123");
    }

    @Test
    void testFindByIdNotFound() {
        when(carRepository.findById("not-exist")).thenReturn(null);

        Car result = carService.findById("not-exist");

        assertNull(result);
    }

    @Test
    void testUpdate() {
        carService.update("car-123", car);

        verify(carRepository, times(1)).update("car-123", car);
    }

    @Test
    void testDeleteCarById() {
        doNothing().when(carRepository).delete("car-123");

        carService.deleteCarById("car-123");

        verify(carRepository, times(1)).delete("car-123");
    }
}