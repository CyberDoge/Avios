package io.ssau.team.Avios;

import io.ssau.team.Avios.config.SecurityConfig;
import io.ssau.team.Avios.param.Header;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AviosApplication.class, SecurityConfig.class})
@WebAppConfiguration
public class LoginTest {

    private MockMvc mvc;

    @Autowired
    private
    WebApplicationContext webApplicationContext;


    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }


    @Test
    public void testLogin() throws Exception {
        String uri = "/login";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).header(Header.TOKEN, "aaa")).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

}