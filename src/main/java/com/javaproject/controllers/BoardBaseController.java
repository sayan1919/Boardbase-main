package com.javaproject.controllers;

import java.net.URI;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.hibernate.boot.model.relational.Database;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.javaproject.beans.BoardBase;
import com.javaproject.beans.ErrorMessage;
import com.javaproject.beans.Review;
import com.javaproject.database.DatabaseAccess;

// special type of controller that is specialized for REST purpose. It marshals our domain objects to and from json
@RestController
@RequestMapping("/boardbases")
public class BoardBaseController {

    private DatabaseAccess da;

    public BoardBaseController(DatabaseAccess da) {
        this.da = da;
    }

    /**
     * Retrieve all boardbases
     * 
     * @return
     */
    @GetMapping
    public List<boardBase> getBoardBases() {
        return da.getBoardBases();
    }

    /**
     * Handles requests for specific boardbase
     * 
     * @param id
     * @return the ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getBoardBase(@PathVariable Long id) {
        BoardBase boardBase = da.getBoardBase(id);
        if (boardBase != null) {
            return ResponseEntity.ok(boardBase);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("No such record"));
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> postboardbase(@RequestBody BoardBase boardBase) {
        try {
            Long id = da.addBoardBase(boardBase);
            boardBase.setId(id);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(location).body(boardBase);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessage("Name already exists."));
        }

    }
}
