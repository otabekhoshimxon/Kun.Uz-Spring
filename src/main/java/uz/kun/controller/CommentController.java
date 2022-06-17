package uz.kun.controller;
//User :Lenovo
//Date :12.06.2022
//Time :23:20
//Project Name :lesson_14_kun_uz
import uz.kun.dto.CommentCreateDTO;
import uz.kun.dto.CommentDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.util.HttpHeaderUtil;
import uz.kun.util.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {



    @Autowired
    private CommentService commentService;


    @PostMapping("/create")
    public ResponseEntity<?> create( @RequestBody CommentCreateDTO commentDTO,HttpServletRequest request) {
        Integer profileid = HttpHeaderUtil.getId(request, ProfileRole.USER);
        commentService.save(profileid,commentDTO);
        return ResponseEntity.ok("ok");
    }
@GetMapping("/getAllComment")
    public ResponseEntity<?> getAll(@RequestHeader("Token") String jwt)
    {
        Integer pID = JWTUtil.decode(jwt, ProfileRole.USER);
        List<CommentDTO> ownComment = commentService.getOwnComment(pID);
        return ResponseEntity.ok(ownComment);
    }

@PutMapping("/update/{id}")
    public ResponseEntity<?> getAll(@PathVariable("id")Integer id,@RequestBody CommentDTO dto ,HttpServletRequest request) {
    Integer profileid = HttpHeaderUtil.getId(request, ProfileRole.USER);
        commentService.update(profileid,id,dto);
        return ResponseEntity.ok("updated");
    }
@DeleteMapping("/delete/{id}")
    public ResponseEntity<?> getAll(@PathVariable("id")Integer id,HttpServletRequest request) {
    Integer profileid = HttpHeaderUtil.getId(request, ProfileRole.USER);
        commentService.delete(profileid,id);
        return ResponseEntity.ok("updated");
    }







}
