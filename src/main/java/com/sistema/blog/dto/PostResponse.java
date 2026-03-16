package com.sistema.blog.dto;

import java.util.List;

/**
 * Wrapper DTO returned by paginated post listing endpoints.
 * Contains the page content along with pagination metadata.
 */
public class PostResponse {

    /** List of posts on the current page. */
    private List<PostDTO> content;

    /** Zero-based index of the current page. */
    private int pageNumber;

    /** Maximum number of posts per page. */
    private int pageSize;

    /** Total number of posts across all pages. */
    private long totalElements;

    /** Total number of available pages. */
    private int totalPages;

    /** {@code true} if this is the last available page. */
    private boolean last;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Default no-args constructor.
     */
    public PostResponse() {
        super();
    }

    // -------------------------------------------------------------------------
    // Getters & Setters
    // -------------------------------------------------------------------------

    /**
     * Returns the list of posts on the current page.
     *
     * @return the page content
     */
    public List<PostDTO> getContent() {
        return content;
    }

    /**
     * Sets the list of posts for the current page.
     *
     * @param content the page content
     */
    public void setContent(List<PostDTO> content) {
        this.content = content;
    }

    /**
     * Returns the zero-based page number.
     *
     * @return the page number
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets the zero-based page number.
     *
     * @param pageNumber the page number
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Returns the number of posts per page.
     *
     * @return the page size
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets the number of posts per page.
     *
     * @param pageSize the page size
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Returns the total number of posts in the database.
     *
     * @return total element count
     */
    public long getTotalElements() {
        return totalElements;
    }

    /**
     * Sets the total number of posts.
     *
     * @param totalElements total element count
     */
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    /**
     * Returns the total number of pages available.
     *
     * @return total page count
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Sets the total number of pages.
     *
     * @param totalPages total page count
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * Returns {@code true} if there are no more pages after this one.
     *
     * @return {@code true} if this is the last page
     */
    public boolean isLast() {
        return last;
    }

    /**
     * Sets whether this is the last available page.
     *
     * @param last {@code true} if this is the last page
     */
    public void setLast(boolean last) {
        this.last = last;
    }

}
