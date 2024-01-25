
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



/** TODO
 * Implement all the unit test in its corresponding class.
 * Make sure to be as exhaustive as possible. Coverage is checked ;)
 */

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest{

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void shouldCreateADoctor() throws Exception {
        
    	Doctor doctor = new Doctor ("John", "Doe", 28, "j.doe@hospital.accwe");

        mockMvc.perform(post("/api/doctor").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isCreated());
                
    }

    @Test
    void shouldGetNoDoctors() throws Exception{
        List<Doctor> doctors = new ArrayList<Doctor>();
        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetDoctors() throws Exception{
        Doctor doctor = new Doctor ("John", "Doe", 28, "j.doe@hospital.accwe");
        Doctor doctor2 = new Doctor ("Joe", "Coleman", 24, "j.coleman@hospital.accwe");
        Doctor doctor3 = new Doctor ("Maria", "Gonzales", 38, "m.gonzales@hospital.accwe");


        List<Doctor> doctors = new ArrayList<Doctor>();
        doctors.add(doctor);
        doctors.add(doctor2);
        doctors.add(doctor3);
        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk());                
    }
    
    @Test
    void shouldGetDoctorById() throws Exception{

        Doctor doctor = new Doctor ("Juana", "Marry", 24, "p.amalia@hospital.accwe");

        doctor.setId(1);

        Optional<Doctor> opt = Optional.of(doctor);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(opt);
        mockMvc.perform(get("/api/doctors/" + doctor.getId()))
                .andExpect(status().isOk());
    }
    
    @Test
    void shouldNotGetAnyDoctorById() throws Exception{
        long id = 31;
        mockMvc.perform(get("/api/doctors/" + id))
                .andExpect(status().isNotFound());
                
    }
    
    @Test
    void shouldDeleteADoctorById() throws Exception{
        Doctor doctor = new Doctor ("Maria", "Isabela", 29, "m.isabela@hospital.accwe");

        doctor.setId(1);

        Optional<Doctor> opt = Optional.of(doctor);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(opt);
        mockMvc.perform(delete("/api/doctors/" + doctor.getId()))
                .andExpect(status().isOk());              
    }
    
    @Test
    void shouldNotDeleteDoctor() throws Exception{
        long id = 2;
        mockMvc.perform(delete("/api/doctors/" + id))
                .andExpect(status().isNotFound());
                
    }
    
    @Test
    void shouldDeleteAllDoctors() throws Exception{
        mockMvc.perform(delete("/api/doctors"))
                .andExpect(status().isOk());
                
    }
}


