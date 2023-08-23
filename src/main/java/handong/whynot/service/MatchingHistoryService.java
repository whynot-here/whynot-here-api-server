package handong.whynot.service;

import handong.whynot.dto.admin.AdminBlindMatchingResponseDTO;
import handong.whynot.repository.MatchingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingHistoryService {
  private final MatchingHistoryRepository matchingHistoryRepository;

  public List<AdminBlindMatchingResponseDTO> getBlindMatchingList(Integer season) {

    return matchingHistoryRepository.findAllBySeason(season).stream()
      .map(AdminBlindMatchingResponseDTO::of)
      .collect(Collectors.toList());
  }
}
