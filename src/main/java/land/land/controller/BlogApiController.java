package land.land.controller;

import land.land.domain.Article;
import land.land.dto.AddArticleRequest;
import land.land.dto.ArticleResponse;
import land.land.dto.UpdateArticleRequest;
import land.land.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
@RequiredArgsConstructor
@RestController
public class BlogApiController {

    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request, Principal principal){
        Article savedArticle = blogService.save(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticle(){
        List<ArticleResponse> articleList = blogService.findAll().stream().map(ArticleResponse::new).toList();

        return ResponseEntity.ok().body(articleList);
    }
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<Article> findArticle(@PathVariable Long id) {
        // Article 조회 로직
        Article article = blogService.findById(id);
        return ResponseEntity.ok(article);
    }
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id){
        blogService.delete(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request){
        Article updatedArticle = blogService.update(id, request);
        return ResponseEntity.ok().body(updatedArticle);
    }
}
