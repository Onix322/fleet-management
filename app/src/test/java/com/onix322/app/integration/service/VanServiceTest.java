package com.onix322.app.integration.service;

import com.onix322.app.details.resource.model.imp.VehicleDetails;
import com.onix322.app.image.resource.model.impl.ImageProfile;
import com.onix322.app.vehicle.resource.model.impl.Van;
import com.onix322.app.vehicle.resource.repository.VanRepository;
import com.onix322.app.vehicle.resource.service.VanService;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VanServiceTest {

    private VanService vanService;
    @Mock
    private VanRepository vanRepository;
    private Van van;

    @BeforeEach
    public void setUp() {
        this.vanService = new VanService(vanRepository);

    }

    @ParameterizedTest
    @MethodSource("provideCreateMethodArguments")
    public void testCreateMethod(Throwable throwable) {

        // GIVEN
        ImageProfile image1 = new ImageProfile();
        ImageProfile image2 = new ImageProfile();
        ImageProfile image3 = new ImageProfile();
        List<ImageProfile> images = List.of(image1, image2, image3);

        VehicleDetails details = new VehicleDetails();

        van = new Van();
        van.setId(UUID.randomUUID());
        van.setImages(images);
        van.setDetails(details);

        // WHEN
        if (throwable != null) {
            when(vanRepository.save(this.van)).thenThrow(throwable);

            // THEN
            assertThrows(throwable.getClass(), () -> vanService.create(this.van));
            return;
        }

        // WHEN
        when(vanRepository.save(this.van)).thenReturn(this.van);

        // THEN
        assertNotNull(vanService.create(this.van));
        assertDoesNotThrow(() -> vanService.create(this.van));
        assertEquals(this.van, vanService.create(this.van));

        verify(vanRepository, atLeastOnce()).save(this.van);
    }

    static Stream<Arguments> provideCreateMethodArguments() {
        return Stream.of(
                Arguments.arguments((Exception) null), // testing success scenario
                Arguments.arguments(new IllegalArgumentException("Vehicle not found")) // testing failure scenario
        );
    }

    @ParameterizedTest
    @MethodSource("provideGetAllMethodArguments")
    public void testGetAll(List<Van> vans) {
        // GIVEN

        // WHEN
        when(vanRepository.findAll()).thenReturn(vans);

        // THEN
        assertEquals(vans, vanService.getAll());
    }

    static Stream<Arguments> provideGetAllMethodArguments() {
        return Stream.of(
                Arguments.arguments(List.of()), // success scenario
                Arguments.arguments(List.of(new Van(), new Van(), new Van())) // success scenario
        );
    }

    @ParameterizedTest
    @MethodSource("provideGetByIdMethodArguments")
    public void testGetByIdMethod(Throwable throwable) {

        //GIVEN
        Van van = new Van();
        UUID id = UUID.randomUUID();
        van.setId(id);
        Optional<Van> expected = Optional.of(van);

        // WHEN
        if (throwable != null) {
            when(vanRepository.find(id)).thenThrow(throwable);

            // THEN
            assertThrows(throwable.getClass(), () -> vanService.getById(id));
            return;
        }

        // WHEN
        when(vanRepository.find(id)).thenReturn(expected);

        // THEN
        assertEquals(van, vanService.getById(id));
        verify(vanRepository, atLeastOnce()).find(id);
    }

    static Stream<Arguments> provideGetByIdMethodArguments() {
        return Stream.of(
                Arguments.arguments((Exception) null), // testing success scenario
                Arguments.arguments(new NotFoundException("Vehicle not found")) // testing failure scenario
        );
    }

    @ParameterizedTest
    @MethodSource("provideUpdateMethodArguments")
    public void testUpdateMethod(Throwable throwable) {

        // GIVEN
        Van givenVan = new Van();
        Van expected = new Van();

        UUID givenId = UUID.randomUUID();

        // WHEN
        if (throwable != null) {
            when(vanRepository.update(givenVan)).thenThrow(throwable);

            //THEN
            assertThrows(throwable.getClass(), () -> vanService.update(givenId, givenVan));
            return;
        }

        // WHEN
        givenVan.setId(givenId);
        when(vanRepository.update(givenVan)).thenReturn(expected);

        // THEN
        assertEquals(expected, vanService.update(givenId, givenVan));
        verify(vanRepository).update(givenVan);
    }

    static Stream<Arguments> provideUpdateMethodArguments() {
        return Stream.of(
                Arguments.arguments((Exception) null),
                Arguments.arguments(new RuntimeException("Internal server error"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideDeleteMethodArguments")
    public void testDeleteMethod(UUID givenId, Boolean expected, Throwable throwable) {

        // WHEN
        if (throwable != null) {
            when(vanRepository.delete(givenId)).thenThrow(throwable);
            assertThrows(throwable.getClass(), () -> vanService.delete(givenId));
            return;
        }

        when(vanRepository.delete(givenId)).thenReturn(expected);
        assertEquals(expected, vanService.delete(givenId));

        verify(vanRepository).delete(givenId);
        // THEN
    }

    static Stream<Arguments> provideDeleteMethodArguments() {
        return Stream.of(
                Arguments.arguments(UUID.randomUUID(), Boolean.TRUE, (Exception) null),
                Arguments.arguments(UUID.randomUUID(), Boolean.FALSE, (Exception) null),
                Arguments.arguments(null, null, new IllegalArgumentException("Van id cannot be null")),
                Arguments.arguments(null, null, new RuntimeException("Internal server error"))
        );
    }
}

