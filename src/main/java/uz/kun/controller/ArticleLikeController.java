package uz.kun.controller;
//User :Lenovo
//Date :15.06.2022
//Time :19:18
//Project Name :Kun.uz

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequestMapping("/article_like")
@RestController
@Api(tags = "Article Like Controller ")
public class ArticleLikeController {
    @Autowired
    private ArticleLikeService service;
    @PostMapping("/like")
    @ApiOperation(value = "Method for LIKE")
    public ResponseEntity<Void> like(@RequestBody ArticleLikeDTO dto,
                                     HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        service.articleLike(dto.getArticleId(), profileId);
        log.info("Article like {}",dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dislike")
    @ApiOperation(value = "Method for DISLIKE")
    public ResponseEntity<Void> dislike(@RequestBody ArticleLikeDTO dto,
                                        HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        log.info("Article dislike {}",dto);
        service.articleDisLike(dto.getArticleId(), profileId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    @ApiOperation(value = "Method for remove (LIKE OR DISLIKE) ")

    public ResponseEntity<Void> remove(@RequestBody ArticleLikeDTO dto,
                                       HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        service.removeLike(dto.getArticleId(), profileId);
        log.info("Article remove(LIKE OR DISLIKE) {}",dto);
        return ResponseEntity.ok().build();
    }


}
