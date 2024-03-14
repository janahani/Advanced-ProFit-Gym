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

    //login successful
    @Test
    public void testLoginProcessSuccess() {
        Client client = new Client();
        client.setEmail("janahani@gmail.com");
        client.setPassword(BCrypt.hashpw("jana123", BCrypt.gensalt(12)));
        when(clientRepository.findByEmail("janahani@gmail.com")).thenReturn(client);

        RedirectView result = indexController.loginProcess("janahani@gmail.com", "jana123", session);

        assertEquals("/user/profile", result.getUrl());
        //confirm user set in session
        verify(session).setAttribute(eq("loggedInUser"), any());
    }

    //login with incorrect password
    @Test
    public void testLoginProcessIncorrectPassword() {
        Client client = new Client();
        client.setEmail("janahani@gmail.com");
        client.setPassword(BCrypt.hashpw("jana123", BCrypt.gensalt(12)));
        when(clientRepository.findByEmail("janahani@gmail.com")).thenReturn(client);

        RedirectView result = indexController.loginProcess("janahani@gmail.com", "janahani123", session);

        assertEquals("/login?error=wrongPassword", result.getUrl());
        // confirm that no user session is set
        verify(session, never()).setAttribute(eq("loggedInUser"), any());
    }

    //login with non-existing email
    @Test
    public void testLoginProcessUserNotFound() {
        when(clientRepository.findByEmail("malakhelmy@example.com")).thenReturn(null);

        RedirectView result = indexController.loginProcess("malakhelmy@example.com", "jana123", session);

        assertEquals("/login?error=userNotFound", result.getUrl());
        // confirm that no user session is set
        verify(session, never()).setAttribute(eq("loggedInUser"), any());
    }
}
