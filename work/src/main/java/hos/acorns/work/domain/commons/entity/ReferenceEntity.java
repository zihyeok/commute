package hos.acorns.work.domain.commons.entity;

import java.time.LocalDate;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import hos.acorns.work.domain.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@DynamicInsert
@Entity
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED) 
@AllArgsConstructor
@ToString
@Builder
@Table(name = "ref_table")
public class ReferenceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("인덱스")
	private Long idx;

	@ColumnDefault("15")
	@Column(name="dayOff_count")
	@Comment("기본 연차수")
	private int dayOffCount;

	@Column(name="allow_ip", length=30)
	@Comment("허용ip")
	private String allowIp;
}
