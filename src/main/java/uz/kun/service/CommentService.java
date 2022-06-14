package uz.kun.service;
//User :Lenovo
//Date :12.06.2022
//Time :23:21
//Project Name :lesson_14_kun_uz

import uz.kun.dto.ArticleDTO;
import uz.kun.dto.CommentCreateDTO;
import uz.kun.dto.CommentDTO;
import uz.kun.dto.ProfileDTO;
import uz.kun.entity.ArticleEntity;
import uz.kun.entity.CommentEntity;
import uz.kun.entity.ProfileEntity;
import uz.kun.exps.ItemNotFoundException;
import uz.kun.repository.ArticleRepository;
import uz.kun.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {


    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;


    public void save(Integer pID, CommentCreateDTO commentDTO) {

        Optional<ArticleEntity> byId = articleRepository.findById1(commentDTO.getArticle_id());
        if (byId.isEmpty())
        {
            throw new ItemNotFoundException("Article not found");
        }
        ArticleEntity articleEntity = byId.get();

        articleRepository.save(articleEntity);
        CommentEntity reply=new CommentEntity();
        reply.setId(commentDTO.getReply_comment_id());

        CommentEntity comment=new CommentEntity();
        comment.setContent(commentDTO.getContent());
        ProfileEntity profile=new ProfileEntity();
        if (commentDTO.getReply_comment_id()!=0)
        {
            comment.setComment(reply);
        }
        profile.setId(pID);
        comment.setProfile(profile);
        comment.setArticle(articleEntity);
        commentRepository.save(comment);
    }


    public List<CommentDTO> getOwnComment(Integer pID) {


        List<CommentEntity> commentsByProfileId = commentRepository.getCommentsByProfileId(pID);
        if (commentsByProfileId.isEmpty())
        {
            throw new ItemNotFoundException("Comments not found");

        }

        List<CommentDTO> dtos=new LinkedList<>();

        for (CommentEntity comment : commentsByProfileId) {
            ArticleDTO articleDTO=new ArticleDTO();
            articleDTO.setId(comment.getArticle().getId());
            ProfileDTO profileDTO=new ProfileDTO();
            profileDTO.setId(comment.getProfile().getId());

            CommentDTO dto=new CommentDTO();
            dto.setArticle(articleDTO);
            dto.setProfile(profileDTO);
            dto.setCreated_date(comment.getCreatedDate());
            dto.setContent(comment.getContent());
            dto.setUpdated_time(comment.getUpdatedDate());
            dto.setId(comment.getId());
            dtos.add(dto);
        }
        return dtos;

    }

    public void update(Integer pID, Integer id, CommentDTO dto) {

        Optional<CommentEntity> byId = commentRepository.findByProfileIdAndId(pID,id);
        if (byId.isEmpty())
        {
            throw new ItemNotFoundException("Comment not found");
        }
        CommentEntity comment = byId.get();
        comment.setContent(dto.getContent());
        comment.setUpdatedDate(LocalDateTime.now());
        commentRepository.save(comment);


    }

    public void delete(Integer pID, Integer id) {

        Optional<CommentEntity> commentEntity = commentRepository.findByProfileIdAndId(pID, id);
        if (commentEntity.isEmpty()){

            throw new ItemNotFoundException("Comment not found");

        }
        CommentEntity comment = commentEntity.get();
        comment.setVisible(false);
        commentRepository.save(comment);


    }
}
