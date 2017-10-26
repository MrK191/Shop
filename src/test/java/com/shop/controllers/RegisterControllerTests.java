package com.shop.controllers;

import com.google.gson.Gson;
import com.shop.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTests {

    @Autowired
    private MockMvc mockMvc;


	@Test
	public void contextLoads() throws Exception {
	    mockMvc.perform(get("/register")).andExpect(status().isMethodNotAllowed());
	}

    @Test
    public void registerBlankUser() throws Exception {
        Gson gson = new Gson();
        User blankUser = new User();
        String json = gson.toJson(blankUser);

        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(json))
                .andReturn().getResolvedException();
    }

}
