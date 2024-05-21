package hos.acorns.work.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = -61672251L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final NumberPath<Integer> active = createNumber("active", Integer.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final StringPath lgnId = createString("lgnId");

    public final StringPath lgnPw = createString("lgnPw");

    public final StringPath name = createString("name");

    public final StringPath phone = createString("phone");

    public final NumberPath<Integer> position = createNumber("position", Integer.class);

    public final StringPath regDt = createString("regDt");

    public final DatePath<java.time.LocalDate> udtDt = createDate("udtDt", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> workInDt = createDate("workInDt", java.time.LocalDate.class);

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

