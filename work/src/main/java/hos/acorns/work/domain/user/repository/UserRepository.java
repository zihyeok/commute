package hos.acorns.work.domain.user.repository;

import java.util.Optional;

import hos.acorns.work.domain.board.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import hos.acorns.work.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	// 아이디 조회 (SELECT * FROM `user` WHERE lgn_id = ?)
	Optional<UserEntity> findByLgnId(String lgnId);

	// 로그인 (SELECT * FROM `user` WHERE lgn_id = ? AND active = ?)
//	Optional<UserEntity> findByLgnIdAndActive(String lgnId, int active);

	/*@Query(nativeQuery = true,
			value = "SELECT idx, active, email, gender, lgn_id, lgn_pw, lgn_name, phone, position, " +
					"DATE_FORMAT(reg_dt, '%Y-%m-%d') AS reg_dt, " +
					"DATE_FORMAT(udt_dt, '%Y-%m-%d') AS udt_dt, " +
					"DATE_FORMAT(workIn_dt, '%Y-%m-%d') AS workIn_dt " +
					"FROM user " +
					"WHERE active IN (1, 2) " +
					"ORDER BY FIELD(active, 2, 1), idx DESC")
	Page<UserEntity> findAllByUserList(Pageable pageable);*/

	@Query(nativeQuery = true,
			value = "SELECT idx, active, email, lgn_id, lgn_pw, lgn_name, phone, position, " +
					"DATE_FORMAT(reg_dt, '%Y-%m-%d') AS reg_dt, " +
					"DATE_FORMAT(udt_dt, '%Y-%m-%d') AS udt_dt, " +
					"DATE_FORMAT(workIn_dt, '%Y-%m-%d') AS workIn_dt " +
					"FROM user " +
					"WHERE active = 1 " +
					"ORDER BY idx DESC")
	Page<UserEntity> findAllByUserList(Pageable pageable);
	@Query(nativeQuery = true,
			value = "SELECT idx, active, email, lgn_id, lgn_pw, lgn_name, phone, position, " +
					"DATE_FORMAT(reg_dt, '%Y-%m-%d') AS reg_dt, " +
					"DATE_FORMAT(udt_dt, '%Y-%m-%d') AS udt_dt, " +
					"DATE_FORMAT(workIn_dt, '%Y-%m-%d') AS workIn_dt " +
					"FROM user " +
					"WHERE active = 2 " +
					"ORDER BY idx DESC")
	Page<UserEntity> findByActiveList(Pageable pageable);

	// 쿼리 수정 and 절 추가
}
