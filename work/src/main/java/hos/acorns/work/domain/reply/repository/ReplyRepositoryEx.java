package hos.acorns.work.domain.reply.repository;

import hos.acorns.work.domain.reply.dto.ReplyRequestDTO;
import hos.acorns.work.domain.reply.entity.ReplyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReplyRepositoryEx{
// extends QuerydslPredicateExecutor<ReplyEntity>
//익스텐션 = 확장

    //Page<ReplyEntity> findByReplyAndBoard(Pageable pageable, Long boardIdx, ReplyRequestDTO dto);
    Page<ReplyEntity> findByReplyAndBoard(Pageable pageable, Long boardIdx);
}
