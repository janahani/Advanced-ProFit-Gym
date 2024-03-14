package com.profitgym.profitgym;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.view.RedirectView;

import com.profitgym.profitgym.controllers.IndexController;
import com.profitgym.profitgym.repositories.ClientRepository;
import com.profitgym.profitgym.models.Client;

import jakarta.servlet.http.HttpSession;

@SpringBootTest
public class LoginTests {
    	
	private IndexController indexController;
    private ClientRepository clientRepository;
    private HttpSession session;

    @BeforeEach
    public void setUp() {
        clientRepository = mock(ClientRepository.class);
        session = mock(HttpSession.class);
        indexController = new IndexController(clientRepository);
    }

    @Test
    public void testLoginProcessSuccess() {
		System.out.println("hi");
        Client client = new Client();
        client.setEmail("test@example.com");
        client.setPassword(BCrypt.hashpw("password123", BCrypt.gensalt(12)));
        when(clientRepository.findByEmail("test@example.com")).thenReturn(client);

        RedirectView result = indexController.loginProcess("test@example.com", "password123", session);

        assertEquals("/user/profile", result.getUrl());
        verify(session).setAttribute(eq("loggedInUser"), any());
    }
}
