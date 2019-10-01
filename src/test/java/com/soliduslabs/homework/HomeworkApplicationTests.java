package com.soliduslabs.homework;

import com.google.common.hash.Hashing;
import com.soliduslabs.homework.Problem1.controller.MessagesController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = HomeworkApplication.class)

public class HomeworkApplicationTests {
    protected MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    private MessagesController messagesController;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testMessageConvertToHash256() throws Exception {
        String message = "Test controller";

        MvcResult result =  this.mockMvc.perform(
                post("/messages").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
						.content("{ \"message\": \"Test controller\" }")).andReturn();
        then(result.getResponse().getStatus()).isEqualTo(200);
        then(result.getResponse().getContentAsString()).isEqualTo(Hashing.sha256()
				.hashString(message, StandardCharsets.UTF_8)
				.toString());
	}
	@Test
	public void testGetMessageByGivingSHA256String() throws Exception {
    	//arrange
		String message = "Test controller number2";
		String hash = Hashing.sha256().hashString(message,StandardCharsets.UTF_8).toString();
		String url = "/messages/"+hash;
		this.mockMvc.perform(
				post("/messages").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
						.content("{ \"message\": \"Test controller number2\" }")).andExpect(status().isOk());

		//act + Assert
		 mockMvc.perform(get(url)).andExpect(status().isOk()).
				 andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8)).
				 andExpect(jsonPath("$.message").value(message));

	}

	@Test
	public void testGetMessageHashNotFound() throws Exception {
    	//arrange
		String message = "Test controller number2";
		String hash = Hashing.sha256().hashString(message,StandardCharsets.UTF_8).toString();
		String url = "/messages/"+hash;
		this.mockMvc.perform(
				post("/messages").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
						.content("{ \"message\": \"Test controller\" }")).andExpect(status().isOk());

		//Act + Assert
		mockMvc.perform(get(url)).andExpect(status().isNotFound());


	}

}
