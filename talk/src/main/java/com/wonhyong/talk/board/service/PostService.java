package com.wonhyong.talk.board.service;

import com.wonhyong.talk.board.dto.PostDto;
import com.wonhyong.talk.board.model.Like;
import com.wonhyong.talk.board.model.Post;
import com.wonhyong.talk.board.repository.CommentRepository;
import com.wonhyong.talk.board.repository.LikeRepository;
import com.wonhyong.talk.board.repository.PostRepository;
import com.wonhyong.talk.member.domain.Member;
import com.wonhyong.talk.member.domain.MemberDetails;
import com.wonhyong.talk.member.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Transactional(readOnly = true)
    public Slice<PostDto.ListResponse> getPage(Pageable pageable) {
        return postRepository.findSliceBy(pageable).map(post -> {
            int like = likeRepository.countAllByPost_Id(post.getId());
            int commentNum = commentRepository.countAllByPost_Id(post.getId());
            return PostDto.ListResponse.from(post, like, commentNum);});
    }

    @Transactional
    public PostDto.DetailResponse findById(Long id) {
        Post post = findPostById(id);
        postRepository.increaseView(id);
        return PostDto.DetailResponse.from(post);
    }

    @Transactional
    public PostDto.DetailResponse create(MemberDetails member, PostDto.Request postDto) {
        Member currentUser = member.getMember();

        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .writer(currentUser)
                .build();

        return PostDto.DetailResponse.from(postRepository.save(post));
    }

    @Transactional
    public PostDto.DetailResponse update(Long id, MemberDetails member, PostDto.Request postDto) {
        Post post = findPostById(id);
        Member currentUser = member.getMember();

        checkIsWriter(currentUser, post.getWriter());

        post.update(postDto.getTitle(), postDto.getContent());

        return PostDto.DetailResponse.from(postRepository.save(post));
    }

    @Transactional
    public void delete(Long id, MemberDetails member) throws NoSuchElementException, UsernameNotFoundException {
        Post post = findPostById(id);
        Member currentUser = member.getMember();

        checkIsWriter(currentUser, post.getWriter());

        postRepository.deleteById(id);
    }

    @Transactional
    public boolean increaseLike(Long id, MemberDetails member) {
        if (likeRepository.existsByPost_IdAndMember_Id(id, member.getId())) {
            return false;
        }

        Post post = new Post(id);
        Member currentUser = member.getMember();

        Like like = Like.builder()
                .post(post)
                .member(currentUser)
                .build();

        likeRepository.save(like);

        return true;
    }

    private Post findPostById(Long id) throws NoSuchElementException {
        return postRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("NO POST FOR " + id));
    }

    private void checkIsWriter(Member currentUser, Member writer) {
        if (!currentUser.equals(writer) && !currentUser.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("");
        }
    }
}