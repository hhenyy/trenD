//package com.td.TrenD.dao;
//
//import com.td.TrenD.model.TrendReVO;
//import com.td.TrenD.model.UserVO;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import javax.transaction.Transactional;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class TrendReRepositoryTest {
//
//	@Autowired
//	private TrendReRepository trendReRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
////	@Test
////	public void 전체_댓글_검색() {
////		List<TrendReVO> allComments = trendReRepository.findAll();
////		assertThat(allComments).isNotNull();
////		System.out.println("=========================");
////		System.out.println("전체 댓글 수: " + allComments.size());
////		System.out.println("=========================");
////	}
//
////	@Test
////	public void 특정_글의_댓글_검색() {
////		int targetPostId = 1;
////		List<TrendReVO> commentsByPost = trendReRepository.findAll(targetPostId);
////		assertThat(commentsByPost).isNotNull();
////
////		System.out.println("=========================");
////		System.out.println("commentsByPost.get(1).getTrNo() = " + commentsByPost.get(1).getTrNo());
////		for (TrendReVO commentResponse : commentsByPost) {
////			System.out.println("commentResponse.getTrReContent() = " + commentResponse.getTrReContent());
////		}
////		System.out.println("=========================");
////
////	}
//
//	@Test
//	@Transactional
//	public void 댓글_입력() {
//		//Given
//		Optional<UserVO> user = userRepository.findById("jun");
//
//		user.ifPresent(u -> {
//			TrendReVO reVO = TrendReVO.builder()
//					                 .trNo(1)
//					                 .userVO(u)
//					                 .trReRef(1)
//					                 .trReLev(1)
//					                 .trReDate(new Date())
//					                 .trReUpdate(new Date())
//					                 .trReDelYn('n')
//					                 .trReContent("Test content")
//					                 .build();
//
//			// When
//			TrendReVO save = trendReRepository.save(reVO);
//			List<TrendReVO> list = trendReRepository.findAll();
//
//			// Then
//			assertThat(list).isNotNull();
//			assertThat(list).contains(save);
//		});
//	}
//
//	@Test
//	@Transactional
//	public void 댓글_수정() {
//		// Given
//		int commentId = 1;
//		String updatedContent = "update-transactional";
//
//		// When
//		Optional<TrendReVO> commentOptional = trendReRepository.findById(commentId);
//		commentOptional.ifPresent(comment -> {
//			comment.setTrReContent(updatedContent);
//			comment.setTrReUpdate(new Date());
//			trendReRepository.save(comment);
//		});
//
//		// Then
//		Optional<TrendReVO> updatedCommentOptional = trendReRepository.findById(commentId);
//		assertThat(updatedCommentOptional).isPresent();
//		updatedCommentOptional.ifPresent(updatedComment -> {
//			assertThat(updatedComment.getTrReContent()).isEqualTo(updatedContent);
//		});
//	}
//
//
//	@Test
//	@Transactional
//	public void 댓글_삭제() {
//		// Given
//		int commentId = 1;
//
//		// When
//		trendReRepository.deleteById(commentId);
//
//		// Then
//		Optional<TrendReVO> deletedComment = trendReRepository.findById(commentId);
//		assertThat(deletedComment).isEmpty();
//	}
//
//	@Test
//	public void 댓글_객체_구하기() {
//
//		//Given
//		int trReNo = 1;
//
//		//when
//		Optional<TrendReVO> reVO = trendReRepository.findById(trReNo);
//
//		reVO.ifPresent(r -> {
//			assertThat(r.getTrReNo()).isEqualTo(trReNo);
//		});
//	}
//}
