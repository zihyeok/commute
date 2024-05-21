package hos.acorns.work.domain.commons.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hos.acorns.work.domain.commons.entity.ReferenceEntity;

public interface ReferenceRepository extends JpaRepository<ReferenceEntity, Long>{

	// ip조회
	Optional<ReferenceEntity> findByAllowIp(String ip);

}
