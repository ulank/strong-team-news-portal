package kz.ulank.strongteamnewsportal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.ulank.strongteamnewsportal.entity.News;
import kz.ulank.strongteamnewsportal.model.dto.NewsDto;
import kz.ulank.strongteamnewsportal.service.NewsService;
import kz.ulank.strongteamnewsportal.util.model.OrderType;
import kz.ulank.strongteamnewsportal.util.model.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by Ulan on 5/12/2023
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "News", description = "News Controller APIs")
@RequestMapping(value = "/api/v1/news")
public class NewsController {

    private final NewsService newsService;

    @SecurityRequirements
    @Operation(summary = "Get all news articles", description = "Retrieves a list of all news articles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of news articles"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<News>> findAll() {
        return new ResponseEntity<>(newsService.findAll(), HttpStatus.OK);
    }

    @SecurityRequirements
    @Operation(summary = "Get all news articles with pagination", description = "Retrieves a list of all news articles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of news articles"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/pagination")
    public Pagination findAllWithPagination(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "100") int pageSize,
                                            @RequestParam(defaultValue = "publishedAt") String sortBy,
                                            @RequestParam(defaultValue = "ASC") OrderType sortType) {
        return newsService.findAllWithPaginationAndSorting(page, pageSize, sortBy, sortType);
    }

    @SecurityRequirements
    @Operation(summary = "Get by sourceId articles with pagination", description = "Retrieves a list of all news articles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of news articles"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/pagination/sourceId/{sourceId}")
    public Pagination findBySourceIdWithPagination(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "100") int pageSize,
                                                   @RequestParam(defaultValue = "publishedAt") String sortBy,
                                                   @RequestParam(defaultValue = "ASC") OrderType sortType, @PathVariable String sourceId) {
        return newsService.findBySourceIdWithPaginationAndSorting(sourceId, page, pageSize, sortBy, sortType);
    }

    @SecurityRequirements
    @Operation(summary = "Get by topicId articles with pagination", description = "Retrieves a list of all news articles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of news articles"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/pagination/topicId/{topicId}")
    public Pagination findByTopicIdWithPagination(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "100") int pageSize,
                                                   @RequestParam(defaultValue = "publishedAt") String sortBy,
                                                   @RequestParam(defaultValue = "ASC") OrderType sortType, @PathVariable Long topicId) {
        return newsService.findByTopicIdWithPaginationAndSorting(topicId, page, pageSize, sortBy, sortType);
    }


    @SecurityRequirements
    @Operation(summary = "Get all news articles", description = "Retrieves a list of all news articles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of news articles"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/id/{newsId}")
    public ResponseEntity<News> findById(@PathVariable UUID newsId) {
        return new ResponseEntity<>(newsService.findById(newsId), HttpStatus.OK);
    }

    @SecurityRequirements({
            @SecurityRequirement(name = "bearer-token")
    })
    @Operation(summary = "Create a news article", description = "Creates a new news article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the news article"),
            @ApiResponse(responseCode = "401", description = "Access Denied"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<News> create(@RequestBody NewsDto news) {
        return new ResponseEntity<>(newsService.save(news), HttpStatus.OK);
    }


    @SecurityRequirements({
            @SecurityRequirement(name = "bearer-token")
    })
    @Operation(summary = "Update a news article", description = "Updates an existing news article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the news article"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Access Denied"),
            @ApiResponse(responseCode = "404", description = "News article not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/id/{newsId}")
    public ResponseEntity<News> change(@PathVariable UUID newsId, @RequestBody NewsDto news) {
        return new ResponseEntity<>(newsService.update(news, newsId), HttpStatus.OK);
    }

    @SecurityRequirements({
            @SecurityRequirement(name = "bearer-token")
    })
    @Operation(summary = "Delete a news article", description = "Deletes an existing news article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the news article"),
            @ApiResponse(responseCode = "404", description = "News article not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/id/{newsId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable UUID newsId) {
        newsService.deleteById(newsId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SecurityRequirements({
            @SecurityRequirement(name = "bearer-token")
    })
    @Operation(summary = "Save news from News API V2", description = "Getting data from API then save our database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved and retrieved the list of news articles"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/fill/slug/{slug}")
    public ResponseEntity<List<News>> fillNews(@PathVariable String slug) {
        return new ResponseEntity<>(newsService.saveNewsUsingSlugByNewsApi(slug), HttpStatus.OK);
    }

}
