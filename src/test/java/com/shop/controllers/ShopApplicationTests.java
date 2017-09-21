package com.shop.controllers;

import com.shop.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ShopApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

	@MockBean
    private RegisterController registerController;

	@MockBean
    private BookService bookService;

	@Before
    public void setup(){
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

	@Test
	public void contextLoads() throws Exception {
	    mockMvc.perform(get("/register")).andExpect(status().isMethodNotAllowed());
	}

	@Test
    public void contextLoads2() throws Exception {
        mockMvc.perform(get("/test")).andExpect(status().isOk());
    }

}
