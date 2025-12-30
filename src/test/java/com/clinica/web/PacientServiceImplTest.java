package com.clinica.web;
import com.clinica.web.model.Pacient;
import com.clinica.web.repository.PacientJdbcRepository;
import com.clinica.web.service.impl.PacientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacientServiceImplTest {

    @Mock
    private PacientJdbcRepository pacientRepository;

    @InjectMocks
    private PacientServiceImpl pacientService;

    @Test
    void findAll_returnsPacienti() {
        Pacient p = new Pacient();
        p.setNume("Popescu");

        when(pacientRepository.findAll(true, "Nume"))
                .thenReturn(List.of(p));

        List<Pacient> result = pacientService.findAll(true, "Nume");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNume()).isEqualTo("Popescu");

        verify(pacientRepository).findAll(true, "Nume");
    }

    @Test
    void search_returnsFilteredPacienti() {
        Pacient p = new Pacient();
        p.setNume("Ionescu");

        when(pacientRepository.search("Nume", "Ion", true))
                .thenReturn(List.of(p));

        List<Pacient> result = pacientService.search("Nume", "Ion", true);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNume()).isEqualTo("Ionescu");
    }

    @Test
    void save_callsRepositorySave() {
        Pacient p = new Pacient();
        p.setNume("Maria");

        when(pacientRepository.save(p)).thenReturn(p);

        Pacient saved = pacientService.save(p);

        assertThat(saved).isNotNull();
        verify(pacientRepository).save(p);
    }

    @Test
    void deleteById_callsRepositoryDelete() {
        pacientService.deleteById(1L);

        verify(pacientRepository).deleteById(1L);
    }

    @Test
    void update_callsRepositoryUpdate() {
        Pacient p = new Pacient();
        p.setPacientID(1);
        p.setNume("Update");

        when(pacientRepository.update(p)).thenReturn(p);

        Pacient updated = pacientService.update(p);

        assertThat(updated.getNume()).isEqualTo("Update");
        verify(pacientRepository).update(p);
    }

    @Test
    void findById_returnsPacient() {
        Pacient p = new Pacient();
        p.setPacientID(1);

        when(pacientRepository.findById(1L)).thenReturn(p);

        Pacient result = pacientService.findById(1L);

        assertThat(result).isNotNull();
        verify(pacientRepository).findById(1L);
    }
}
