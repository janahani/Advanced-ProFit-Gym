package com.profitgym.profitgym;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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
public class LoginTest {
    	
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
        Client client = new Client();
        client.setEmail("test@example.com");
        client.setPassword(BCrypt.hashpw("password123", BCrypt.gensalt(12)));
        when(clientRepository.findByEmail("test@example.com")).thenReturn(client);

        RedirectView result = indexController.loginProcess("test@example.com", "password123", session);

        assertEquals("/user/profile", result.getUrl());
        verify(session).setAttribute(eq("loggedInUser"), any());
    }

        @Test
    public void testLoginProcessIncorrectPassword() {
        Client client = new Client();
        client.setEmail("test@example.com");
        // Set a different password than the one expected in the test
        client.setPassword(BCrypt.hashpw("differentpassword", BCrypt.gensalt(12)));
        when(clientRepository.findByEmail("test@example.com")).thenReturn(client);

        RedirectView result = indexController.loginProcess("test@example.com", "password123", session);

        // Assert that the user is redirected to the login page or an error page
        assertEquals("/login?error=wrongPassword", result.getUrl());
        // Optionally, verify that no user session is set
        verify(session, never()).setAttribute(eq("loggedInUser"), any());
    }

    @Test
    public void testLoginProcessUserNotFound() {
        // Simulate scenario where user is not found in the database
        when(clientRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        RedirectView result = indexController.loginProcess("nonexistent@example.com", "password123", session);

        // Assert that the user is redirected to the login page or an error page
        assertEquals("/login?error=userNotFound", result.getUrl());
        // Optionally, verify that no user session is set
        verify(session, never()).setAttribute(eq("loggedInUser"), any());
    }
}
