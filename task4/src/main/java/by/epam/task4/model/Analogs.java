
package by.epam.task4.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Analogs entity class.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@Data
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"analogList"})
@XmlRootElement(name = "analogs")
public final class Analogs {

    /**
     * Analog list.
     */
    @XmlElement(required = true)
    private List<Analog> analogList = new ArrayList<>();

    /**
     * @return a string with enumeration of analogs
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Analog analog : analogList) {
            sb.append("Analog: ").append(analog);
        }
        return sb.toString();
    }
}
