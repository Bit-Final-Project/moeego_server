package article.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import article.bean.ArticleDTO;
import article.service.ArticleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ArticleController {

	private final ArticleService articleService;

	// 한 페이지에 출력할 게시글 수
	private int pageSize = 10;


	// 전체 게시글 조회 페이징
	@GetMapping("/article")
	public ResponseEntity<Map<String, Object>> getArticles(@RequestParam(value = "pg", required = false, defaultValue = "1") int pg) { 

	    Page<ArticleDTO> articlePage = articleService.getArticleListByPage(pg, pageSize);

	    Map<String, Object> response = new HashMap<>();
	    response.put("content", articlePage.getContent());  // 현재 페이지의 콘텐츠 (게시글 목록)
	    response.put("totalPages", articlePage.getTotalPages());  // 전체 페이지 수
	    response.put("currentPage", articlePage.getNumber() + 1);  // 현재 페이지 번호 (0부터 시작하므로 +1)
	    response.put("totalElements", articlePage.getTotalElements());  // 전체 게시글 수

	    return ResponseEntity.ok(response);
	}
	
	// 타입별 게시글 목록
	@GetMapping("/article/{type}")
	public ResponseEntity<Map<String, Object>> getArticlesByType(
	        @PathVariable("type") String type,
	        @RequestParam(value = "pg", required = false, defaultValue = "1") int pg) {

	    int typeCode;
	    switch (type) {
	        case "notices":
	            typeCode = 0;
	            break;
	        case "event":
	            typeCode = 1;
	            break;
	        case "free":
	            typeCode = 2;
	            break;
	        case "qna":
	            typeCode = 3;
	            break;
	        case "pro":
	            typeCode = 4;
	            break;
	        default:
	            return ResponseEntity.badRequest().build(); 
	    }

	    Page<ArticleDTO> articlePage = articleService.getTypeArticles(pg, pageSize, typeCode);

	    Map<String, Object> response = new HashMap<>();
	    response.put("content", articlePage.getContent());  // 현재 페이지의 콘텐츠 (게시글 목록)
	    response.put("totalPages", articlePage.getTotalPages());  // 전체 페이지 수
	    response.put("currentPage", articlePage.getNumber() + 1);  // 현재 페이지 번호 (0부터 시작하므로 +1)
	    response.put("totalElements", articlePage.getTotalElements());  // 전체 게시글 수

	    return ResponseEntity.ok(response);
	}
	

	// 인기글 좋아요 순 목록 페이징
	@GetMapping("/article/hot")
	public ResponseEntity<Map<String, Object>> getHotArticles(@RequestParam(value = "pg", required = false, defaultValue = "1") int pg) {

	    Page<ArticleDTO> articlePage = articleService.getHotArticleByPage(pg, pageSize);

	    Map<String, Object> response = new HashMap<>();
	    response.put("content", articlePage.getContent());  // 현재 페이지의 콘텐츠 (게시글 목록)
	    response.put("totalPages", articlePage.getTotalPages());  // 전체 페이지 수
	    response.put("currentPage", articlePage.getNumber() + 1);  // 현재 페이지 번호 (0부터 시작하므로 +1)
	    response.put("totalElements", articlePage.getTotalElements());  // 전체 게시글 수

	    return ResponseEntity.ok(response);
	}

	// 게시글 상세보기
	@GetMapping("/article/viewpage")
	public ResponseEntity<ArticleDTO> getArticleView(@RequestParam("article_no") Long articleNo) {
		
		ArticleDTO article = articleService.getArticleViewById(articleNo);

		return ResponseEntity.ok(article);
	}
	
	// 마이페이지 작성한 게시글 보기
	@GetMapping("/article/myPage")
	public ResponseEntity<Map<String, Object>> getMyArticles( @RequestParam(name = "member_no") Long member_no,
	        											   @RequestParam(value = "pg", required = false, defaultValue = "1") int pg) {
	   
		Page<ArticleDTO> articleList = articleService.getMyArticles(member_no, pg, pageSize);

		Map<String, Object> response = new HashMap<>();
		response.put("content", articleList.getContent());  // 현재 페이지의 콘텐츠 (게시글 목록)
	    response.put("totalPages", articleList.getTotalPages());  // 전체 페이지 수
	    response.put("currentPage", articleList.getNumber() + 1);  // 현재 페이지 번호 (0부터 시작하므로 +1)
	    response.put("totalElements", articleList.getTotalElements());  // 전체 게시글 수

	    return ResponseEntity.ok(response);
	}
	
}
