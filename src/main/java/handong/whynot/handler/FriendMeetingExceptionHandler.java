package handong.whynot.handler;

import handong.whynot.dto.common.ErrorResponseDTO;
import handong.whynot.dto.friend_meeting.FriendMeetingResponseCode;
import handong.whynot.exception.blind_date.*;
import handong.whynot.exception.friend_meeting.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class FriendMeetingExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(FriendMeetingNotFoundException.class)
    public ErrorResponseDTO friendMeetingNotFoundExceptionHandle() {
        return ErrorResponseDTO.of(FriendMeetingResponseCode.FRIEND_MEETING_READ_FAIL, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(FriendMeetingNotAuthenticatedException.class)
    public ErrorResponseDTO friendMeetingNotAuthenticatedExceptionHandle() {
        return ErrorResponseDTO.of(FriendMeetingResponseCode.FRIEND_MEETING_NOT_AUTHENTICATED, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(FriendMeetingDuplicatedException.class)
    public ErrorResponseDTO friendMeetingDuplicatedExceptionHandle() {
        return ErrorResponseDTO.of(FriendMeetingResponseCode.FRIEND_MEETING_DUPLICATED, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(FriendMeetingNotMatchedException.class)
    public ErrorResponseDTO friendMeetingNotMatchedExceptionHandle() {
        return ErrorResponseDTO.of(FriendMeetingResponseCode.FRIEND_MEETING_NOT_MATCHED, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(InvalidMatchingException.class)
    ErrorResponseDTO invalidMatchingExceptionHandle() {
        return ErrorResponseDTO.of(FriendMeetingResponseCode.FRIEND_MEETING_NOT_MATCHED, null);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(MatchingNotFoundException.class)
    public ErrorResponseDTO matchingNotFoundExceptionHandle() {
        return ErrorResponseDTO.of(FriendMeetingResponseCode.MATCHING_READ_FAIL, null);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(FriendMeetingNotOpenedException.class)
    public ErrorResponseDTO friendMeetingNotOpenedExceptionHandle() {
        return ErrorResponseDTO.of(FriendMeetingResponseCode.REVEAL_FAIL, null);
    }
}
