package com.wonhyong.talk.board;

import com.wonhyong.talk.board.model.Board;
import com.wonhyong.talk.board.repository.BoardRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class BoardDataLoader {
    private final BoardRepository boardRepository;

    public BoardDataLoader(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @PostConstruct
    private void loadBoardData() {
        List<Board> sampleBoards = new ArrayList<>(100);

        for (int i=0; i<100; i++) {
            sampleBoards.add(new Board("test" + i, i + "th test"));
        }

        boardRepository.saveAll(sampleBoards);
    }
}
