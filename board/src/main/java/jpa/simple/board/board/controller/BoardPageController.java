package jpa.simple.board.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardPageController {

    @GetMapping("/list")
    public String boardListPage() {
        return "list";
    }

    @GetMapping("/read")
    public String boardReadPage() {
        return "read";
    }

    @GetMapping("/update")
    public String boardUpdatePage() {
        return "update";
    }

    @GetMapping("/create")
    public String boardCreatePage() {
        return "create";
    }
}
