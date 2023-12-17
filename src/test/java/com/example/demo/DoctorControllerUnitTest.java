package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controllers.DoctorController;
import com.example.demo.entities.Appointment;
import com.example.demo.entities.Doctor;
import com.example.demo.entities.Patient;
import com.example.demo.entities.Room;
import com.example.demo.repositories.DoctorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest{

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

	/*
	 * @Test void this_is_a_test(){ // DELETE ME assertThat(true).isEqualTo(false);
	 * }
	 */
    
    
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
    void shouldDeleteAllDoctors() throws Exception{
        mockMvc.perform(delete("/api/doctors"))
                .andExpect(status().isOk());
                
    }
    
}
