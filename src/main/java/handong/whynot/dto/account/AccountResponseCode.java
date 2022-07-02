package handong.whynot.dto.account;

import handong.whynot.dto.common.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountResponseCode implements ResponseCode {
    ACCOUNT_CREATE_OK(20001, "사용자 [생성]에 성공하였습니다."),
    ACCOUNT_CREATE_FAIL_ALREADY_EXIST(40001, "사용자 [생성]에 실패하였습니다. - 이미 존재함"),

    ACCOUNT_READ_OK(20002, "사용자 [조회]에 성공하였습니다."),
    ACCOUNT_READ_FAIL(40002, "사용자 [조회]에 실패하였습니다."),

    ACCOUNT_UPDATE_OK(20003, "사용자 [수정]에 성공하였습니다."),
    ACCOUNT_UPDATE_FAIL_NOT_FOUND(40003, "사용자 [수정]에 실패하였습니다. - 해당 id 찾을 수 없음"),

    ACCOUNT_DELETE_OK(20004, "사용자 [삭제]에 성공하였습니다."),
    ACCOUNT_DELETE_FAIL_NOT_FOUND(40004, "사용자 [삭제]에 실패하였습니다. - 해당 id 찾을 수 없음"),

    ACCOUNT_NOT_VALID_FROM(40005, "사용자 [입력검증]에 실패하였습니다."),
    ACCOUNT_ALREADY_EXIST_EMAIL(40006, "사용자 [중복검증]에 실패하였습니다. - 이미 사용중인 이메일입니다."),
    ACCOUNT_ALREADY_EXIST_NICKNAME(40007, "사용자 [중복검증]에 실패하였습니다. - 이미 사용중인 닉네임입니다."),
    ACCOUNT_NOT_VALID_TOKEN(40008, "사용자 [이메일 토큰검증]에 실패하였습니다."),
    ACCOUNT_CREATE_TOKEN_OK(20005, "사용자 [토큰생성]에 성공하였습니다."),
    ACCOUNT_VERIFY_OK(20006, "사용자 [이메일 토큰검증]에 성공하였습니다."),
    ACCOUNT_FORBIDDEN(40009, "사용자 인증이 필요합니다."),
    ACCOUNT_NOT_VALID(40010, "사용자 아이디 혹은 비밀번호가 일치하지 않습니다."),

    ACCOUNT_VALID_DUPLICATE(20007, "사용 가능한 인자입니다."),
    ACCOUNT_LOGOUT_SUCCESS(20008, "로그아웃 성공하였습니다."),
    ACCOUNT_TOKEN_EXPIRED(40011, "만료된 토큰입니다.");

    private final Integer statusCode;
    private final String message;
}
