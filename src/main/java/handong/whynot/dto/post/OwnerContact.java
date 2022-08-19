package handong.whynot.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OwnerContact {

   private ContactType type;
   private String value;
}
