package hos.acorns.work.domain.attendance.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hos.acorns.work.domain.attendance.dto.AttendanceInCheckProjection;
import hos.acorns.work.domain.attendance.dto.AttendanceListProjection;
import hos.acorns.work.domain.attendance.entity.AttendanceEntity;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long>{

	// 출근확인
	@Query(nativeQuery = true,
			value = "SELECT COUNT(*) AS count " +
					"FROM work_log " +
					"WHERE user_idx = :userIdx " +
					"AND DATE(reg_dt) = CURDATE()")
	AttendanceInCheckProjection findByUserIdx(@Param("userIdx") Long userIdx);

	// 직원출퇴근 리스트
	@Query(nativeQuery = true,
			value = "SELECT wl.idx AS idx, " +
							"wl.user_idx AS userIdx, " +
							"u.lgn_id AS lgnId, " +
							"u.lgn_name AS lgnName, " +
							"date(wl.reg_dt) AS regDt, " +
							"date_format(wl.workIn, '%Y-%m-%d %H:%i') AS workIn, " +
							"date_format(wl.workOut, '%Y-%m-%d %H:%i') AS workOut " +
					"FROM work_log wl " +
					"LEFT JOIN user u ON (wl.user_idx = u.idx) " +
					"WHERE wl.user_idx = :userIdx " +
					"ORDER BY wl.idx DESC")
	Page<AttendanceListProjection> findByUserIdx(@Param("userIdx") Long userIdx, Pageable pageable);

	// 관리자 출퇴근 리스트
	@Query(nativeQuery = true,
			value = "SELECT wl.idx AS idx, " +
							"wl.user_idx AS userIdx, " +
							"u.lgn_id AS lgnId, " +
							"u.lgn_name AS lgnName, " +
							"date(wl.reg_dt) AS regDt, " +
							"date_format(wl.workIn, '%Y-%m-%d %T') AS workIn, " +
							"date_format(wl.workOut, '%Y-%m-%d %T') AS workOut " +
					"FROM work_log wl " +
					"LEFT JOIN user u ON (wl.user_idx = u.idx) " +
					"ORDER BY wl.idx DESC")
	Page<AttendanceListProjection> findAllList(Pageable pageable);
	
	// 관리자 출퇴근 리스트 - 엑셀 출력용
	@Query(nativeQuery = true,
			value = "SELECT wl.idx AS idx, " +
							"wl.user_idx AS userIdx, " +
							"u.lgn_id AS lgnId, " +
							"u.lgn_name AS lgnName, " +
							"date(wl.reg_dt) AS regDt, " +
							"date_format(wl.workIn, '%Y-%m-%d %r') AS workIn, " +
							"date_format(wl.workOut, '%Y-%m-%d %r') AS workOut " +
					"FROM work_log wl " +
					"LEFT JOIN user u ON (wl.user_idx = u.idx) " +
					"ORDER BY wl.idx DESC")
	List<AttendanceListProjection> findAllList();
	
	@Query(nativeQuery = true,
			value = "select * " +
					"from work_log wl " +
					"left join `user` u on(wl.user_idx = u.idx)")
	Page<AttendanceEntity> findAllByUserIdx(Long userIdx, Pageable pageable);


}
