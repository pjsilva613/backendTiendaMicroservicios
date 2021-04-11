package com.tiendaVentaOnline.tiendaOnlineService.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendaVentaOnline.tiendaOnlineService.entity.Persona;
import com.tiendaVentaOnline.tiendaOnlineService.service.PersonaServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/personas")
public class PersonaController {

    @Autowired
    private PersonaServiceInt personaServiceInt;

    @GetMapping
    public ResponseEntity<List<Persona>> listAllPerson( ) {
        List<Persona> personas =  personaServiceInt.findAllPerson();
        if (null == personas) {
            return  ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(personas);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Persona> getPerson(@PathVariable("id") long id) {
        Persona persona = personaServiceInt.getPerson(id);
        if (  null == persona) {
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(persona);
    }

    @PostMapping
    public ResponseEntity<Persona> createPerson(@Valid @RequestBody Persona persona, BindingResult result) {
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Persona personaDb = personaServiceInt.createPerson (persona);
        return  ResponseEntity.status( HttpStatus.CREATED).body(personaDb);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updatePerson(@PathVariable("id") long id, @RequestBody Persona persona) {
        Persona personaDb = personaServiceInt.getPerson(id);
        if ( null == personaDb ) {
            return  ResponseEntity.notFound().build();
        }
        persona.setId(id);
        personaDb=personaServiceInt.updatePerson(persona);
        return  ResponseEntity.ok(personaDb);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Persona> deletePerson(@PathVariable("id") long id) {
        Persona persona = personaServiceInt.getPerson(id);
        if ( null == persona ) {
            return  ResponseEntity.notFound().build();
        }
        persona = personaServiceInt.deletePerson(persona);
        return  ResponseEntity.ok(persona);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
