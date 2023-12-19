package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import javax.persistence.PostPersist;

import org.hibernate.event.spi.PostCollectionRecreateEvent;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.controllers.RoomController;
import com.example.demo.entities.Patient;
import com.example.demo.entities.Room;
import com.example.demo.repositories.RoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

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