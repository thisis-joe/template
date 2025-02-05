package com.example.template.domain.post.post.controller;

import com.example.template.domain.post.post.entity.Post;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
public class PostController {
    private List<Post> posts = new ArrayList();
    private long lastId = 3L;

    public PostController() {
        Post p1 = Post.builder()
                .id(1L)
                .title("title1")
                .content("title1")
                .build();

        Post p2 = Post.builder()
                .id(2L)
                .title("title2")
                .content("title2")
                .build();

        Post p3 = Post.builder()
                .id(3L)
                .title("title3")
                .content("title3")
                .build();

        posts.add(p1);
        posts.add(p2);
        posts.add(p3);
    }
    @GetMapping("/write")
    public String showWrite() {
        return "domain/post/post/write";
    }

    @AllArgsConstructor
    @Getter
    public static class WriteForm {
        @NotBlank(message = "01-제목을 입력해주세요.")
        @Length(min=5, message = "02-제목은 5글자 이상입니다.")
        private String title;

        @NotBlank(message = "03-내용을 입력해주세요.")
        @Length(min=10, message = "04-내용은 10글자 이상입니다.")
        private String content;
    }
    @PostMapping("/write")
    // @ResponseBody를 빼면 반환값을 템플릿으로 인식한다.
    public String doWrite(@Valid WriteForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            //return "뭔가 실패했음";
            String errorMessage = bindingResult.getFieldErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .sorted()
                    .map(msg -> msg.split("-")[1])
                    .collect(Collectors.joining("<br>"));

            return "domain/post/post/write";
        }

        Post post = Post.builder()
                .id(++lastId)
                .title(form.getTitle())
                .content(form.getContent())
                .build();

        posts.add(post);

        //return showList();
        return "redirect:/posts"; //리다이렉트
    }

    private String getFormHtml(String errorMsg, String title, String content) {
        return """
                    <div>%s</div>
                    <form method="post">
                      <input type="text" name="title" placeholder="제목" value="%s"/> <br>
                      <textarea name="content">%s</textarea> <br>
                      <input type="submit" value="등록" /> <br>
                    </form>
                    """.formatted(errorMsg,title,content);
    }

    @GetMapping
    @ResponseBody
    private String showList() {

        String lis = posts.stream()
                .map(p -> "<li>" + p.getTitle() + "</li>")
                .collect(Collectors.joining());

        String ul = "<ul>" + lis + "</ul>";

        return """
                <div>글 목록</div>
                %s
               <a href="/posts/write">글쓰기</a>
               """.formatted(ul);
    }
}