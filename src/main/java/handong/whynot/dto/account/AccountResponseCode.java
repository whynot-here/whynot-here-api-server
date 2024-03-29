package handong.whynot.dto.account;

import handong.whynot.dto.common.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountResponseCode implements ResponseCode {
    ACCOUNT_CREATE_OK(20001, "사용자 [생성]에 성공하였습니다."),
    ACCOUNT_READ_OK(20002, "사용자 [조회]에 성공하였습니다."),
    ACCOUNT_UPDATE_OK(20003, "사용자 [수정]에 성공하였습니다."),
    ACCOUNT_DELETE_OK(20004, "사용자 [삭제]에 성공하였습니다."),
    ACCOUNT_CREATE_TOKEN_OK(20005, "사용자 [토큰생성]에 성공하였습니다."),
    ACCOUNT_VERIFY_OK(20006, "사용자 [이메일 토큰검증]에 성공하였습니다."),
    ACCOUNT_VALID_DUPLICATE(20007, "사용 가능한 인자입니다."),
    ACCOUNT_LOGOUT_SUCCESS(20008, "로그아웃 성공하였습니다."),
    ACCOUNT_CHANGE_PASSWORD_OK(20009, "비밀번호 변경에 성공하였습니다."),
    ACCOUNT_UPDATE_DEVICE_TOKEN_OK(20010, "Device 토큰 업데이트에 성공하였습니다."),
    BLOCK_ACCOUNT_CREATED_OK(20011, "사용자 차단에 성공하였습니다."),
    BLOCK_ACCOUNT_DELETED_OK(20012, "사용자 차단 해제에 성공하였습니다."),
    BLIND_DATE_PUSH_ON_OFF_OK(20013, "한대소 알림 받기 상태 변경에 성공하였습니다."),

    ACCOUNT_CREATE_FAIL_ALREADY_EXIST(40001, "사용자 [생성]에 실패하였습니다. - 이미 존재함"),
    ACCOUNT_READ_FAIL(40002, "사용자 [조회]에 실패하였습니다."),
    ACCOUNT_UPDATE_FAIL_NOT_FOUND(40003, "사용자 [수정]에 실패하였습니다. - 해당 id 찾을 수 없음"),
    ACCOUNT_DELETE_FAIL_NOT_FOUND(40004, "사용자 [삭제]에 실패하였습니다. - 해당 id 찾을 수 없음"),
    ACCOUNT_NOT_VALID_FROM(40005, "사용자 [입력검증]에 실패하였습니다."),
    ACCOUNT_ALREADY_EXIST_EMAIL(40006, "사용자 [중복검증]에 실패하였습니다. - 이미 사용중인 이메일입니다."),
    ACCOUNT_ALREADY_EXIST_NICKNAME(40007, "사용자 [중복검증]에 실패하였습니다. - 이미 사용중인 닉네임입니다."),
    ACCOUNT_NOT_VALID_TOKEN(40008, "사용자 [이메일 토큰검증]에 실패하였습니다."),
    ACCOUNT_FORBIDDEN(40009, "사용자 인증이 필요합니다."),
    ACCOUNT_NOT_VALID(40010, "사용자 아이디 혹은 비밀번호가 일치하지 않습니다."),
    ACCOUNT_TOKEN_EXPIRED(40011, "만료된 토큰입니다."),
    ACCOUNT_NOT_VALID_TOKEN_SECRET(40012, "올바르지 않은 jwt 토큰 시크릿입니다."),
    ACCOUNT_OAUTH2_PROCESS_FAILED(40013, "OAuth2 처리 실패하였습니다."),
    ACCOUNT_OAUTH2_INVALID_REGISTRATION(40014, "지원하지 않는 OAuth2 서버입니다."),
    ACCOUNT_OAUTH2_NOT_PROVIDED_VALUE(40015, "필수 사용자 정보가 제공되지 않았습니다."),
    ACCOUNT_OAUTH2_EXIST_SAME_EMAIL(40016, "동일한 이메일이 사용되고 있습니다."),
    ALREADY_EXIST_ROLE(40017, "이미 가지고 있는 Role 입니다."),
    ROLE_READ_FAIL(40018, "존재하지 않는 Role 입니다."),
    ACCOUNT_INVALID_NICKNAME(40019, "변경할 수 없는 단어가 포함된 닉네임입니다."),
    ACCOUNT_PASSWORD_NOT_MATCHED(40020, "비밀번호가 일치하지 않습니다."),
    ACCOUNT_PASSWORD_NOT_VALID(40021, "비밀번호 변경 대상자가 아닙니다."),
    ALREADY_EXIST_BLOCK_ACCOUNT(40022, "이미 차단된 사용자입니다."),
    BLOCK_ACCOUNT_READ_FAIL(40023, "차단 사용자 [조회]에 실패하였습니다."),;


    private final Integer statusCode;
    private final String message;
}
