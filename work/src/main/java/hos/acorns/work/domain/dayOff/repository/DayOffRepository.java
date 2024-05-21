package hos.acorns.work.domain.dayOff.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hos.acorns.work.domain.dayOff.dto.DayOffAlarmListProjection;
import hos.acorns.work.domain.dayOff.dto.DayOffListDTO;
import hos.acorns.work.domain.dayOff.entity.DayOffEntity;

public interface DayOffRepository extends JpaRepository<DayOffEntity, Long> {

	// 관리자연차전체리스트
	@Query(nativeQuery = true,
			value = "SELECT dl.idx as idx, " +
							"u.idx as userIdx, " +
							"dl.active as active, " +
							"dl.category as category, " +
							"dl.start_dt as startDt, " +
							"u.lgn_name  as lgnName, " +
							"u.lgn_id as lgnId, " +
							"dl.idx as dayOffIdx, " +
							"dl.content as content, " +
							"dl.dayoff_count as dayoffCount, " +
							"dl.off_check as offCheck, " +
							"IFNULL(DATE_FORMAT(dl.udt_dt, '%Y-%m-%d %H:%i'), " +
									"DATE_FORMAT(dl.reg_dt, '%Y-%m-%d %H:%i')) AS udtDt " +
					"FROM dayoff_log dl " +
					"LEFT JOIN user u on (dl.user_idx = u.idx) " +
					"ORDER BY dl.idx desc")
	Page<DayOffAlarmListProjection> findAllById(Pageable pageable);

	// 관리자연차검색리스트
	@Query(nativeQuery = true,
			value = "SELECT dl.idx as idx, " +
							"u.idx as userIdx, " +
							"dl.active as active, " +
							"dl.category as category, " +
							"dl.start_dt as startDt, " +
							"u.lgn_name  as lgnName, " +
							"u.lgn_id as lgnId, " +
							"dl.idx as dayOffIdx, " +
							"dl.content as content, " +
							"dl.dayoff_count as dayoffCount, " +
							"dl.off_check as offCheck, " +
							"IFNULL(DATE_FORMAT(dl.udt_dt, '%Y-%m-%d %H:%i'), " +
									"DATE_FORMAT(dl.reg_dt, '%Y-%m-%d %H:%i')) AS udtDt " +
					"FROM dayoff_log dl " +
					"LEFT JOIN user u on (dl.user_idx = u.idx) " +
					"WHERE dl.active = :active " +
					"ORDER BY dl.idx desc")
	Page<DayOffAlarmListProjection> findAllByIdAndActive(@Param("active") int active, Pageable pageable);

	// 직원연차전체리스트
	@Query(nativeQuery = true,
			value = "SELECT dl.idx as idx, " +
							"u.idx as userIdx, " +
							"dl.active as active, " +
							"dl.category as category, " +
							"dl.start_dt as startDt, " +
							"u.lgn_name  as lgnName, " +
							"u.lgn_id as lgnId, " +
							"dl.idx as dayOffIdx, " +
							"dl.content as content, " +
							"dl.dayoff_count as dayoffCount, " +
							"dl.off_check as offCheck, " +
							"IFNULL(DATE_FORMAT(dl.udt_dt, '%Y-%m-%d %H:%i'), " +
									"DATE_FORMAT(dl.reg_dt, '%Y-%m-%d %H:%i')) AS udtDt " +
					"FROM dayoff_log dl " +
					"LEFT JOIN user u on (dl.user_idx = u.idx) " +
					"WHERE dl.user_idx = :idx " +
					"ORDER BY dl.idx desc")
	Page<DayOffAlarmListProjection> findAllBySearchUserIdx(@Param("idx") Long userIdx, Pageable pageable);


	// 직원연차검색리스트
	@Query(nativeQuery = true,
			value = "SELECT dl.idx as idx, " +
							"u.idx as userIdx, " +
							"dl.active as active, " +
							"dl.category as category, " +
							"dl.start_dt as startDt, " +
							"u.lgn_name  as lgnName, " +
							"u.lgn_id as lgnId, " +
							"dl.idx as dayOffIdx, " +
							"dl.content as content, " +
							"dl.dayoff_count as dayoffCount, " +
							"dl.off_check as offCheck, " +
							"IFNULL(DATE_FORMAT(dl.udt_dt, '%Y-%m-%d %H:%i'), " +
									"DATE_FORMAT(dl.reg_dt, '%Y-%m-%d %H:%i')) AS udtDt " +
					"FROM dayoff_log dl " +
					"LEFT JOIN user u on (dl.user_idx = u.idx) " +
					"WHERE dl.user_idx = :idx " +
					"AND dl.active = :active " +
					"ORDER BY dl.idx desc")
	Page<DayOffAlarmListProjection> findAllBySearchUserIdxAndActive(@Param("idx") Long userIdx,@Param("active") int active, Pageable pageable);

	// 관리자연차알람리스트
	@Query(nativeQuery = true,
	        value = "SELECT dl.idx as idx, " +
							"u.idx as userIdx, " +
							"dl.active as active, " +
							"dl.category as category, " +
							"dl.start_dt as startDt, " +
							"u.lgn_name  as lgnName, " +
							"u.lgn_id as lgnId, " +
							"dl.dayoff_count as dayoffCount, " +
							"dl.off_check as offCheck, " +
							"dl.udt_dt as udtDt, " +
							"dl.content as content " +
					"FROM dayoff_log dl " +
					"LEFT JOIN user u on (dl.user_idx = u.idx) " +
					"WHERE dl.active = :active " +
					"AND off_check = :offCheck " +
					"ORDER BY dl.idx desc")
	List<DayOffAlarmListProjection> findByActiveAndOffCheck(@Param("active") int active, @Param("offCheck") int offCheck);

	// 직원연차알람리스트
	@Query(nativeQuery = true,
			value = "SELECT dl.idx AS idx, " +
							"u.idx AS userIdx, " +
							"dl.active AS active, " +
							"dl.category AS category, " +
							"dl.start_dt AS startDt, " +
							"u.lgn_name  AS lgnName, " +
							"u.lgn_id AS lgnId, " +
							"dl.dayoff_count AS dayoffCount, " +
							"dl.off_check AS offCheck, " +
							"dl.udt_dt AS udtDt, " +
							"dl.content AS content " +
					"FROM dayoff_log dl " +
					"LEFT JOIN user u ON (dl.user_idx = u.idx) " +
					"WHERE dl.user_idx = :idx " +
					"AND dl.active IN ( :active1 , :active2 ) " +
					"AND dl.user_off_check = :userOffCheck " +
					"ORDER BY dl.idx DESC")
	List<DayOffAlarmListProjection> findByActiveAndUserOffCheck(@Param("idx") Long idx, @Param("active1") int active1, @Param("active2") int active2, @Param("userOffCheck") int userOffCheck);

	// 관리자연차알람확인
	List<DayOffEntity> findByOffCheck(int offCheck);

	// 연차승인거절리스트
	@Query(nativeQuery = true,
			value = "SELECT dl.idx AS idx, " +
							"u.idx AS userIdx, " +
							"dl.active AS active, " +
							"dl.category AS category, " +
							"dl.start_dt AS startDt, " +
							"u.lgn_name  AS lgnName, " +
							"u.lgn_id AS lgnId, " +
							"dl.idx AS dayOffIdx, " +
							"dl.content AS content, " +
							"dl.dayoff_count AS dayoffCount, " +
							"dl.off_check AS offCheck, " +
							"IFNULL(DATE_FORMAT(dl.udt_dt, '%Y-%m-%d %H:%i'), " +
									"DATE_FORMAT(dl.reg_dt, '%Y-%m-%d %%H:%i')) AS udtDt " +
					"FROM dayoff_log dl " +
					"LEFT JOIN user u ON (dl.user_idx = u.idx) " +
					"WHERE dl.active = :active " +
					"ORDER BY dl.idx DESC")
	Page<DayOffAlarmListProjection> findAllByActiveOrderByIdxDesc(@Param("active") int active, Pageable customPageable);

	// 해당직원연차찾기
	@Query(nativeQuery = true,
			value = "SELECT * " +
					"FROM dayoff_log " +
					"WHERE user_idx = :idx " +
					"AND active IN ( :active1 , :active2 ) " +
					"AND user_off_check = :userOffCheck")
	List<DayOffEntity> findByUserIdxActiveAndUserOffCheck(@Param("idx") Long idx, @Param("active1") int active1, @Param("active2") int active2, @Param("userOffCheck") int userOffCheck);

}
