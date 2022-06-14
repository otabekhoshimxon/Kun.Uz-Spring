package uz.kun.controller;
//User :Lenovo
//Date :12.06.2022
//Time :23:20
//Project Name :lesson_14_kun_uz
import uz.kun.dto.CommentCreateDTO;
import uz.kun.dto.CommentDTO;
import uz.kun.enums.ProfileRole;
import uz.kun.service.CommentService;
import uz.kun.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestHeader("Token") String jwt, @RequestBody CommentCreateDTO commentDTO)
    {
        Integer pID = JwtUtil.decode(jwt, ProfileRole.USER);
        commentService.save(pID,commentDTO);
        return ResponseEntity.ok("ok");
    }
@GetMapping("/getAllComment")
    public ResponseEntity<?> getAll(@RequestHeader("Token") String jwt)
    {
        Integer pID = JwtUtil.decode(jwt, ProfileRole.USER);
        List<CommentDTO> ownComment = commentService.getOwnComment(pID);
        return ResponseEntity.ok(ownComment);
    }

@PutMapping("/update/{id}")
    public ResponseEntity<?> getAll(@RequestHeader("Token") String jwt,@PathVariable("id")Integer id,@RequestBody CommentDTO dto)
    {
        Integer pID = JwtUtil.decode(jwt, ProfileRole.USER);
        commentService.update(pID,id,dto);
        return ResponseEntity.ok("updated");
    }
@DeleteMapping("/delete/{id}")
    public ResponseEntity<?> getAll(@RequestHeader("Token") String jwt,@PathVariable("id")Integer id)
    {
        Integer pID = JwtUtil.decode(jwt, ProfileRole.USER);
        commentService.delete(pID,id);
        return ResponseEntity.ok("updated");
    }







}
