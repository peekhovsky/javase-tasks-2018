package by.epam.task4.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Medicines entity class.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@Data
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"medicineList"})
@XmlRootElement(name = "medicines")
public final class Medicines {

    /**
     * A list of medicines.
     */
    @XmlElement(required = true)
    @Getter
    @Setter
    private List<Medicine> medicineList = new ArrayList<>();


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (Medicine medicine : medicineList) {
            res.append("\nMedicine: ").append(medicine);
        }
        return res.toString();
    }

}
