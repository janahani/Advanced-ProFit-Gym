package com.profitgym.profitgym;

import com.profitgym.profitgym.controllers.IndexController;
import com.profitgym.profitgym.models.Employee;
import com.profitgym.profitgym.models.Authority;
import com.profitgym.profitgym.repositories.EmployeeRepository;
import com.profitgym.profitgym.repositories.EmployeeAuthoritiesRepository;
import com.profitgym.profitgym.repositories.AuthorityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.view.RedirectView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class LoginEmployeeTest {

    private IndexController indexController;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeAuthoritiesRepository employeeAuthoritiesRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    private MockHttpSession session;
    private Employee dbEmp;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        session = new MockHttpSession();
        indexController = new IndexController(employeeRepository, employeeAuthoritiesRepository, authorityRepository);
    }

    @Test
    public void testLoginEmpProcess_Success() {
        dbEmp = new Employee();
        dbEmp.setEmail("test@example.com");
        dbEmp.setPassword(BCrypt.hashpw("password123", BCrypt.gensalt(12)));
        dbEmp.setJobTitle(1); 
    
        when(employeeRepository.findByEmail("test@example.com")).thenReturn(dbEmp);
    
        RedirectView result = indexController.loginEmpProcess("test@example.com", "password123", session);

        assertEquals("/admindashboard", result.getUrl());
    }
    

    @Test
    public void testLoginEmpProcess_WrongPassword() {
        dbEmp = new Employee();
        dbEmp.setEmail("test@example.com");
        dbEmp.setPassword(BCrypt.hashpw("password123", BCrypt.gensalt(12)));

        when(employeeRepository.findByEmail("test@example.com")).thenReturn(dbEmp);

        RedirectView result = indexController.loginEmpProcess("test@example.com", "wrongpassword", session);

        assertEquals("/loginemployee?error=wrongPassword", result.getUrl());
    }

    @Test
    public void testLoginEmpProcess_UserNotFound() {
        when(employeeRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        RedirectView result = indexController.loginEmpProcess("nonexistent@example.com", "password123", session);

        assertEquals("/loginemployee?error=userNotFound", result.getUrl());
    }
}
