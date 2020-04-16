
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
public class EUR {
    private String ID;
    private String NumCode;
    private String CharCode;
    private long Nominal;
    private String Name;
    private double Value;
    private double Previous;
}
