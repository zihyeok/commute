package hos.acorns.work.domain.dayOff.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDayOffEntity is a Querydsl query type for DayOffEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDayOffEntity extends EntityPathBase<DayOffEntity> {

    private static final long serialVersionUID = -1761166827L;

    public static final QDayOffEntity dayOffEntity = new QDayOffEntity("dayOffEntity");

    public final NumberPath<Integer> active = createNumber("active", Integer.class);

    public final NumberPath<Integer> category = createNumber("category", Integer.class);

    public final StringPath content = createString("content");

    public final NumberPath<Float> dayoffCount = createNumber("dayoffCount", Float.class);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final NumberPath<Integer> offCheck = createNumber("offCheck", Integer.class);

    public final DatePath<java.time.LocalDate> regDt = createDate("regDt", java.time.LocalDate.class);

    public final StringPath startDt = createString("startDt");

    public final StringPath udtDt = createString("udtDt");

    public final NumberPath<Long> userIdx = createNumber("userIdx", Long.class);

    public final NumberPath<Integer> userOffCheck = createNumber("userOffCheck", Integer.class);

    public QDayOffEntity(String variable) {
        super(DayOffEntity.class, forVariable(variable));
    }

    public QDayOffEntity(Path<? extends DayOffEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDayOffEntity(PathMetadata metadata) {
        super(DayOffEntity.class, metadata);
    }

}

