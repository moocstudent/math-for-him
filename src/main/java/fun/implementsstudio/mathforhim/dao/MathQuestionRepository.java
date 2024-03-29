package fun.implementsstudio.mathforhim.dao;

import fun.implementsstudio.mathforhim.entity.MathQuestion;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * junyang,前方的路还有很远，不要太在意之前的得失，要一鼓作气勇攀高峰。
 */
public interface MathQuestionRepository extends BaseRepository<MathQuestion,Long> {
    /**
     * 又见炊烟升起 暮色照大地
     * @param type
     * @param maxLimit
     * @param size
     * @return random list
     */
    @Query(value = "select id,question,answer,max_limit,type,create_time,update_time from " +
            "math_question_bank where " +
            "if(IFNULL(:type,'') !='',type=:type,1=1) and max_limit <=:maxLimit order by rand() limit :size"
    ,nativeQuery = true)
    List<MathQuestion> findByConditions(@Param("type") String type,
                                        @Param("maxLimit") long maxLimit,
                                        @Param("size") long size);





}
