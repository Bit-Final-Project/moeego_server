package com.ncp.moeego.article.service;

import com.ncp.moeego.article.bean.Article;
import com.ncp.moeego.article.bean.ArticleDTO;
import com.ncp.moeego.article.repository.ArticleRepository;
import com.ncp.moeego.comment.repository.CommentRepository;
import com.ncp.moeego.common.Date;
import com.ncp.moeego.member.entity.Member;
import com.ncp.moeego.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
   
    // memberNo맞춰서 이름 가져오는 로직
    public String getMemberNameByMemberNo(Long memberNo) {
        Optional<Member> member = memberRepository.findById(memberNo); // memberNo로 Member 조회

        return member.map(Member::getName).orElse("Unknown"); // Member가 없으면 "Unknown" 반환
    }

    @Override
    public List<Article> getArticleList(int type) {
        return articleRepository.findAllByType(type);
    }

    @Override
    public void write(Article article) {
        articleRepository.save(article);

    }

    @Override
    public Article getEventList(int num) {
        return articleRepository.findByArticleNo(num);
    }

    @Override
    public void update(Article article) {
        articleRepository.save(article);

    }

    @Override
    public void deleteByArticleNo(int num) {
        articleRepository.deleteById((long) num);
    }

    @Override
    public List<Article> searchArticles(String subject, String content) {
        return articleRepository.searchArticles(subject, content);
    }

    @Override
    public List<Article> searchSubjectArticles(String subject) {
        return articleRepository.searchSubjectArticles(subject);
    }

    @Override
    public List<Article> searchContentArticles(String content) {
        return articleRepository.searchContentArticles(content);
    }

    // 좋아요 순으로 조회(인기 게시글)
    @Override
    public Page<ArticleDTO> getHotArticleByPage(int pg, int pageSize) {
        // Pageable 객체 생성: likes 기준 내림차순 정렬
        Pageable pageable = PageRequest.of(pg - 1, pageSize, Sort.by(Sort.Order.desc("likes")));

        // ArticleRepository에서 좋아요 순으로 페이징 처리된 결과 조회
        Page<Article> articlePage = articleRepository.findAll(pageable);

        // Article 객체를 ArticleDTO로 변환하여 반환
        return articlePage.map(article -> {
            String elapsedTime = Date.calculateDate(article.getWriteDate()); // 경과 시간 계산
            String memberName = getMemberNameByMemberNo(article.getMemberNo().getMemberNo()); // 회원 이름 가져오기
            int commentCount = commentRepository.countByArticleNo(article.getArticleNo());
            return new ArticleDTO(
            		article.getArticleNo(), 
            		article.getSubject(), 
            		article.getContent(), 
            		article.getView(),
                    article.getType(), 
                    article.getWriteDate(), 
                    article.getMemberNo().getMemberNo(), 
                    article.getLikes(),
                    elapsedTime, 
                    memberName,
                    article.getService(),
                    article.getArea(),
                    commentCount);
        });
    }


    
    // 전체 게시글 조회 페이징 성능 개선 후
    @Override
    public Page<ArticleDTO> getArticleListByPage(int pg, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pg - 1, pageSize, Sort.by(Sort.Order.desc("writeDate")));

        // 성능 개선을 위한 단일 쿼리로 댓글 수 가져오기
        Page<Object[]> articlePage = articleRepository.findArticlesWithCommentCount(pageRequest);

        return articlePage.map(result -> {
            Article article = (Article) result[0];
            Long commentCount = (Long) result[1];

            String elapsedTime = Date.calculateDate(article.getWriteDate());
            String memberName = getMemberNameByMemberNo(article.getMemberNo().getMemberNo());

            return new ArticleDTO(
                    article.getArticleNo(), 
                    article.getSubject(), 
                    article.getContent(), 
                    article.getView(),
                    article.getType(), 
                    article.getWriteDate(), 
                    article.getMemberNo().getMemberNo(), 
                    article.getLikes(),
                    elapsedTime, 
                    memberName,
                    article.getService(),
                    article.getArea(),
                    commentCount.intValue());
        });
    }
    
    
//    // 전체 게시글 조회 페이징 성능 개선 전
//    @Override
//    public Page<ArticleDTO> getArticleListByPage(int pg, int pageSize) {
//        PageRequest pageRequest = PageRequest.of(pg - 1, pageSize, Sort.by(Sort.Order.desc("writeDate")));
//
//        Page<Article> articlePage = articleRepository.findAll(pageRequest);
//        
//        // Article 객체를 ArticleDTO로 변환하여 Page<ArticleDTO>로 반환
//        return articlePage.map(article -> {
//            String elapsedTime = Date.calculateDate(article.getWriteDate());
//            String memberName = getMemberNameByMemberNo(article.getMemberNo().getMemberNo());
//            int commentCount = commentRepository.countByArticleNo(article.getArticleNo());
//            return new ArticleDTO(
//            		article.getArticleNo(), 
//            		article.getSubject(), 
//            		article.getContent(), 
//            		article.getView(),
//                    article.getType(), 
//                    article.getWriteDate(), 
//                    article.getMemberNo().getMemberNo(), 
//                    article.getLikes(),
//                    elapsedTime, 
//                    memberName,
//                    article.getService(),
//                    article.getArea(),
//                    commentCount);
//        });
//    }
    
    
    
    
    // Type 별 게시판 조회
    @Override
    public Page<ArticleDTO> getTypeArticles(int pg, int pageSize, int type) {
        PageRequest pageRequest = PageRequest.of(pg - 1, pageSize, Sort.by(Sort.Order.desc("writeDate")));

        // 성능 개선을 위한 단일 쿼리로 댓글 수 가져오기
        Page<Object[]> articlePage = articleRepository.findTypeArticlesWithCommentCount(type, pageRequest);

        return articlePage.map(result -> {
            Article article = (Article) result[0];
            Long commentCount = (Long) result[1];

            String elapsedTime = Date.calculateDate(article.getWriteDate());
            String memberName = getMemberNameByMemberNo(article.getMemberNo().getMemberNo());

            return new ArticleDTO(
                article.getArticleNo(),
                article.getSubject(),
                article.getContent(),
                article.getView(),
                article.getType(),
                article.getWriteDate(),
                article.getMemberNo().getMemberNo(),
                article.getLikes(),
                elapsedTime,
                memberName,
                article.getService(),
                article.getArea(),
                commentCount.intValue()
            );
        });
    }

    // 게시글 상세 조회
    @Override
    public ArticleDTO getArticleViewById(Long articleNo) {
        Optional<Article> list = articleRepository.findById(articleNo);

        // 게시글이 없으면 null 반환
        if (!list.isPresent()) {
            return null;
        }
        
        int commentCount = commentRepository.countByArticleNo(articleNo);
        Article article = list.get();
        // 게시글이 존재하면 ArticleDTO로 변환하여 반환
        String elapsedTime = Date.calculateDate(article.getWriteDate());
        String memberName = getMemberNameByMemberNo(article.getMemberNo().getMemberNo());
        return new ArticleDTO(
        		article.getArticleNo(), 
        		article.getSubject(), 
        		article.getContent(), 
        		article.getView(),
                article.getType(), 
                article.getWriteDate(), 
                article.getMemberNo().getMemberNo(), 
                article.getLikes(),
                elapsedTime, 
                memberName,
                article.getService(),
                article.getArea(),
                commentCount);

    }

    // 마이페이지 작성한 게시글 조회
    @Override
    public Page<ArticleDTO> getMyArticles(Long member_no, int pg, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pg - 1, pageSize, Sort.by(Sort.Order.desc("writeDate")));

        // 성능 개선을 위한 단일 쿼리로 댓글 수 가져오기
        Page<Object[]> articlePage = articleRepository.findMyArticlesWithCommentCount(member_no, pageRequest);

        return articlePage.map(result -> {
            Article article = (Article) result[0];
            Long commentCount = (Long) result[1];

            String elapsedTime = Date.calculateDate(article.getWriteDate());
            String memberName = getMemberNameByMemberNo(article.getMemberNo().getMemberNo());

            return new ArticleDTO(
                article.getArticleNo(),
                article.getSubject(),
                article.getContent(),
                article.getView(),
                article.getType(),
                article.getWriteDate(),
                article.getMemberNo().getMemberNo(),
                article.getLikes(),
                elapsedTime,
                memberName,
                article.getService(),
                article.getArea(),
                commentCount.intValue()
            );
        });
    }

    // 게시글 작성
    @Override
    public boolean writeArticle(ArticleDTO articleDTO) {

        try {

            // ArticleDTO를 Article 엔티티로 변환
            Article article = new Article();
            article.setSubject(articleDTO.getSubject());
            article.setContent(articleDTO.getContent());
            article.setView(0); // 초기 조회수는 0
            article.setLikes(0); // 초기 좋아요는 0
            article.setWriteDate(LocalDateTime.now()); // 현재 시간
            article.setType(articleDTO.getType());
            article.setService(articleDTO.getService());
            article.setArea(articleDTO.getArea());

            // 작성자 member_no
            Optional<Member> member = memberRepository.findById(articleDTO.getMemberNo());
            if (member.isPresent()) {
                article.setMemberNo(member.get());
            } else {
                return false;
            }

            // 게시글 저장
            articleRepository.save(article);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 게시글 수정
    @Override
    public boolean updateArticle(Long articleNo, ArticleDTO articleDTO) {
        try {
            // 게시글 가져오기
            Optional<Article> optionalArticle = articleRepository.findById(articleNo);
            if (optionalArticle.isPresent()) {
                Article article = optionalArticle.get();

                // 업데이트
                article.setSubject(articleDTO.getSubject());
                article.setContent(articleDTO.getContent());
//	            article.setView(articleDTO.getView()); 조회수 수정 X 
//	            article.setLikes(articleDTO.getLikes()); 좋아요 수정 X 
//	            article.setWriteDate(LocalDateTime.now()); 시간은 업데이트 하지않음
                article.setType(articleDTO.getType());
                article.setService(articleDTO.getService());
                article.setArea(articleDTO.getArea());

                // 게시글 저장
                articleRepository.save(article);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    // 게시글 삭제
    @Override
    public boolean deleteArticle(Long articleNo) {
        try {
            // 게시글 가져오기
            Optional<Article> article = articleRepository.findById(articleNo);
            if (article.isPresent()) {
                // 게시글 삭제
                articleRepository.deleteById(articleNo);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
