package com.wonhyong.talk.board.service;

import com.wonhyong.talk.board.domain.Like;
import com.wonhyong.talk.board.domain.Post;
import com.wonhyong.talk.board.dto.PostDto;
import com.wonhyong.talk.board.repository.CommentRepository;
import com.wonhyong.talk.board.repository.LikeRepository;
import com.wonhyong.talk.board.repository.PostRepository;
import com.wonhyong.talk.member.domain.Member;
import com.wonhyong.talk.member.domain.MemberDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Transactional(readOnly = true)
    public boolean isExistById(@NonNull Long id) {
        return postRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public PostDto.DetailResponse findById(MemberDetails member, @NonNull Long id) throws NoSuchElementException {
        Post post = findPostById(id);

        // Check already liked
        List<Like> likes = post.getLikes();
        int like = (likes.stream().anyMatch(l -> l.getMember().equals(member.getMember())))?
                likes.size() * -1
                : likes.size();

        int commentNum = commentRepository.countAllByPost_Id(post.getId());

        return PostDto.DetailResponse.from(post, like, commentNum);
    }

    @Transactional
    public void increaseView(@NonNull Long id) {
        postRepository.increaseView(id);
    }

    @Transactional
    public PostDto.DetailResponse create(MemberDetails member, @NonNull PostDto.Request postDto) throws NoSuchElementException {
        Member currentUser = member.getMember();

        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .writer(currentUser)
                .build();

        postRepository.save(post);

        return PostDto.DetailResponse.from(post, 0, 0);
    }

    @Transactional
    public PostDto.DetailResponse update(Long id, MemberDetails member, @NonNull PostDto.Request postDto) throws NoSuchElementException, UsernameNotFoundException, IllegalAccessException {
        Post post = findPostById(id);
        Member currentUser = member.getMember();

        checkIsWriter(currentUser, post.getWriter());

        post.update(postDto.getTitle(), postDto.getContent());
        postRepository.save(post);

        return findById(member, id);
    }

    @Transactional
    public void delete(Long id, MemberDetails member) throws NoSuchElementException, UsernameNotFoundException, IllegalAccessException {
        Post post = findPostById(id);
        Member currentUser = member.getMember();

        checkIsWriter(currentUser, post.getWriter());

        postRepository.deleteById(id);
    }

    @Transactional
    public boolean increaseLike(@NonNull Long id, MemberDetails member) {
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

    private void checkIsWriter(Member currentUser, Member writer) throws IllegalAccessException {
        if (!currentUser.equals(writer)) {
            throw new IllegalAccessException("NO PERMISSION");
        }
    }
}