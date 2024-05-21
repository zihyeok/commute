package hos.acorns.work.domain.commons.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReferenceEntity is a Querydsl query type for ReferenceEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReferenceEntity extends EntityPathBase<ReferenceEntity> {

    private static final long serialVersionUID = 905011768L;

    public static final QReferenceEntity referenceEntity = new QReferenceEntity("referenceEntity");

    public final StringPath allowIp = createString("allowIp");

    public final NumberPath<Integer> dayOffCount = createNumber("dayOffCount", Integer.class);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public QReferenceEntity(String variable) {
        super(ReferenceEntity.class, forVariable(variable));
    }

    public QReferenceEntity(Path<? extends ReferenceEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReferenceEntity(PathMetadata metadata) {
        super(ReferenceEntity.class, metadata);
    }

}

