package com.sistema.blog.service;

import com.sistema.blog.dto.PostDTO;
import com.sistema.blog.dto.PostResponse;

/**
 * Service interface defining the business operations for managing blog posts.
 */
public interface PostService {

    /**
     * Creates a new blog post.
     *
     * @param postDTO the post data to persist
     * @return the created post as a {@link PostDTO}
     */
    PostDTO createPost(PostDTO postDTO);

    /**
     * Retrieves a paginated and sorted list of all blog posts.
     *
     * @param pageNumber the zero-based page index
     * @param pageSize   the number of posts per page
     * @param sortBy     the field name to sort by
     * @param sortDir    the sort direction ("asc" or "desc")
     * @return a {@link PostResponse} containing the page data and metadata
     */
    PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir);

    /**
     * Retrieves a single post by its ID.
     *
     * @param id the post ID
     * @return the matching post as a {@link PostDTO}
     */
    PostDTO getPostById(Long id);

    /**
     * Updates an existing post.
     *
     * @param postDTO the updated post data (must include the post ID)
     * @return the updated post as a {@link PostDTO}
     */
    PostDTO updatePost(PostDTO postDTO);

    /**
     * Deletes a post by its ID.
     *
     * @param id the ID of the post to delete
     */
    void deletePost(long id);

}
