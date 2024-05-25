package com.profitgym.profitgym;

import com.profitgym.profitgym.controllers.UserController;
import com.profitgym.profitgym.models.AssignedClass;
import com.profitgym.profitgym.models.Client;
import com.profitgym.profitgym.models.ReservedClass;
import com.profitgym.profitgym.repositories.AssignedClassRepository;
import com.profitgym.profitgym.repositories.ClientRepository;
import com.profitgym.profitgym.repositories.ReservedClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class BookClassTest {

    private UserController userController;

    @Mock
    private ReservedClassRepository reservedClassRepository;

    @Mock
    private AssignedClassRepository assignedClassRepository;

    @Mock
    private ClientRepository clientRepository;

    private MockHttpSession session;
    private Client loggedInUser;
    private ReservedClass reservedClass;
    private AssignedClass assignedClass;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        session = new MockHttpSession();
        loggedInUser = new Client();
        loggedInUser.setID(1);

        assignedClass = new AssignedClass();
        assignedClass.setID(1);
        assignedClass.setPrice(100);
        assignedClass.setAvailablePlaces(5);

        reservedClass = new ReservedClass();
        reservedClass.setAssignedClassID(1);
        reservedClass.setClientID(1);

        userController = new UserController(clientRepository, assignedClassRepository, reservedClassRepository);
    }

    @Test
    public void testRequestClass_Pending() {
        session.setAttribute("loggedInUser", loggedInUser);

        when(reservedClassRepository.findByClientID(anyInt())).thenReturn(new ArrayList<>());
        when(assignedClassRepository.findByID(anyInt())).thenReturn(assignedClass);

        RedirectView result = userController.RequestClass(reservedClass, session);

        assertEquals("/user/bookclass?RequestSent", result.getUrl());
    }

    @Test
    public void testRequestClass_ClassActivated() {
        assignedClass.setPrice(0);

        session.setAttribute("loggedInUser", loggedInUser);

        when(reservedClassRepository.findByClientID(anyInt())).thenReturn(new ArrayList<>());
        when(assignedClassRepository.findByID(anyInt())).thenReturn(assignedClass);

        RedirectView result = userController.RequestClass(reservedClass, session);

        assertEquals("/user/bookclass?ClassActivated", result.getUrl());
    }

    @Test
    public void testRequestClass_NoAvailablePlaces() {
        assignedClass.setAvailablePlaces(0);

        session.setAttribute("loggedInUser", loggedInUser);

        when(reservedClassRepository.findByClientID(anyInt())).thenReturn(new ArrayList<>());
        when(assignedClassRepository.findByID(anyInt())).thenReturn(assignedClass);

        RedirectView result = userController.RequestClass(reservedClass, session);

        assertEquals("/user/bookclass?NoAvailablePlaces", result.getUrl());
    }

    @Test
    public void testRequestClass_AlreadyBookedThisClass() {
        ReservedClass existingReservedClass = new ReservedClass();
        existingReservedClass.setAssignedClassID(1);
        existingReservedClass.setClientID(1);
        existingReservedClass.setIsActivated("Activated");

        List<ReservedClass> reservedClasses = new ArrayList<>();
        reservedClasses.add(existingReservedClass);

        session.setAttribute("loggedInUser", loggedInUser);

        when(reservedClassRepository.findByClientID(anyInt())).thenReturn(reservedClasses);
        when(assignedClassRepository.findByID(anyInt())).thenReturn(assignedClass);

        RedirectView result = userController.RequestClass(reservedClass, session);

        assertEquals("/user/bookclass?AlreadyBookedThisClass", result.getUrl());
    }

    @Test
    public void testRequestClass_RequestAlreadySentAndPending() {
        ReservedClass existingReservedClass = new ReservedClass();
        existingReservedClass.setAssignedClassID(1);
        existingReservedClass.setClientID(1);
        existingReservedClass.setIsActivated("Pending");

        List<ReservedClass> reservedClasses = new ArrayList<>();
        reservedClasses.add(existingReservedClass);

        session.setAttribute("loggedInUser", loggedInUser);

        when(reservedClassRepository.findByClientID(anyInt())).thenReturn(reservedClasses);
        when(assignedClassRepository.findByID(anyInt())).thenReturn(assignedClass);

        RedirectView result = userController.RequestClass(reservedClass, session);

        assertEquals("/user/bookclass?RequestAlreadySentAndPending", result.getUrl());
    }

    @Test
    public void testRequestClass_UserNotLoggedIn() {
        RedirectView result = userController.RequestClass(reservedClass, session);

        assertEquals("/login", result.getUrl());
    }
}
