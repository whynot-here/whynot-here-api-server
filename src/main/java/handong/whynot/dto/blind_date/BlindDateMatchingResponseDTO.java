package handong.whynot.dto.blind_date;

import handong.whynot.domain.BlindDate;
import handong.whynot.dto.blind_date.enums.LocationEnum;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static handong.whynot.dto.blind_date.enums.CharacterEnum.getCharacterEnum;
import static handong.whynot.dto.blind_date.enums.ContactStyleEnum.getContactStyleEnum;
import static handong.whynot.dto.blind_date.enums.DateStyleEnum.getDateStyleEnum;
import static handong.whynot.dto.blind_date.enums.DepartmentEnum.getDepartmentEnum;
import static handong.whynot.dto.blind_date.enums.DrinkEnum.getDrinkEnum;
import static handong.whynot.dto.blind_date.enums.FaithEnum.getFaithEnum;
import static handong.whynot.dto.blind_date.enums.HobbyEnum.getHobbyEnum;
import static handong.whynot.dto.blind_date.enums.JobEnum.getJobEnum;
import static handong.whynot.dto.blind_date.enums.LocationEnum.getLocationEnum;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlindDateMatchingResponseDTO {

  private String name;
  private String gender;
  private Integer myAge;
  private Integer myHeight;
  private String department;
  private String smoke;
  private String faith;
  private String myDrink;
  private String location;
  private List<String> hobbyList;
  private String contactStyle;
  private List<String> characterList;
  private List<String> dateStyle;
  private String commentForMate;
  private String kakaoLink;
  private List<String> imageLinks;
  private String myName;
  private String myJob;
  private String myJobDesc;

  public static BlindDateMatchingResponseDTO of(BlindDate blindDate, String myName, List<String> images) {
    String department = getDepartmentEnum(blindDate.getDepartment()).getDesc();
    String smoke = StringUtils.equals(blindDate.getSmoke(), "Y") ? "흡연자" : "비흡연자";
    String faith = getFaithEnum(blindDate.getFaith()).getDesc();
    String drink = getDrinkEnum(blindDate.getMyDrink()).getDesc();
    String location = "";
    String job = getJobEnum(blindDate.getMyJob()).getDesc();
    LocationEnum locationEnum = getLocationEnum(blindDate.getMyLocation());
    if (locationEnum.equals(LocationEnum.DORMITORY)) {
      location = blindDate.getMyLocationDesc() + locationEnum.getDesc();
    }

    List<String> hobbies = Stream.of(blindDate.getHobby().split(";"))
      .map(it -> getHobbyEnum(it).getDesc())
      .collect(Collectors.toList());
    String contactStyle = getContactStyleEnum(blindDate.getMyContactStyle()).getDesc();
    List<String> characters = Stream.of(blindDate.getMyCharacter().split(";"))
      .map(it -> getCharacterEnum(it).getDesc())
      .collect(Collectors.toList());
    List<String> dateStyles = Stream.of(blindDate.getDateStyle().split(";"))
      .map(it -> getDateStyleEnum(it).getDesc())
      .collect(Collectors.toList());

    return builder()
      .name(blindDate.getName())
      .gender(blindDate.getGender())
      .myAge(blindDate.getMyAge())
      .myHeight(blindDate.getMyHeight())
      .department(department)
      .smoke(smoke)
      .faith(faith)
      .myDrink(drink)
      .location(location)
      .hobbyList(hobbies)
      .contactStyle(contactStyle)
      .characterList(characters)
      .dateStyle(dateStyles)
      .commentForMate(blindDate.getCommentForMate())
      .kakaoLink(blindDate.getKakaoLink())
      .imageLinks(images)
      .myName(myName)
      .myJob(job)
      .myJobDesc(blindDate.getMyJobDesc())
      .build();
  }
}
