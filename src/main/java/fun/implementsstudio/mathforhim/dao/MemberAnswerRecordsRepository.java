package fun.implementsstudio.mathforhim.dao;

import fun.implementsstudio.mathforhim.entity.MemberAnswerRecords;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface MemberAnswerRecordsRepository extends BaseRepository<MemberAnswerRecords,Long> {

    @Nullable
    MemberAnswerRecords findByQuestionIdAndMemberId(String questionId,String memberId);

    List<MemberAnswerRecords> findByMemberId(String memberId, Sort sort);
}