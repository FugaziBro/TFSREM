
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
public class USD {

    private String CharCode;
    private String ID;
    private String Name;
    private long Nominal;
    private String NumCode;
    private double Previous;
    private double Value;

}
