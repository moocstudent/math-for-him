package fun.implementsstudio.mathforhim.dao;

import fun.implementsstudio.mathforhim.entity.MemberAnswerRecords;

public interface MemberAnswerRecordsRepository extends BaseRepository<MemberAnswerRecords,Long> {

    MemberAnswerRecords findByQuestionIdAndMemberId(String questionId,String memberId);
}