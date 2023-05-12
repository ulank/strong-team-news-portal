package kz.ulank.strongteamnewsportal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.ulank.strongteamnewsportal.entity.Topic;
import kz.ulank.strongteamnewsportal.model.dto.TopicDto;
import kz.ulank.strongteamnewsportal.service.TopicService;
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
@RequestMapping(value = "/api/v1/topic")
@Tag(name = "Topic", description = "Topic Controller APIs")
public class TopicController {

    private final TopicService topicService;

    @SecurityRequirements
    @Operation(summary = "Get all topics", description = "Returns a list of all topics")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the topics")
    @GetMapping
    public ResponseEntity<List<Topic>> findAll() {
        return new ResponseEntity<>(topicService.findAll(), HttpStatus.OK);
    }

    @SecurityRequirements
    @Operation(summary = "Find a topic by ID", description = "Returns a single topic based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found the topic"),
            @ApiResponse(responseCode = "404", description = "Topic not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/id/{topicId}")
    public ResponseEntity<Topic> findById(@PathVariable Long topicId) {
        return new ResponseEntity<>(topicService.findById(topicId), HttpStatus.OK);
    }


    @SecurityRequirements({
            @SecurityRequirement(name = "bearer-token")
    })
    @Operation(summary = "Create a new topic", description = "Creates a new topic based on the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the topic"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Topic> create(@RequestBody TopicDto topic) {
        return new ResponseEntity<>(topicService.save(topic), HttpStatus.OK);
    }


    @SecurityRequirements({
            @SecurityRequirement(name = "bearer-token")
    })
    @Operation(summary = "Update a topic", description = "Updates an existing topic based on the provided ID and data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the topic"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Topic not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/id/{topicId}")
    public ResponseEntity<Topic> change(@PathVariable Long topicId, @RequestBody TopicDto topic) {
        return new ResponseEntity<>(topicService.update(topic, topicId), HttpStatus.OK);
    }

    @SecurityRequirements({
            @SecurityRequirement(name = "bearer-token")
    })
    @Operation(summary = "Delete a topic", description = "Deletes an existing topic based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the topic"),
            @ApiResponse(responseCode = "404", description = "Topic not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/id/{topicId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long topicId) {
        topicService.deleteById(topicId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
