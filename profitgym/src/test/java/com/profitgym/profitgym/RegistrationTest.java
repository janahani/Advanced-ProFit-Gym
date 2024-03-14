package com.profitgym.profitgym;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.profitgym.profitgym.controllers.IndexController;
import com.profitgym.profitgym.models.Client;
import com.profitgym.profitgym.repositories.ClientRepository;

@SpringBootTest
public class RegistrationTest {
    
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private IndexController indexController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   @Test
public void testSaveClient() {
    // Mock client object
    Client client = new Client();
    client.setEmail("janahani@gmail.com");
    client.setPassword("password123");
    when(clientRepository.save(client)).thenReturn(client);
    ModelAndView modelAndView = indexController.saveClient(client);
    verify(clientRepository).save(client);

    // Verify that the returned ModelAndView is not null
    assertNotNull(modelAndView);
    assertEquals("redirect:/login", modelAndView.getViewName());
}
}
