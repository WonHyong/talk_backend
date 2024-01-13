package com.wonhyong.talk.board.service;

import com.wonhyong.talk.board.dto.PostDto;
import com.wonhyong.talk.board.model.Post;
import com.wonhyong.talk.board.repository.PostRepository;
import com.wonhyong.talk.member.domain.Member;
import com.wonhyong.talk.member.domain.MemberDetails;
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
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public Slice<PostDto> getPage(Pageable pageable) {
        return postRepository.findSliceBy(pageable).map(PostDto::from);
    }

    @Transactional
    public PostDto findById(@NonNull Long id) throws NoSuchElementException {
        Post post = findPostById(id);

        // increase view count
        postRepository.increaseView(id);

        return PostDto.from(post);
    }

    @Transactional
    public PostDto create(MemberDetails member, @NonNull PostDto postDto) throws NoSuchElementException, UsernameNotFoundException {
        Member currentUser = findUser(member);

        Post post = postDto.toModel(currentUser);
        postRepository.save(post);

        return PostDto.from(post);
    }

    @Transactional
    public PostDto update(Long id, MemberDetails member, @NonNull PostDto postDto) throws NoSuchElementException, UsernameNotFoundException, IllegalAccessException {
        Post post = findPostById(id);

        Member currentUser = findUser(member);

        checkIsWriter(currentUser, post.getMember());

        post.update(postDto.getTitle(), postDto.getContent());
        postRepository.save(post);

        return PostDto.from(post);
    }

    @Transactional
    public void delete(Long id, MemberDetails member) throws NoSuchElementException, UsernameNotFoundException, IllegalAccessException {
        Post post = findPostById(id);

        Member currentUser = findUser(member);

        checkIsWriter(currentUser, post.getMember());

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

    protected void checkIsWriter(Member currentUser, Member writer) throws IllegalAccessException {
        if (currentUser == null || writer == null) throw new IllegalAccessException("NO WRITER or USER");
        if (!memberService.isMemberEquals(currentUser, writer)) {
            throw new IllegalAccessException(currentUser.getName() + " not equals to writer: " + writer.getName());
        }
    }
}
