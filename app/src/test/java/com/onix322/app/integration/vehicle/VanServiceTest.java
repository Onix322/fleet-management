package com.onix322.app.integration.vehicle;

import com.onix322.app.details.resource.model.imp.dao.VehicleDetails;
import com.onix322.app.image.resource.model.impl.dao.ImageProfile;
import com.onix322.app.vehicle.resource.model.impl.dao.Van;
import com.onix322.app.vehicle.resource.model.impl.dto.VanAddDto;
import com.onix322.app.vehicle.resource.model.impl.dto.VanReturnDto;
import com.onix322.app.vehicle.resource.model.impl.dto.VanSummaryDto;
import com.onix322.app.vehicle.resource.model.impl.dto.VanUpdateDto;
import com.onix322.app.vehicle.resource.model.impl.mapper.VanMapper;
import com.onix322.app.vehicle.resource.repository.VehicleRepository;
import com.onix322.app.vehicle.resource.service.VanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VanServiceTest {

    @Mock
    private VehicleRepository<Van> vanRepository;
    @Mock
    private VanMapper vanMapper;

    private VanService vanService;

    @BeforeEach
    public void setUp() {
        vanService = new VanService(vanRepository, vanMapper);
    }

    @Test
    public void testCreateVanSuccess() {

        // GIVEN
        VanAddDto givenAddDto = new VanAddDto();
        VanReturnDto expectedReturnDto = new VanReturnDto();

        List<ImageProfile> imageProfiles = List.of(new ImageProfile());
        VehicleDetails vehicleDetails = new VehicleDetails();

        Van mappedVan = new Van();
        mappedVan.setImages(imageProfiles);
        mappedVan.setDetails(vehicleDetails);
        Van createdVan = new Van();
        createdVan.setImages(imageProfiles);
        createdVan.setDetails(vehicleDetails);

        // WHEN
        when(vanMapper.toVan(givenAddDto)).thenReturn(mappedVan);
        when(vanRepository.save(mappedVan)).thenReturn(createdVan);
        when(vanMapper.toVanDto(createdVan)).thenReturn(expectedReturnDto);

        // THEN
        VanReturnDto result = vanService.create(givenAddDto);
        assertEquals(expectedReturnDto, result);
        verify(vanRepository).save(mappedVan);
        verify(vanMapper).toVanDto(createdVan);
        verify(vanMapper).toVan(givenAddDto);
    }

    @Test
    public void testCreateWhenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> vanService.create((VanAddDto) null));
        verify(vanMapper, never()).toVan((VanAddDto) null);
        verify(vanRepository, never()).save((Van) null);
        verify(vanMapper, never()).toVanDto((Van) null);
    }

    @Test
    public void testUpdateVanSuccess() {
        // GIVEN
        VanUpdateDto givenVan = new VanUpdateDto();
        Optional<Van> foundVan = Optional.of(new Van());
        givenVan.setId(UUID.randomUUID());
        VanReturnDto expected = new VanReturnDto();

        // WHEN
        when(vanRepository.find(givenVan.getId())).thenReturn(foundVan);
        when(vanMapper.updateVan(givenVan, foundVan.get())).thenReturn(foundVan.get());
        when(vanRepository.save(foundVan.get())).thenReturn(foundVan.get());
        when(vanMapper.toVanDto(foundVan.get())).thenReturn(expected);

        // THEN
        VanReturnDto result = vanService.update(givenVan.getId(), givenVan);
        assertEquals(expected, result);
        verify(vanRepository).find(givenVan.getId());
        verify(vanMapper).updateVan(givenVan, foundVan.get());
        verify(vanRepository).save(foundVan.get());
        verify(vanMapper).toVanDto(foundVan.get());
    }

    @Test
    public void testUpdateWhenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> vanService.update((UUID) null, (Van) null));
        verify(vanMapper, never()).updateVan(null, (Van) null);
        verify(vanRepository, never()).save((Van) null);
        verify(vanMapper, never()).toVanDto((Van) null);
    }

    @Test
    public void testGetAllVansSuccess() {
        // GIVEN
        Van givenVan = new Van();
        List<Van> givenListVan = List.of(givenVan);

        VanSummaryDto expectedVanSummaryDto = new VanSummaryDto();
        List<VanSummaryDto> expectedList = List.of(expectedVanSummaryDto);

        when(vanRepository.findAll()).thenReturn(givenListVan);
        when(vanMapper.toVanSummaryDto(givenVan)).thenReturn(expectedVanSummaryDto);

        // WHEN
        List<VanSummaryDto> result = vanService.getAllVans();
        assertEquals(expectedList, result);
        verify(vanRepository).findAll();
        verify(vanMapper, atLeastOnce()).toVanSummaryDto(givenVan);
    }
}
