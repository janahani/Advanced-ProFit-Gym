package com.profitgym.profitgym;

import com.profitgym.profitgym.controllers.AdminController;
import com.profitgym.profitgym.models.Client;
import com.profitgym.profitgym.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AddClientAdminSideTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AdminController adminController;

    @Mock
    private JavaMailSender emailSender;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        adminController = new AdminController(clientRepository,emailSender);
    }

    @Test
    public void testSaveClient_EmailAlreadyExists() {
        Client client = new Client();
        client.setEmail("test@example.com");
        when(clientRepository.findByEmail("test@example.com")).thenReturn(client);

        ModelAndView result = adminController.saveClient(client);

        assertEquals("redirect:/admindashboard/addclient?EmailAlreadyExists", result.getViewName());
    }

    @Test
    public void testSaveClient_Success() {
        Client client = new Client();
        client.setEmail("newclient@example.com");
        client.setFirstName("John");

        adminController.sendEmail(client.getEmail(),"Test Subject","Test body");

        Mockito.verify(emailSender).send(Mockito.any(SimpleMailMessage.class));

        ModelAndView result = adminController.saveClient(client);

        assertEquals("redirect:/admindashboard/addclient", result.getViewName());
    }
}
