package fun.implementsstudio.mathforhim.dao;

import fun.implementsstudio.mathforhim.entity.MemberAnswerRecords;
import io.lettuce.core.dynamic.annotation.Param;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberAnswerRecordsRepository extends BaseRepository<MemberAnswerRecords, Long> {

    @Nullable
    MemberAnswerRecords findByQuestionIdAndMemberId(String questionId, String memberId);

    List<MemberAnswerRecords> findByMemberId(String memberId, Sort sort);

    @Query(value = "select id,question_id,wrong_count,right_count,type,member_id," +
            "create_time,update_time from " +
            "member_answer_records where " +
            "if(IFNULL(:questionId,'') !='',question_id=:questionId,1=1) " +
            "and if(IFNULL(:type,'') !='',type=:type,1=1) " +
            "and if(IFNULL(:memberId,'')!='',member_id=:memberId,1=1) order by create_time desc",
            nativeQuery = true)
    List<MemberAnswerRecords> findByQIdTypeAndMemberIdNative(
            @Param("questionId")String questionId,
            @Param("type") String type,
            @Param("memberId") String memberId);
}