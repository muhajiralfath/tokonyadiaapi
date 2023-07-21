package com.enigma.tokonyadia.service.impl;

import com.enigma.tokonyadia.entity.Admin;
import com.enigma.tokonyadia.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        adminRepository = mock(AdminRepository.class);
        adminService = new AdminServiceImpl(adminRepository);
    }

    @Test
    public void createAdmin_Success() {
        // Create a sample Admin object for testing
        Admin admin = new Admin();
        admin.setName("adminUser");

        // Mock the behavior of the adminRepository.save() method
        when(adminRepository.save(admin)).thenReturn(admin);

        // Call the create() method of the AdminService
        Admin result = adminService.create(admin);

        // Verify that the adminRepository.save() method is called once
        verify(adminRepository, times(1)).save(admin);

        // Verify the result
        assertEquals(admin, result);
    }

    @Test
    public void createAdmin_DuplicateUsername_ThrowsConflictException() {
        // Create a sample Admin object for testing
        Admin admin = new Admin();
        admin.setName("adminUser");

        // Mock the behavior of the adminRepository.save() method
        when(adminRepository.save(admin)).thenThrow(new DataIntegrityViolationException("Duplicate entry"));

        // Call the create() method of the AdminService and expect an exception
        assertThrows(ResponseStatusException.class, () -> adminService.create(admin));

        // Verify that the adminRepository.save() method is called once
        verify(adminRepository, times(1)).save(admin);
    }
}
