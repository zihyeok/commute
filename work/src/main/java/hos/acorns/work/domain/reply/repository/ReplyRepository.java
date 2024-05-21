package hos.acorns.work.domain.reply.repository;

import hos.acorns.work.domain.reply.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Long>, ReplyRepositoryEx{


}
