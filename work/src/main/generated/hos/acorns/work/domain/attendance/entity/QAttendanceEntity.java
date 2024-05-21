package hos.acorns.work.domain.attendance.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAttendanceEntity is a Querydsl query type for AttendanceEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttendanceEntity extends EntityPathBase<AttendanceEntity> {

    private static final long serialVersionUID = -1661444607L;

    public static final QAttendanceEntity attendanceEntity = new QAttendanceEntity("attendanceEntity");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final StringPath regDt = createString("regDt");

    public final NumberPath<Long> userIdx = createNumber("userIdx", Long.class);

    public final StringPath workIn = createString("workIn");

    public final StringPath workOut = createString("workOut");

    public QAttendanceEntity(String variable) {
        super(AttendanceEntity.class, forVariable(variable));
    }

    public QAttendanceEntity(Path<? extends AttendanceEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAttendanceEntity(PathMetadata metadata) {
        super(AttendanceEntity.class, metadata);
    }

}

