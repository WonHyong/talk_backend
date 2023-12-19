package com.bezkoder.spring.datajpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.datajpa.model.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	List<Board> findByPublished(boolean published);
	List<Board> findByTitleContaining(String title);
}
