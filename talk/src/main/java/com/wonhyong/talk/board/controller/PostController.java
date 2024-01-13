package com.wonhyong.talk.board.controller;

import com.wonhyong.talk.board.dto.PostDto;
import com.wonhyong.talk.board.service.PostService;
import com.wonhyong.talk.member.domain.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;


@CrossOrigin
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public Iterable<PostDto> getPosts(Pageable pageable) throws Exception {
        return postService.getPage(pageable);
    }

    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable("id") Long id) throws Exception {
        return postService.findById(id);
    }

    @PostMapping
    public PostDto createPost(@RequestBody PostDto postRequestDto,
                                      @AuthenticationPrincipal MemberDetails member) throws Exception {
        return postService.create(member, postRequestDto);
    }

    @PutMapping("/{id}")
    public PostDto updatePost(@PathVariable("id") Long id,
                              @RequestBody PostDto postRequestDto,
                              @AuthenticationPrincipal MemberDetails member) throws Exception {

        return postService.update(id, member, postRequestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id, @AuthenticationPrincipal MemberDetails member) throws Exception {
        postService.delete(id, member);
        return ResponseEntity.ok("Post(" + id + ") deleted");
    }

    //TODO to all controller handler
    @ExceptionHandler({NoSuchElementException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleNoElement(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({UsernameNotFoundException.class, IllegalAccessException.class})
    public ResponseEntity<String> handleUserExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}