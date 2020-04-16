package apimodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiDailyRequestModel {
    private String Date;
    private String PreviousDate;
    private String PreviousURL;
    private String Timestamp;
}