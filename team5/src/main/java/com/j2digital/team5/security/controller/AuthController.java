package com.j2digital.team5.security.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.j2digital.team5.exceptions.AttributeException;
import com.j2digital.team5.security.domain.Usuario;
import com.j2digital.team5.security.dto.CreateUserDto;
import com.j2digital.team5.security.dto.JwtTokenDto;
import com.j2digital.team5.security.dto.LoginUserDto;
import com.j2digital.team5.security.dto.Mensaje;
import com.j2digital.team5.security.service.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	UserService userService;
	
    @PostMapping("/create")
    public ResponseEntity<Mensaje> create(@Valid @RequestBody CreateUserDto dto) throws AttributeException {
        Usuario usuario = userService.create(dto);
        return ResponseEntity.ok(new Mensaje(HttpStatus.OK, "user " + usuario.getUsername() + " have been created"));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@Valid @RequestBody LoginUserDto dto) throws AttributeException {
        JwtTokenDto jwtTokenDto = userService.login(dto);
        return ResponseEntity.ok(jwtTokenDto);
    }
}
