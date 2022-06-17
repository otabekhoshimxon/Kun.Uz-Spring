package uz.kun.controller;
//User :Lenovo
//Date :15.06.2022
//Time :19:18
//Project Name :Kun.uz

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.kun.dto.ArticleLikeDTO;
import uz.kun.service.ArticleLikeService;
import uz.kun.service.ArticleService;
import uz.kun.util.HttpHeaderUtil;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/article_like")
@RestController
public class ArticleLikeController {

    @Autowired
    private ArticleLikeService service;



    @PostMapping("/like")
    public ResponseEntity<Void> like(@RequestBody ArticleLikeDTO dto,
                                     HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        service.articleLike(dto.getArticleId(), profileId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dislike")
    public ResponseEntity<Void> dislike(@RequestBody ArticleLikeDTO dto,
                                        HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        service.articleDisLike(dto.getArticleId(), profileId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> remove(@RequestBody ArticleLikeDTO dto,
                                       HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        service.removeLike(dto.getArticleId(), profileId);
        return ResponseEntity.ok().build();
    }


}
