package net.javaguides.ems.controller;


import net.javaguides.ems.dto.AuthResponse;

import net.javaguides.ems.dto.LoginRequest;
import net.javaguides.ems.dto.RegisterRequest;
import net.javaguides.ems.entity.Role;
import net.javaguides.ems.entity.User;


import net.javaguides.ems.jwt.JwtUtils;
import net.javaguides.ems.repository.RoleRepository;
import net.javaguides.ems.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authManager,
                          JwtUtils jwtUtils,
                          UserRepository userRepo,
                          RoleRepository roleRepo,
                          PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtUtils = jwtUtils;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow();

        String token = jwtUtils.generateToken(user.getUsername());

        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        return new AuthResponse(token, roles);
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Role> roleEntities = request.getRoles()
                .stream()
                .map(roleName -> roleRepo.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        user.setRoles(roleEntities);
        userRepo.save(user);

        return "User registered successfully";
    }
    }