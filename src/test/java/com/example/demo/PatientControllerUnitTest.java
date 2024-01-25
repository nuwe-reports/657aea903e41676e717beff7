package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.format.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(PatientController.class)
class PatientControllerUnitTest{

    @MockBean
    private PatientRepository patientRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    
    @Test
    void shouldCreateAPatient() throws Exception {
        
    	Patient patient = new Patient ("John", "Doe", 28, "j.doe@hospital.accwe");

        mockMvc.perform(post("/api/patient").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated());
                
    }

    @Test
    void shouldGetNoPatients() throws Exception{
        List<Patient> patients = new ArrayList<Patient>();
        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetPatients() throws Exception{
    	
        Patient patient = new Patient ("John", "Doe", 28, "j.doe@hospital.accwe");
        Patient patient2 = new Patient ("Joe", "Perez", 28, "j.perez@hospital.accwe");
        Patient patient3 = new Patient ("Mary", "Goonzales", 28, "j.gonzales@hospital.accwe");

        List<Patient> patients = new ArrayList<Patient>();
        patients.add(patient);
        patients.add(patient2);
        patients.add(patient3);
        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk());                
    }
    
    @Test
    void shouldGetPatientById() throws Exception{

        Patient patient = new Patient ("Juana", "Mary", 24, "j.mary@hospital.accwe");

        patient.setId(1);

        Optional<Patient> opt = Optional.of(patient);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(opt);
        mockMvc.perform(get("/api/patients/" + patient.getId()))
                .andExpect(status().isOk());
    }
    
    @Test
    void shouldNotGetAnyPatientById() throws Exception{
        long id = 21;
        mockMvc.perform(get("/api/patients/" + id))
                .andExpect(status().isNotFound());
                
    }
    
    @Test
    void shouldDeleteAPatientById() throws Exception{
        Patient patient = new Patient ("Maria", "Isabela", 29, "m.isabela@hospital.accwe");

        patient.setId(1);

        Optional<Patient> opt = Optional.of(patient);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(opt);
        mockMvc.perform(delete("/api/patients/" + patient.getId()))
                .andExpect(status().isOk());
                
    }
    
    @Test
    void shouldNotDeletePatient() throws Exception{
        long id = 2;
        mockMvc.perform(delete("/api/patients/" + id))
                .andExpect(status().isNotFound());
                
    }
    
    @Test
    void shouldDeleteAllPatients() throws Exception{
        mockMvc.perform(delete("/api/patients"))
                .andExpect(status().isOk());
                
    }

}