
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
    void shouldDeleteAllDoctors() throws Exception{
        mockMvc.perform(delete("/api/doctors"))
                .andExpect(status().isOk());
                
    }
}


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
    void shouldDeleteAllPatients() throws Exception{
        mockMvc.perform(delete("/api/patients"))
                .andExpect(status().isOk());
                
    }

}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest{

    @MockBean
    private RoomRepository roomRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void shouldCreateARoom() throws Exception {
    	Room room = new Room("Dermatology");
    	
    	mockMvc.perform(post("/api/room").contentType(MediaType.APPLICATION_JSON)
    			.content(objectMapper.writeValueAsString(room)))
    			.andExpect(status().isCreated());
    }
    
    @Test
    void shouldGetNoRooms() throws Exception{
        List<Room> rooms = new ArrayList<Room>();
        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void shouldGetRooms() throws Exception{
    	
        Room room1 = new Room("Dermatology");
        Room room2 = new Room("Operations");
        Room room3 = new Room("Emergencies");

        List<Room> rooms = new ArrayList<Room>();
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);
        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk());                
    }
    
    @Test
    void shouldGetRoomById() throws Exception{

        Room room1 = new Room("Dermatology");
        //Room room2 = new Room("Operations");
        
        room1.getRoomName();

        Optional<Room> opt = Optional.of(room1);

        assertThat(opt).isPresent();
        assertThat(opt.get().getRoomName()).isEqualTo(room1.getRoomName());
        assertThat(room1.getRoomName()).isEqualTo("Dermatology");

        when(roomRepository.findByRoomName(room1.getRoomName())).thenReturn(opt);
        mockMvc.perform(get("/api/rooms/" + room1.getRoomName()))
                .andExpect(status().isOk());
    }
    
    @Test
    void shouldDeleteARoomById() throws Exception{
    	
    	Room room = new Room("Dermatology");
    	
        room.getRoomName();

        Optional<Room> opt = Optional.of(room);

        assertThat(opt).isPresent();
        assertThat(opt.get().getRoomName()).isEqualTo(room.getRoomName());
        assertThat(room.getRoomName()).isEqualTo("Dermatology");

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(opt);
        mockMvc.perform(delete("/api/rooms/" + room.getRoomName()))
                .andExpect(status().isOk());
                
    }
    
    @Test
    void shouldDeleteAllRooms() throws Exception{
        mockMvc.perform(delete("/api/rooms"))
                .andExpect(status().isOk());
                
    }

}
