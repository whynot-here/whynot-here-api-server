package handong.whynot.service;

import handong.whynot.dto.admin.AdminFriendMatchingResponseDTO;
import handong.whynot.repository.FriendMatchingHistoryRepository;
import handong.whynot.repository.FriendMeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendMatchingHistoryService {
  private final FriendMatchingHistoryRepository friendMatchingHistoryRepository;
  private final FriendMeetingRepository friendMeetingRepository;

  public List<AdminFriendMatchingResponseDTO> getFriendMatchingListBySeason(Integer season) {

    return friendMatchingHistoryRepository.findAllBySeason(season).stream()
      .map(AdminFriendMatchingResponseDTO::of)
      .collect(Collectors.toList());
  }
}
