package com.example.news_aggregator.controller;

import com.example.news_aggregator.dto.ArticleDTO;
import com.example.news_aggregator.entity.Article;
import com.example.news_aggregator.exception.ArticleNotFoundException;
import com.example.news_aggregator.service.ArticleService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/create")
    public ResponseEntity<Article> createArticle(@Valid @RequestBody ArticleDTO articleDTO) {
        logger.info("Request to create article with title '{}'", articleDTO.getTitle());
        try {
            Article article = articleService.createArticle(articleDTO);
            return new ResponseEntity<>(article, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating article", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @Valid @RequestBody ArticleDTO articleDTO) {
        logger.info("Request to update article with id {}", id);
        try {
            Article updatedArticle = articleService.updateArticle(id, articleDTO);
            return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            logger.error("Article not found with id {}", id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error updating article with id {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        logger.info("Request to delete article with id {}", id);
        try {
            Optional<Article> deletedArticle = articleService.deleteArticle(id);
            return deletedArticle.isPresent() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error deleting article with id {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        logger.info("Request to fetch article with id {}", id);
        try {
            Article article = articleService.getArticleById(id);
            return new ResponseEntity<>(article, HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            logger.error("Article not found with id {}", id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error fetching article with id {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<Article>> getAllArticles(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(defaultValue = "id,asc") String[] sort) {
        logger.info("Request to fetch all articles with pagination and sorting");
        try {
            List<Sort.Order> orders = new ArrayList<>();
            if (sort[0].contains(",")) {
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(Sort.Direction.fromString(_sort[1]), _sort[0]));
                }
            } else {
                orders.add(new Sort.Order(Sort.Direction.fromString(sort[1]), sort[0]));
            }

            Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
            List<Article> articles = articleService.getAllArticles(pageable);
            return new ResponseEntity<>(articles, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching all articles", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
