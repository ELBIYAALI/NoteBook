package org.sid;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import services.NoteBookController;

@RunWith(SpringRunner.class)
@WebMvcTest(value = NoteBookController.class, secure = false)
public class NoteBookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	String exampleJson1 = "{\"code\":\"%python x=2\"}";
	String exampleJson2 = "{\"code\":\"%python print(x)\"}";

	@Test
	public void testGetPythonCode() throws Exception {

		MockHttpSession mocksession = new MockHttpSession();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/execute").session(mocksession)
				.accept(MediaType.APPLICATION_JSON).content(exampleJson1).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		System.out.println("-HttpStatus.CREATED.value()-" + HttpStatus.CREATED.value() + " response.getStatus() "
				+ response.getStatus());
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("", response.getContentAsString());

		RequestBuilder requestBuilder2 = MockMvcRequestBuilders.post("/execute").session(mocksession)
				.accept(MediaType.APPLICATION_JSON).content(exampleJson2).contentType(MediaType.APPLICATION_JSON);

		MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();
		MockHttpServletResponse response2 = result2.getResponse();

		assertEquals(HttpStatus.OK.value(), response2.getStatus());
		assertEquals("2", response2.getContentAsString());

	}
}
