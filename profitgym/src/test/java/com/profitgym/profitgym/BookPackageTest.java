package com.profitgym.profitgym;

import com.profitgym.profitgym.controllers.UserController;
import com.profitgym.profitgym.models.Client;
import com.profitgym.profitgym.models.Memberships;
import com.profitgym.profitgym.models.Package;
import com.profitgym.profitgym.repositories.MembershipsRepository;
import com.profitgym.profitgym.services.PackageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.view.RedirectView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class BookPackageTest {

    private UserController userController;

    @Mock
    private MembershipsRepository membershipsRepository;

    @Mock
    private PackageService packageService;

    private MockHttpSession session;
    private Client loggedInUser;
    private Memberships membership;
    private Package packageEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        session = new MockHttpSession();
        loggedInUser = new Client();
        loggedInUser.setID(1);

        membership = new Memberships();
        membership.setClientID(1);
        membership.setPackageID(1);

        packageEntity = new Package();
        packageEntity.setId(1);
        packageEntity.setNumOfMonths(6);
        packageEntity.setFreezeLimit(2);

        userController = new UserController(membershipsRepository, packageService);
    }

    @Test
    public void testRequestPackage_AlreadySubscribedInAMembership() {
        session.setAttribute("loggedInUser", loggedInUser);

        Memberships existingMembership = new Memberships();
        existingMembership.setClientID(1);
        existingMembership.setIsActivated("Activated");

        when(membershipsRepository.findByClientID(anyInt())).thenReturn(existingMembership);

        RedirectView result = userController.RequestPackage(membership, session);

        assertEquals("/user/bookpackage?AlreadySubscribedInAMembership", result.getUrl());
    }

    @Test
    public void testRequestPackage_RequestAlreadySentAndPending() {
        session.setAttribute("loggedInUser", loggedInUser);

        Memberships existingMembership = new Memberships();
        existingMembership.setClientID(1);
        existingMembership.setIsActivated("Pending");

        when(membershipsRepository.findByClientID(anyInt())).thenReturn(existingMembership);

        RedirectView result = userController.RequestPackage(membership, session);

        assertEquals("/user/bookpackage?RequestAlreadySentAndPending", result.getUrl());
    }

    @Test
    public void testRequestPackage_RequestSent() {
        session.setAttribute("loggedInUser", loggedInUser);

        when(membershipsRepository.findByClientID(anyInt())).thenReturn(null);
        when(packageService.findById(anyInt())).thenReturn(packageEntity);

        RedirectView result = userController.RequestPackage(membership, session);

        assertEquals("/user/bookpackage?RequestSent", result.getUrl());
    }

    @Test
    public void testRequestPackage_UserNotLoggedIn() {
        RedirectView result = userController.RequestPackage(membership, session);

        assertEquals("/login", result.getUrl());
    }

}
