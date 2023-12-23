package com.wonhyong.talk;

import com.wonhyong.talk.board.model.Board;
import com.wonhyong.talk.board.repository.BoardRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class WonHyongTalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(WonHyongTalkApplication.class, args);
    }
}
