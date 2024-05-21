package hos.acorns.work.domain.board.repository;

import hos.acorns.work.domain.board.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    /*@Modifying
    @Query("update BoardEntity a set a.view = a.view + 1  where a.idx = :id")
    BoardEntity updateConut(long idx);*/

    /* idx 까지 포함
    @Query(nativeQuery = true,
            value = "SELECT idx, board_pw, category, content, state, title, view," +
                    "DATE_FORMAT(reg_dt, '%Y-%m-%d') AS reg_dt ," +
                    "DATE_FORMAT(udt_dt, '%Y-%m-%d') AS udt_dt " +
                    "FROM board " +
                    "WHERE idx = :boardIdx " +
                    "ORDER BY idx DESC")
    Page<BoardEntity> findAllByList(@Param("boardIdx") Long boardIdx, Pageable pageable);*/

    /*@Query(nativeQuery = true,
            value = "SELECT idx, board_pw, category, content, state, title, view," +
                    "DATE_FORMAT(reg_dt, '%Y-%m-%d') AS reg_dt ," +
                    "DATE_FORMAT(udt_dt, '%Y-%m-%d') AS udt_dt " +
                    "FROM board " +
                    "ORDER BY idx DESC")
    Page<BoardEntity> findAllByList(Pageable pageable);*/

    //Page<BoardEntity> findAllByList(@Param("boardIdx") Long boardIdx, Pageable pageable);
    //Page<BoardEntity> findAllBy(Pageable pageable);
    //Optional<BoardEntity> updateConut(long inx);

    @Query(nativeQuery = true,
            value = "SELECT idx, board_pw, category, content, state, title, view," +
                    "DATE_FORMAT(reg_dt, '%Y-%m-%d') AS reg_dt ," +
                    "DATE_FORMAT(udt_dt, '%Y-%m-%d') AS udt_dt " +
                    "FROM board " +
                    "WHERE state = 0 " +
                    "ORDER BY FIELD(category, 1, 0), idx DESC")
    Page<BoardEntity> findAllByStateZero(Pageable pageable);

}
