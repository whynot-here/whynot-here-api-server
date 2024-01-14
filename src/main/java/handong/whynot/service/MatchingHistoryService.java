package handong.whynot.service;

import handong.whynot.domain.BlindDate;
import handong.whynot.domain.MatchingHistory;
import handong.whynot.dto.admin.AdminBlindMatchingResponseDTO;
import handong.whynot.dto.blind_date.BlindDateResponseCode;
import handong.whynot.exception.blind_date.BlindDateNotFoundException;
import handong.whynot.exception.blind_date.MatchingNotFoundException;
import handong.whynot.repository.BlindDateRepository;
import handong.whynot.repository.MatchingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingHistoryService {
  private final MatchingHistoryRepository matchingHistoryRepository;
  private final BlindDateRepository blindDateRepository;

  public List<AdminBlindMatchingResponseDTO> getBlindMatchingListBySeason(Integer season) {

    return matchingHistoryRepository.findAllBySeason(season).stream()
      .map(AdminBlindMatchingResponseDTO::of)
      .collect(Collectors.toList());
  }

  @Transactional
  public void deleteBlindMatching(Long matchingId) {
    MatchingHistory matchingHistory = matchingHistoryRepository.findById(matchingId)
      .orElseThrow(() -> new MatchingNotFoundException(BlindDateResponseCode.MATCHING_READ_FAIL));

    // 매칭 업데이트
    BlindDate male = blindDateRepository.findById(matchingHistory.getMaleId())
      .orElseThrow(() -> new BlindDateNotFoundException(BlindDateResponseCode.BLIND_DATE_READ_FAIL));
    BlindDate female = blindDateRepository.findById(matchingHistory.getFemaleId())
      .orElseThrow(() -> new BlindDateNotFoundException(BlindDateResponseCode.BLIND_DATE_READ_FAIL));

    male.updateMatchingBlindDate(null);
    female.updateMatchingBlindDate(null);

    matchingHistoryRepository.deleteById(matchingId);
  }

  @Transactional
  public void updateImageLink(BlindDate blindDate, String link) {
    if (StringUtils.equals(blindDate.getGender(), "M")) {
      MatchingHistory maleHistory = matchingHistoryRepository.findByMaleId(blindDate.getId())
        .orElseThrow(() -> new MatchingNotFoundException(BlindDateResponseCode.MATCHING_READ_FAIL));
      maleHistory.setMaleImageLink(link);
    }
    else {
      MatchingHistory femaleHistory = matchingHistoryRepository.findByFemaleId(blindDate.getId())
        .orElseThrow(() -> new MatchingNotFoundException(BlindDateResponseCode.MATCHING_READ_FAIL));
      femaleHistory.setFemaleImageLink(link);
    }
  }

  public String getMatchingImageByBlindDate(BlindDate blindDate) {
    if (StringUtils.equals(blindDate.getGender(), "M")) {
      MatchingHistory maleHistory = matchingHistoryRepository.findByMaleId(blindDate.getId())
        .orElseThrow(() -> new MatchingNotFoundException(BlindDateResponseCode.MATCHING_READ_FAIL));
      return maleHistory.getMaleImageLink();
    }
    else {
      MatchingHistory femaleHistory = matchingHistoryRepository.findByFemaleId(blindDate.getId())
        .orElseThrow(() -> new MatchingNotFoundException(BlindDateResponseCode.MATCHING_READ_FAIL));
      return femaleHistory.getFemaleImageLink();
    }
  }

  public MatchingHistory getMatchingHistoryByBlindDate(BlindDate blindDate) {
    MatchingHistory matchingHistory;
    
    if (StringUtils.equals(blindDate.getGender(), "M")) {
      matchingHistory = matchingHistoryRepository.findByMaleId(blindDate.getId())
        .orElseThrow(() -> new MatchingNotFoundException(BlindDateResponseCode.MATCHING_READ_FAIL));
    }
    else {
      matchingHistory = matchingHistoryRepository.findByFemaleId(blindDate.getId())
        .orElseThrow(() -> new MatchingNotFoundException(BlindDateResponseCode.MATCHING_READ_FAIL));
    }

    return matchingHistory;
  }
}
