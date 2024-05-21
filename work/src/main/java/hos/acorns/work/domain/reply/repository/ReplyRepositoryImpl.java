package hos.acorns.work.domain.reply.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hos.acorns.work.domain.board.entity.QBoardEntity;
import hos.acorns.work.domain.reply.entity.QReplyEntity;
import hos.acorns.work.domain.reply.entity.ReplyEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
@Slf4j
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepositoryEx{

    private final JPAQueryFactory jpaQueryFactory;
    private final QReplyEntity reply = QReplyEntity.replyEntity;
    private final QBoardEntity board = QBoardEntity.boardEntity;

    @Override
    public Page<ReplyEntity> findByReplyAndBoard(Pageable pageable, Long boardIdx) {
        JPAQuery<ReplyEntity> query = jpaQueryFactory
                .selectFrom(reply)
                .join(board)
                .on(reply.refIdx.eq(board.idx))
                .orderBy(reply.idx.desc());

        List<ReplyEntity> resultList = query
                .offset(pageable.getOffset()) // 페이지 시작 지점 설정
                .limit(pageable.getPageSize()) // 페이지 크기 설정
                .fetch(); // 데이터 fetch

        // 페이징된 결과를 PageImpl을 사용하여 Page 객체로 반환
        return new PageImpl<>(resultList, pageable, query.fetchCount());
    }

    /*
    @Override
    public Page<ReplyEntity> findByReplyAndBoard(Pageable pageable, Long boardIdx, ReplyRequestDTO dto) {
        JPAQuery<ReplyEntity> query = jpaQueryFactory
                .selectFrom(reply)
                .join(board)
                .where(reply.refIdx.eq(dto.getRefIdx()), board.idx.eq(boardIdx))
                .orderBy(reply.idx.desc());

        List<ReplyEntity> resultList = query
                .offset(pageable.getOffset()) // 페이지 시작 지점 설정
                .limit(pageable.getPageSize()) // 페이지 크기 설정
                .fetch(); // 데이터 fetch

        // 페이징된 결과를 PageImpl을 사용하여 Page 객체로 반환
        return new PageImpl<>(resultList, pageable, query.fetchCount());

    }
    */

}
