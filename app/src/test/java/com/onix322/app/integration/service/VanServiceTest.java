package com.onix322.app.integration.service;

import com.onix322.app.details.resource.model.imp.dao.VehicleDetails;
import com.onix322.app.image.resource.model.impl.dao.ImageProfile;
import com.onix322.app.vehicle.resource.model.impl.dao.Van;
import com.onix322.app.vehicle.resource.repository.VehicleRepository;
import com.onix322.app.vehicle.resource.service.VehicleService;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VanServiceTest {

    private VehicleService<Van> vanService;
    @Mock
    private VehicleRepository<Van> vanRepository;

    @BeforeEach
    public void setUp() {
        this.vanService = new VehicleService<>(vanRepository);
    }

    @Test
    public void testCreateMethod() {

        // GIVEN
        ImageProfile image1 = new ImageProfile();
        ImageProfile image2 = new ImageProfile();
        ImageProfile image3 = new ImageProfile();
        List<ImageProfile> images = List.of(image1, image2, image3);

        VehicleDetails details = new VehicleDetails();

        Van van = new Van();
        van.setId(UUID.randomUUID());
        van.setImages(images);
        van.setDetails(details);

        when(vanRepository.save(van)).thenReturn(van);

        // WHEN
        Van actual = vanService.create(van);

        // THEN
        assertNotNull(actual);
        assertDoesNotThrow(() -> vanService.create(van));
        assertEquals(van, actual);
        verify(vanRepository, atLeastOnce()).save(van);
    }

    @Test
    public void testCreateMethodWhenRepositoryThrowsException() {

        // GIVEN
        ImageProfile image1 = new ImageProfile();
        ImageProfile image2 = new ImageProfile();
        ImageProfile image3 = new ImageProfile();
        List<ImageProfile> images = List.of(image1, image2, image3);

        VehicleDetails details = new VehicleDetails();

        Van van = new Van();
        van.setId(UUID.randomUUID());
        van.setImages(images);
        van.setDetails(details);

        when(vanRepository.save(van)).thenThrow(new IllegalArgumentException("Vehicle not found"));

        // WHEN / THEN
        assertThrows(IllegalArgumentException.class, () -> vanService.create(van));
        verify(vanRepository).save(van);
    }

    @Test
    public void testGetAllMethodWithEmptyList() {

        // GIVEN
        List<Van> vans = List.of();

        when(vanRepository.findAll()).thenReturn(vans);

        // WHEN
        List<Van> actual = vanService.getAll();

        // THEN
        assertEquals(vans, actual);
        verify(vanRepository).findAll();
    }

    @Test
    public void testGetAllMethodWithVans() {

        // GIVEN
        List<Van> vans = List.of(new Van(), new Van(), new Van());

        when(vanRepository.findAll()).thenReturn(vans);

        // WHEN
        List<Van> actual = vanService.getAll();

        // THEN
        assertEquals(vans, actual);
        verify(vanRepository).findAll();
    }

    @Test
    public void testGetByIdMethod() {

        // GIVEN
        Van van = new Van();
        UUID id = UUID.randomUUID();
        van.setId(id);
        Optional<Van> expected = Optional.of(van);

        when(vanRepository.find(id)).thenReturn(expected);

        // WHEN
        Van actual = vanService.getById(id);

        // THEN
        assertEquals(van, actual);
        verify(vanRepository, atLeastOnce()).find(id);
    }

    @Test
    public void testGetByIdMethodWhenRepositoryThrowsNotFoundException() {

        // GIVEN
        UUID id = UUID.randomUUID();

        when(vanRepository.find(id)).thenThrow(new NotFoundException("Vehicle not found"));

        // WHEN / THEN
        assertThrows(NotFoundException.class, () -> vanService.getById(id));
        verify(vanRepository).find(id);
    }

    @Test
    public void testDeleteMethodReturnsTrue() {

        // GIVEN
        UUID givenId = UUID.randomUUID();

        when(vanRepository.delete(givenId)).thenReturn(Boolean.TRUE);

        // WHEN
        Boolean actual = vanService.delete(givenId);

        // THEN
        assertEquals(Boolean.TRUE, actual);
        verify(vanRepository).delete(givenId);
    }

    @Test
    public void testDeleteMethodReturnsFalse() {

        // GIVEN
        UUID givenId = UUID.randomUUID();

        when(vanRepository.delete(givenId)).thenReturn(Boolean.FALSE);

        // WHEN
        Boolean actual = vanService.delete(givenId);

        // THEN
        assertEquals(Boolean.FALSE, actual);
        verify(vanRepository).delete(givenId);
    }

    @Test
    public void testDeleteMethodWithNullId() {

        // GIVEN
        when(vanRepository.delete(null)).thenThrow(new IllegalArgumentException("Van id cannot be null"));

        // WHEN / THEN
        assertThrows(IllegalArgumentException.class, () -> vanService.delete(null));
        verify(vanRepository).delete(null);
    }

    @Test
    public void testDeleteMethodWhenRepositoryThrowsException() {

        // GIVEN
        UUID givenId = UUID.randomUUID();

        when(vanRepository.delete(givenId)).thenThrow(new RuntimeException("Internal server error"));

        // WHEN / THEN
        assertThrows(RuntimeException.class, () -> vanService.delete(givenId));
        verify(vanRepository).delete(givenId);
    }
}
