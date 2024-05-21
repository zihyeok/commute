package hos.acorns.work.domain.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardEntity is a Querydsl query type for BoardEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardEntity extends EntityPathBase<BoardEntity> {

    private static final long serialVersionUID = 1340384917L;

    public static final QBoardEntity boardEntity = new QBoardEntity("boardEntity");

    public final StringPath boardPw = createString("boardPw");

    public final NumberPath<Integer> category = createNumber("category", Integer.class);

    public final StringPath content = createString("content");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final DatePath<java.time.LocalDate> regDt = createDate("regDt", java.time.LocalDate.class);

    public final NumberPath<Integer> state = createNumber("state", Integer.class);

    public final StringPath title = createString("title");

    public final DatePath<java.time.LocalDate> udtDt = createDate("udtDt", java.time.LocalDate.class);

    public final NumberPath<Integer> view = createNumber("view", Integer.class);

    public QBoardEntity(String variable) {
        super(BoardEntity.class, forVariable(variable));
    }

    public QBoardEntity(Path<? extends BoardEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardEntity(PathMetadata metadata) {
        super(BoardEntity.class, metadata);
    }

}

