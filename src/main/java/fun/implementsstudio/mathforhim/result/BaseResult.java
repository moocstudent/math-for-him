package fun.implementsstudio.mathforhim.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResult implements Serializable {
    private Integer code;
    private String msg;
    private Object data;
}
