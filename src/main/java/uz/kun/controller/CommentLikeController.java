package uz.kun.controller;
//User :Lenovo
//Date :15.06.2022
//Time :19:18
//Project Name :Kun.uz

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.kun.dto.CommentLikeDTO;
import uz.kun.service.ArticleLikeService;
import uz.kun.service.CommentLikeService;
import uz.kun.util.HttpHeaderUtil;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/comment_like")
@RestController
@Api(tags = "Comment Like Controller ")
public class CommentLikeController {


    @Autowired
    private CommentLikeService service;

    @PostMapping("/like")
    public ResponseEntity<Void> like(@RequestBody CommentLikeDTO dto,
                                     HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        service.commentLike(dto.getCommentId(), profileId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dislike")
    public ResponseEntity<Void> dislike(@RequestBody CommentLikeDTO dto,
                                        HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        service.commentDisLike(dto.getCommentId(), profileId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> remove(@RequestBody CommentLikeDTO dto,
                                       HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        service.removeLike(dto.getCommentId(), profileId);
        return ResponseEntity.ok().build();
    }


}
