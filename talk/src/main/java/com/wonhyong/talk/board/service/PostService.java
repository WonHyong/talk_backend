package com.wonhyong.talk.board.service;

import com.wonhyong.talk.board.dto.PostRequestDto;
import com.wonhyong.talk.board.dto.PostResponseDto;
import com.wonhyong.talk.board.entity.Post;
import com.wonhyong.talk.board.repository.PostRepository;
import com.wonhyong.talk.member.domain.Member;
import com.wonhyong.talk.member.domain.MemberDetails;
import com.wonhyong.talk.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Slice<PostResponseDto> pages(Pageable pageable) {
        return postRepository.findSliceBy(pageable)
                .map(PostResponseDto::from);
    }

    @Transactional(readOnly = true)
    public Optional<PostResponseDto> findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        post.increaseView();
        return Optional.of(PostResponseDto.from(post));
    }

    @Transactional
    public PostResponseDto create(PostRequestDto postRequestDto, MemberDetails member) {
        Post post = postRequestDto.toEntity();
        Member writerMember = memberRepository.findByName(member.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Invalid authentication!")
        );
        post.setMappingMember(writerMember);

        post.setComments(List.of());
        return PostResponseDto.from(postRepository.save(post));
    }

    @Transactional
    public PostResponseDto update(Long id, PostRequestDto postRequestDto, String userName) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        System.out.println(post.getMember().getName());
        if (!post.getMember().getName().equals(userName)) {
            return null;
        }else {
            post.update(postRequestDto.getTitle(), postRequestDto.getContent());
            postRepository.save(post);
            return PostResponseDto.from(post);
        }
    }

    @Transactional
    public boolean delete(Long id, String userName) {

        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        if (!post.getMember().getName().equals(userName)) {
            return false;
        }
        postRepository.deleteById(id);
        return true;
    }
}
