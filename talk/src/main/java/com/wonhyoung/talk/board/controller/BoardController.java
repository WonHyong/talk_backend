package com.bezkoder.spring.datajpa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.datajpa.model.Board;
import com.bezkoder.spring.datajpa.repository.BoardRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class BoardController {

	@Autowired
	BoardRepository BoardRepository;

	@GetMapping("/boards")
	public ResponseEntity<List<Board>> getAllBoards(@RequestParam(required = false) String title) {
		try {
			List<Board> boards = new ArrayList<Board>();

			if (title == null)
				BoardRepository.findAll().forEach(boards::add);
			else
				BoardRepository.findByTitleContaining(title).forEach(boards::add);

			if (boards.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(boards, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/boards/{id}")
	public ResponseEntity<Board> getBoardById(@PathVariable("id") long id) {
		Optional<Board> BoardData = BoardRepository.findById(id);

		if (BoardData.isPresent()) {
			return new ResponseEntity<>(BoardData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/boards")
	public ResponseEntity<Board> createBoard(@RequestBody Board Board) {
		try {
			Board _board = BoardRepository
					.save(new Board(Board.getTitle(), Board.getDescription(), false));
			return new ResponseEntity<>(_board, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/bos/{id}")
	public ResponseEntity<Board> updateBoard(@PathVariable("id") long id, @RequestBody Board Board) {
		Optional<Board> BoardData = BoardRepository.findById(id);

		if (BoardData.isPresent()) {
			Board _board = BoardData.get();
			_board.setTitle(Board.getTitle());
			_board.setDescription(Board.getDescription());
			_board.setPublished(Board.isPublished());
			return new ResponseEntity<>(BoardRepository.save(_board), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/boards/{id}")
	public ResponseEntity<HttpStatus> deleteBoard(@PathVariable("id") long id) {
		try {
			BoardRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/boards")
	public ResponseEntity<HttpStatus> deleteAllBoards() {
		try {
			BoardRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/boards/published")
	public ResponseEntity<List<Board>> findByPublished() {
		try {
			List<Board> Boards = BoardRepository.findByPublished(true);

			if (Boards.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(Boards, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
