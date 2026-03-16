package com.sistema.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.sistema.blog.dto.PostDTO;
import com.sistema.blog.dto.PostResponse;
import com.sistema.blog.service.PostService;
import com.sistema.blog.utils.AppConstants;

import jakarta.validation.Valid;

/**
 * REST controller that exposes CRUD endpoints for blog posts.
 * Write operations (create, delete) are restricted to users with the {@code ADMIN} role.
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {

    /** Service layer handling all post business logic. */
    @Autowired
    private PostService postService;

    // -------------------------------------------------------------------------
    // GET
    // -------------------------------------------------------------------------

    /**
     * Returns a paginated and sorted list of all posts.
     *
     * @param pageNumber the zero-based page index (default: {@value AppConstants#PAGE_NUMBER})
     * @param pageSize   the number of posts per page (default: {@value AppConstants#PAGE_SIZE})
     * @param sortBy     the field to sort by (default: {@value AppConstants#SORT_BY})
     * @param sortDir    the sort direction — "asc" or "desc" (default: {@value AppConstants#SORT_DIR})
     * @return a {@link PostResponse} with page content and pagination metadata
     */
    @GetMapping
    public PostResponse listPosts(
            @RequestParam(value = "pageNo",  defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,   required = false) int pageSize,
            @RequestParam(value = "sortBy",  defaultValue = AppConstants.SORT_BY,     required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR,    required = false) String sortDir) {
        return postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
    }

    /**
     * Returns a single post by its ID.
     *
     * @param id the post ID
     * @return {@code 200 OK} with the matching {@link PostDTO}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // -------------------------------------------------------------------------
    // POST
    // -------------------------------------------------------------------------

    /**
     * Creates a new blog post. Requires the {@code ADMIN} role.
     *
     * @param postDTO the post data (validated)
     * @return {@code 201 CREATED} with the persisted {@link PostDTO}
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDTO> savePost(@Valid @RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    // -------------------------------------------------------------------------
    // PUT
    // -------------------------------------------------------------------------

    /**
     * Updates an existing post. The post ID must be present inside the request body.
     *
     * @param postDTO the updated post data (validated)
     * @return {@code 200 OK} with the updated {@link PostDTO}
     */
    @PutMapping
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postService.updatePost(postDTO), HttpStatus.OK);
    }

    // -------------------------------------------------------------------------
    // DELETE
    // -------------------------------------------------------------------------

    /**
     * Deletes a post by its ID. Requires the {@code ADMIN} role.
     *
     * @param id the ID of the post to delete
     * @return {@code 200 OK} with a confirmation message
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post deleted successfully.", HttpStatus.OK);
    }

}
