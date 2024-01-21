package handong.whynot.dto.blind_date;

import handong.whynot.dto.common.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BlindDateResponseCode implements ResponseCode {
  BLIND_DATE_CREATED_OK(20001, "소개팅 지원에 성공하였습니다."),
  BLIND_DATE_SUBMIT_APPLY_OK(20002, "소개팅 매칭에 대한 응답에 성공하였습니다."),
  MATCHING_CREATED_OK(20003, "새로운 매칭에 성공하였습니다."),
  MATCHING_DELETED_OK(20004, "매칭 삭제에 성공하였습니다."),
  MATCHING_NOTICE_OK(20005, "매칭 결과 발송에 성공하였습니다."),
  BLIND_DATE_FEE_CREATED_OK(20006, "보증금 납부 동의에 성공하였습니다."),
  BLIND_DATE_UPDATED_OK(20007, "소개팅 정보 수정에 성공하였습니다."),
  BLIND_DATE_APPLY_FINISH_OK(20008, "소개팅 최종 제출에 성공하였습니다."),
  BLIND_DATE_MATCHING_IMAGE_CREATED_OK(20009, "소개팅 만남 인증 사진 업로드에 성공하였습니다."),
  BLIND_DATE_RETRY_APPLY_OK(20010, "소개팅 재매칭 신청에 성공하였습니다."),
  BLIND_DATE_REPORT_MANNERS_OK(20011, "소개팅 비매너 신고에 성공하였습니다."),
  BLIND_DATE_DELETED_OK(20012, "소개팅 삭제에 성공하였습니다."),
  BLIND_DATE_FEE_RECALL_OK(20013, "참여비 즉시 환불 요청이 완료되었습니다."),
  BLIND_DATE_REMATCH_OK(20014, "재매칭 여부 응답이 완료되었습니다."),
  BLIND_DATE_EXTRA_INFO_OK(20015, "추가 수정이 완료되었습니다."),
  BLIND_DATE_CREATED_FAIL(40001, "소개팅 지원에 실패하였습니다."),
  BLIND_DATE_READ_FAIL(40002, "소개팅 [조회]에 실패하였습니다."),
  BLIND_DATE_NOT_AUTHENTICATED(40003, "학생증 인증이 되지 않은 사용자입니다."),
  BLIND_DATE_DUPLICATED(40004, "이번 시즌에 소개팅 지원한 이력이 있습니다."),
  BLIND_DATE_NOT_MATCHED(40005, "매칭된 대상이 없습니다."),
  MATCHING_INVALID(40006, "매칭 조건이 올바르지 않습니다. (남여 매칭이 아니거나, 이미 매칭된 인원이 있습니다.)"),
  MATCHING_READ_FAIL(40007, "매칭 내역 조회에 실패하였습니다."),
  REVEAL_FAIL(40008, "매칭 오픈 시간이 아닙니다."),
  BLIND_DATE_FEE_READ_FAIL(40009, "보증금 납부 내역 조회에 실패하였습니다."),
  BLIND_DATE_FEE_DUPLICATED(40010, "이미 보증금 납부 내역이 존재합니다.");

  private final Integer statusCode;
  private final String message;
}
