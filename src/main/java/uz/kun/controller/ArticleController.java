package uz.kun.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import uz.kun.dto.*;
import uz.kun.enums.Lang;
import uz.kun.enums.ProfileRole;
import uz.kun.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.util.HttpHeaderUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/article")
@RestController
@Controller
@Slf4j
@Api(tags = "Article controller ")
public class ArticleController {
    @Autowired
    private ArticleService articleService;


    @PostMapping("/moderator/create")
    @ApiOperation(value = " Method for create article only MODERATOR ")
    public ResponseEntity<ArticleDTO> create(@RequestParam("data") @Valid ArticleCreateDTO dto,HttpServletRequest request,@RequestParam("file") MultipartFile file) {
        log.info("Article creating : {}",dto,file);
        Integer id = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        ArticleDTO response = articleService.create(dto, id,file);
        return ResponseEntity.ok().body(response);
    }
    ///CREATE OK
    @PutMapping("/moderator/update/{id}")
    @ApiOperation(value = " Method for update article only MODERATOR ")
    public ResponseEntity<String> update(@PathVariable("id") String id,@Valid @RequestBody ArticleUpdateDTO dto, HttpServletRequest request) {
        log.info("Article updating : {}",dto);
        Integer profileid = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        articleService.update(id,dto, profileid);
        return ResponseEntity.ok().body("Successfully updated");
    }
    ///UPDATE OK
    @GetMapping("/moderator/getAllForModerator")
    @ApiOperation(value = " Method for get all article only ( MODERATOR ) ")

    public ResponseEntity<?> getAllForModerator(   HttpServletRequest request) {
        Integer id = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        List<ArticleDTO> response = articleService.getAll();
        log.info("Articles getting : {}",response);
        return ResponseEntity.ok().body(response);
    }
    ///GET ALL FOR MODERATOR OK
    @GetMapping("/getAll")
    @ApiOperation(value = " Method for create article ( OPEN API ) ")
    public ResponseEntity<List<ArticleDTO>> getAll() {
        List<ArticleDTO> response = articleService.getPublishedArticleList();
        log.info("Published articles getting : {}",response);
        return ResponseEntity.ok().body(response);
    }

///GET ALL OK

    @ApiOperation(value = " Method for publish article(Not publish) only MODERATOR ")
    @PutMapping("/moderator/publish/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, HttpServletRequest request) {
        Integer profileid = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        log.info("Article set publish : {}",id,profileid);
       articleService.publish(id, profileid);
        return ResponseEntity.ok().body("Successfully published");
    }



    ///PUBLISH OK
      @DeleteMapping("/moderator/delete/{id}")
      @ApiOperation(value = " Method for delete article only MODERATOR ")

      public ResponseEntity<ArticleDTO> delete(@PathVariable("id") String id,
                                             HttpServletRequest request) {
          Integer id1 = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
          log.info("Article set delete : {}",id,id1);
       articleService.delete(id);
        return ResponseEntity.ok().build();
    }

    ///DELETE OK



    @GetMapping("/get5LastByCategoryKey/{key}")
    @ApiOperation(value = " Method for Get last 5 category ( OPEN API ) ")
    public ResponseEntity<?> getLast5ByCategoryKey(@PathVariable("key") String key) {
    List<ArticleDTO> response = articleService.getLastByCategoryKey(key);
        log.info("Get last 5 category  : {}",key,response);
    return ResponseEntity.ok().body(response);
}


    ///GET LAST 5 BY CATEGORY KEY OK



    @GetMapping("/getLastByTypes/{key}/{count}")
    @ApiOperation(value = " Method for Get last By Types ( OPEN API ) ")
    public ResponseEntity<?> getLast5ByType(@PathVariable("key") String key,@PathVariable("count")  Integer count) {
        List<ArticleDTO> response = articleService.getLastByType(key,count);
        log.info("Get last By Types : {}",key);
        return ResponseEntity.ok().body(response);
    }







    @ApiOperation(value = " Method for last 8 Articles Wich Id Not Included ( OPEN API ) ")
    @GetMapping("/getLast8ArticlesWichIdNotIncluded")
    public ResponseEntity<?>getLast8ArticlesWichIdNotIncluded(@RequestBody ArticleRequestIDsDTO ids)
    {

        List<ArticleDTO> response = articleService.getLast8NotIn(ids);
        log.info("Get last 8 Articles Wich Id Not Included : {}",ids);
        return ResponseEntity.ok(response);
    }
 @GetMapping("/get4MostReadArticles")
 @ApiOperation(value = " Method for  4 Most Read Articles ( OPEN API ) ")

 public ResponseEntity<?>get4MostReadArticles()
    {
        List<ArticleDTO> response = articleService.get4MostReadArticles();
        log.info("Getting 4 Most Read Articles ");
        return ResponseEntity.ok(response);
    }
    @ApiOperation(value = " Method for  Get Article By Id And Lang ( OPEN API ) ")

@GetMapping("/getArticleByIdAndLan")
    public ResponseEntity<?> getArticleByIdAndLan(@RequestBody String key,@RequestHeader(value = "Accept-Language", defaultValue = "uz") Lang lang) {
        List<ArticleDTO> response = articleService.getLastByTypeAndByLang(key,lang);
        log.info("Get Article By Id And Lang : {}",lang);
        return ResponseEntity.ok().body(response);
    }


    @ApiOperation(value = " Method for  Last 4 Article By Tag Name ( OPEN API ) ")
@GetMapping("/getLast4ArticleByTagName")
    public ResponseEntity<?> getLast4ArticleByTagName(@RequestBody ArticleByTagNameRequestDTO tagname) {
        List<ArticleDTO> response = articleService.getLast4ArticleByTagName(tagname);
    log.info("Get Last 4 Article By Tag Name: {}",tagname);
        return ResponseEntity.ok().body(response);
    }
    @ApiOperation(value = " Method for  Last 5 Article By Types And By Region Key ( OPEN API ) ")
@GetMapping("/getLast5ArticleByTypesAndByRegionKey")
    public ResponseEntity<?> getLast4ArticleByTagName(@RequestBody ArticleByTypesAndRegionKeyRequestDTO tagname) {
        List<ArticleDTO> response = articleService.getLast5ArticleByTypesAndByRegionKey(tagname);
    log.info("Get Last 5 Article By Types And By Region Key: {}",tagname);
        return ResponseEntity.ok().body(response);
    }
    @ApiOperation(value = " Method for Article List By Region Key ( OPEN API ) ")
@GetMapping("/getArticleListByRegionKey/{key}")
    public ResponseEntity<?> getArticleListByRegionKey(@PathVariable("key") String regionKey,@RequestParam(value = "page",required = false) int page, @RequestParam(value = "size",required = false) int size) {
        List<ArticleDTO> response = articleService.getArticleListByRegionKey(regionKey,page,size);
    log.info("Get Article List By Region Key: {}",regionKey);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = " Method for Get Article List By Category Key + ( Pagination ) ( OPEN API ) ")
    @GetMapping("/getArticleListByCategoryKey/{key}")
    public ResponseEntity<?> getArticleListByCategoryKey(@PathVariable("key") String regionKey,@RequestParam("page") int page, @RequestParam("size") int size) {
        List<ArticleDTO> response = articleService.getArticleListByCategoryKey(regionKey,page,size);
        log.info(" Get Article List By Category Key {}",regionKey);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = " Method for Get 5 Article List By Category Key ( OPEN API ) ")
    @GetMapping("/get5ArticleByCategoryKey/{key}")
    public ResponseEntity<?> get5ArticleListByCategoryKey(@PathVariable("key") String regionKey) {
        List<ArticleDTO> response = articleService.get5ArticleByCategoryKey(regionKey);
        log.info(" Get 5 Article List By Category Key {}",regionKey);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = " Method for Increase Article View Count ( OPEN API ) ")
    @PostMapping("/increaseArticleViewCount/{key}")
    public ResponseEntity<?> increaseArticleViewCount(@PathVariable("key") String id) {
             articleService.increaseArticleViewCount(id);
        log.info(" Increase Article View Count  {}",id);
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = " Method for Filter ( OPEN API ) ")
    @PostMapping("/filter")
    public ResponseEntity<List<ArticleDTO>> filter(@RequestBody  ArticleFilterDTO dto) {
        List<ArticleDTO> response = articleService.filter(dto);
        log.info(" Article Filter {}",dto);
        return ResponseEntity.ok().body(response);
    }


}
