package com.sistema.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sistema.blog.dto.PostDTO;
import com.sistema.blog.dto.PostResponse;
import com.sistema.blog.entities.Post;
import com.sistema.blog.exceptions.ResourceNotFoundException;
import com.sistema.blog.repository.PostRepository;

/**
 * Implementation of {@link PostService} that handles all post-related
 * business logic, including creation, paginated retrieval, update, and deletion.
 */
@Service
public class PostServiceImpl implements PostService {

    /** Mapper used to convert between {@link Post} entities and {@link PostDTO}s. */
    @Autowired
    private ModelMapper modelMapper;

    /** Repository for performing CRUD and pagination operations on {@link Post} entities. */
    @Autowired
    private PostRepository postRepository;

    // -------------------------------------------------------------------------
    // Public operations
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     * Maps the DTO to an entity, persists it, and returns the saved result as a DTO.
     */
    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post    = mapToEntity(postDTO);
        Post newPost = postRepository.save(post);
        return mapToDTO(newPost);
    }

    /**
     * {@inheritDoc}
     * Builds a {@link Pageable} with the requested sort direction and page settings,
     * queries the database, and assembles the response metadata.
     */
    @Override
    public PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort     sort     = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                            ? Sort.by(sortBy).ascending()
                            : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post>    page    = postRepository.findAll(pageable);
        List<PostDTO> content = page.getContent()
                                    .stream()
                                    .map(this::mapToDTO)
                                    .collect(Collectors.toList());

        PostResponse response = new PostResponse();
        response.setContent(content);
        response.setPageNumber(pageNumber);
        response.setPageSize(pageSize);
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    /**
     * {@inheritDoc}
     * Looks up the existing post to ensure it exists, maps the incoming DTO
     * to an entity, and saves the updated version.
     */
    @Override
    public PostDTO updatePost(PostDTO postDTO) {
        postRepository.findById(postDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postDTO.getId()));
        Post updatedPost = postRepository.save(mapToEntity(postDTO));
        return mapToDTO(updatedPost);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    /**
     * Converts a {@link Post} entity to a {@link PostDTO}.
     *
     * @param post the entity to convert
     * @return the resulting DTO
     */
    private PostDTO mapToDTO(Post post) {
        return modelMapper.map(post, PostDTO.class);
    }

    /**
     * Converts a {@link PostDTO} to a {@link Post} entity.
     *
     * @param postDTO the DTO to convert
     * @return the resulting entity
     */
    private Post mapToEntity(PostDTO postDTO) {
        return modelMapper.map(postDTO, Post.class);
    }

}
