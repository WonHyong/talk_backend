package com.wonhyong.talk.board.service;

import com.wonhyong.talk.board.dto.PostDto;
import com.wonhyong.talk.board.model.Like;
import com.wonhyong.talk.board.model.Post;
import com.wonhyong.talk.board.repository.LikeRepository;
import com.wonhyong.talk.board.repository.PostRepository;
import com.wonhyong.talk.member.domain.Member;
import com.wonhyong.talk.member.domain.MemberDetails;
import com.wonhyong.talk.member.domain.Role;
import com.wonhyong.talk.member.service.MemberService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public Slice<PostDto> getPage(Pageable pageable, MemberDetails member) {
        Member currentUser = findUser(member);
        return postRepository.findSliceBy(pageable).map(post -> PostDto.from(post, currentUser));
    }

    @Transactional
    public PostDto findById(MemberDetails member, @NonNull Long id) throws NoSuchElementException {
        Member currentUser = findUser(member);
        Post post = findPostById(id);

        // increase view count
        postRepository.increaseView(id);

        return PostDto.from(post, currentUser);
    }

    @Transactional
    public PostDto create(MemberDetails member, @NonNull PostDto postDto) throws NoSuchElementException, UsernameNotFoundException {
        Member currentUser = findUser(member);

        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .member(currentUser)
                .build();

        postRepository.save(post);

        return PostDto.from(post, currentUser);
    }

    @Transactional
    public boolean increaseLike(@NonNull Long id, MemberDetails member) {
        Member currentUser = findUser(member);
        Post post = findPostById(id);

        if (post.isAlreadyLiked(currentUser)) {
            return false;
        }

        Like like = Like.builder()
                .likeTo(post)
                .member(currentUser)
                .build();

        likeRepository.save(like);

        return true;
    }

    @Transactional
    public PostDto update(Long id, MemberDetails member, @NonNull PostDto postDto) throws NoSuchElementException, UsernameNotFoundException, IllegalAccessException {
        Post post = findPostById(id);

        Member currentUser = findUser(member);

        if (!isWriter(currentUser, post.getMember())) {
            throw new IllegalAccessException("NO PERMISSION FOR UPDATE POST: " + id);
        }

        post.update(postDto.getTitle(), postDto.getContent());
        postRepository.save(post);

        return PostDto.from(post, currentUser);
    }

    @Transactional
    public void delete(Long id, MemberDetails member) throws NoSuchElementException, UsernameNotFoundException, IllegalAccessException {
        Post post = findPostById(id);

        Member currentUser = findUser(member);

        if (!isWriter(currentUser, post.getMember())) {
            throw new IllegalAccessException("NO PERMISSION FOR DELETE POST: " + id);
        }

        postRepository.deleteById(id);
    }

    protected Post findPostById(Long id) throws NoSuchElementException {
        return postRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("NO POST FOR " + id));
    }

    protected Member findUser(MemberDetails member) throws UsernameNotFoundException {
        return memberService.findByName(member.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("NO USER FOR " + member.getUsername()));
    }

    protected boolean isWriter(Member currentUser, Member writer) {
        if (currentUser != null && currentUser.getRole().equals(Role.ADMIN)) return true;
        return currentUser != null && writer != null && memberService.isMemberEquals(currentUser, writer);
    }
}
