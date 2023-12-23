package com.wonhyong.talk.board.controller;

import com.wonhyong.talk.board.model.Board;
import com.wonhyong.talk.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardRepository boardRepository;

    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping
    public Iterable<Board> getAllBoards(Pageable pageable) {
        return boardRepository.findSliceBy(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Board> getBoardById(@PathVariable("id") long id) {
        return boardRepository.findById(id);
    }

    @PostMapping
    public Board postBoard(@RequestBody Board board) {
        return boardRepository.save(board);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Board> putBoard(@PathVariable("id") long id, @RequestBody Board board) {
        return (boardRepository.existsById(id))
                ? new ResponseEntity<>(boardRepository.save(board), HttpStatus.OK)
                : new ResponseEntity<>(boardRepository.save(board), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable("id") long id) {
        boardRepository.deleteById(id);
    }

}
