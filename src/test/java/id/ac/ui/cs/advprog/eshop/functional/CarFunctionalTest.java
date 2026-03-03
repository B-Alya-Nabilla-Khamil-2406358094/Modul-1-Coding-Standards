package id.ac.ui.cs.advprog.eshop.functional;

import id.ac.ui.cs.advprog.eshop.controller.CarController;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.hamcrest.Matchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
class CarFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarService carService;

    // ==================== CarList.html ====================

    @Test
    void carListPage_shouldRenderCarListTemplate() throws Exception {
        when(carService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("CarList"));
    }

    @Test
    void carListPage_shouldShowAllCarsInTable() throws Exception {
        Car car1 = new Car();
        car1.setCarId("id-1");
        car1.setCarName("Toyota");
        car1.setCarColor("Red");
        car1.setCarQuantity(5);

        Car car2 = new Car();
        car2.setCarId("id-2");
        car2.setCarName("Honda");
        car2.setCarColor("Blue");
        car2.setCarQuantity(3);

        when(carService.findAll()).thenReturn(Arrays.asList(car1, car2));

        mockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("cars", Matchers.hasSize(2)));
    }

    @Test
    void carListPage_shouldShowEmptyTableWhenNoCars() throws Exception {
        when(carService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("cars", Matchers.hasSize(0)));
    }

    // ==================== CreateCar.html ====================

    @Test
    void createCarPage_shouldRenderCreateCarTemplate() throws Exception {
        mockMvc.perform(get("/car/createCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateCar"));
    }

    @Test
    void createCarPage_shouldHaveCarModelAttribute() throws Exception {
        mockMvc.perform(get("/car/createCar"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void createCarPost_shouldRedirectToListCar() throws Exception {
        mockMvc.perform(post("/car/createCar")
                        .param("carName", "Toyota")
                        .param("carColor", "Red")
                        .param("carQuantity", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car/listCar")); // fix
    }

    // ==================== EditCar.html ====================

    @Test
    void editCarPage_shouldRenderEditCarTemplate() throws Exception {
        Car car = new Car();
        car.setCarId("id-1");
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);

        when(carService.findById("id-1")).thenReturn(car);

        mockMvc.perform(get("/car/editCar/{carId}", "id-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("EditCar"));
    }

    @Test
    void editCarPage_shouldHaveCarModelAttribute() throws Exception {
        Car car = new Car();
        car.setCarId("id-1");
        car.setCarName("Toyota");
        car.setCarColor("Red");
        car.setCarQuantity(5);

        when(carService.findById("id-1")).thenReturn(car);

        mockMvc.perform(get("/car/editCar/{carId}", "id-1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("car", car));
    }

    @Test
    void editCarPost_shouldRedirectToListCar() throws Exception {
        mockMvc.perform(post("/car/editCar")
                        .param("carId", "id-1")
                        .param("carName", "Honda")
                        .param("carColor", "Blue")
                        .param("carQuantity", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car/listCar")); // fix
    }

    // ==================== deleteCar ====================

    @Test
    void deleteCar_shouldRedirectToListCar() throws Exception {
        mockMvc.perform(post("/car/deleteCar")
                        .param("carId", "id-1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car/listCar")); // fix
    }
}