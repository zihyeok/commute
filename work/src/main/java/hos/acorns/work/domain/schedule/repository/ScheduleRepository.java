package hos.acorns.work.domain.schedule.repository;

import hos.acorns.work.domain.schedule.dto.ScheduleRequestDTO;
import hos.acorns.work.domain.schedule.entity.ScheduleEntity;
import hos.acorns.work.domain.user.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    @Query(nativeQuery = true,
            value = "SELECT id, title, udt_active," +
                    "DATE_FORMAT(reg_dt, '%Y-%m-%d') AS reg_dt, " +
                    "DATE_FORMAT(end_dt, '%Y-%m-%d') AS end_dt, " +
                    "DATE_FORMAT(start_dt, '%Y-%m-%d') AS start_dt, " +
                    "DATE_FORMAT(udt_dt, '%Y-%m-%d') AS udt_dt " +
                    "FROM schedule " +
                    "WHERE udt_active IN (1, 2) " +
                    "ORDER BY id ")
    List<ScheduleEntity> findAlls();
}
