package com.profitgym.profitgym;

import com.profitgym.profitgym.controllers.AdminController;
import com.profitgym.profitgym.models.Client;
import com.profitgym.profitgym.models.Employee;
import com.profitgym.profitgym.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AddEmployeeAdminSideTest {

    @Mock
    private AdminController adminController;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private JavaMailSender emailSender;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        adminController = new AdminController(employeeRepository,emailSender);
    }

    @Test
    public void testSaveEmployee_EmailAlreadyExists() {
        Employee employee = new Employee();
        employee.setEmail("existingemployee@example.com");

        when(employeeRepository.findByEmail("existingemployee@example.com")).thenReturn(new Employee());

        ModelAndView result = adminController.saveEmployee(employee, 1);

        assertEquals("redirect:/admindashboard/addemployee?EmailAlreadyExists", result.getViewName());

        verify(employeeRepository, never()).save(employee);
    }

    @Test
    public void testSaveEmployee_Success() {
        Employee employee = new Employee();
        employee.setEmail("newemployee@example.com");
        employee.setName("John");

        adminController.sendEmail(employee.getEmail(),"Test Subject","Test body");

        Mockito.verify(emailSender).send(Mockito.any(SimpleMailMessage.class));

        ModelAndView result = adminController.saveEmployee(employee,1);

        assertEquals("redirect:/admindashboard/addemployee", result.getViewName());
    }
}

