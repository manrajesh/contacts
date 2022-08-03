package com.kn.contact.contacts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = {ContactsApplication.class})
@AutoConfigureMockMvc
public class ContactsApplicationTests {

	protected ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

	@Autowired
	private WebApplicationContext wac;

	protected MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
}
