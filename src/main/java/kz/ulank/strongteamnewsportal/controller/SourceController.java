package kz.ulank.strongteamnewsportal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.ulank.strongteamnewsportal.entity.Source;
import kz.ulank.strongteamnewsportal.model.dto.SourceDto;
import kz.ulank.strongteamnewsportal.service.SourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Ulan on 5/12/2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/source")
@Tag(name = "Source", description = "Source Controller APIs")
public class SourceController {

    private final SourceService sourceService;

    @SecurityRequirements
    @Operation(summary = "Get all sources", description = "Returns a list of all sources")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the sources")
    @GetMapping
    public ResponseEntity<List<Source>> findAll() {
        return new ResponseEntity<>(sourceService.findAll(), HttpStatus.OK);
    }

    @SecurityRequirements
    @Operation(summary = "Find a source by ID", description = "Returns a single source based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found the source"),
            @ApiResponse(responseCode = "404", description = "Source not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/id/{sourceId}")
    public ResponseEntity<Source> findById(@PathVariable String sourceId) {
        return new ResponseEntity<>(sourceService.findById(sourceId), HttpStatus.OK);
    }


    @SecurityRequirements({
            @SecurityRequirement(name = "bearer-token")
    })
    @Operation(summary = "Create a new source", description = "Creates a new source based on the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the source"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Source> create(@RequestBody SourceDto source) {
        return new ResponseEntity<>(sourceService.save(source), HttpStatus.OK);
    }


    @SecurityRequirements({
            @SecurityRequirement(name = "bearer-token")
    })
    @Operation(summary = "Update a source", description = "Updates an existing source based on the provided ID and data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the source"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Source not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/id/{sourceId}")
    public ResponseEntity<Source> change(@PathVariable String sourceId, @RequestBody SourceDto source) {
        return new ResponseEntity<>(sourceService.update(source, sourceId), HttpStatus.OK);
    }

    @SecurityRequirements({
            @SecurityRequirement(name = "bearer-token")
    })
    @Operation(summary = "Delete a source", description = "Deletes an existing source based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the source"),
            @ApiResponse(responseCode = "404", description = "Source not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/id/{sourceId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String sourceId) {
        sourceService.deleteById(sourceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
