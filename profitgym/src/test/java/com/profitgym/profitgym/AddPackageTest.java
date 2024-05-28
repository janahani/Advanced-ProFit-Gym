package com.profitgym.profitgym;

import com.profitgym.profitgym.controllers.PackageController;
import com.profitgym.profitgym.models.Employee;
import com.profitgym.profitgym.models.Package;
import com.profitgym.profitgym.services.PackageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AddPackageTest {

    @Mock
    private PackageService packageService;

    @InjectMocks
    private PackageController packageController;

    private MockHttpSession session;
    private Employee loggedInEmp;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        session = new MockHttpSession();
        loggedInEmp = new Employee();
        loggedInEmp.setID(1);

        session = new MockHttpSession();
    }

    @Test
    public void testSavePackage_Success() {
        Package packageObj = new Package();

        packageObj.setTitle("1 Month");
        packageObj.setPrice(600);

        session.setAttribute("loggedInEmp", loggedInEmp); 
        ModelAndView result = packageController.savePackage(packageObj, session);

        verify(packageService, times(1)).savePackage(packageObj);
        assertEquals("redirect:/admindashboard/addpackage", result.getViewName());
    }
    
    @Test
    public void testSavePackage_NoLoggedInEmp() {
        Package packageObj = new Package();

        packageObj.setTitle("1 Month");
        packageObj.setPrice(600);
        
        ModelAndView result = packageController.savePackage(packageObj, session);

        assertEquals("redirect:/loginemployee", result.getViewName());
    }

}

