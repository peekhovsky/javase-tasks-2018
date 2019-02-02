package by.epam.task4.model;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Dosage entity class.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "dosage")
public final class Dosage {

    /**
     * A number of portions of medicine per day.
     */
    @XmlAttribute(name = "valuePerDay", required = true)
    @XmlSchemaType(name = "unsignedInt")
    private long valuePerDay;

    /**
     * Period of medication intake.
     */
    @XmlAttribute(name = "periodDays", required = true)
    @XmlSchemaType(name = "unsignedInt")
    private long periodDays;

    @Override
    public String toString() {
        return "\nValue per day: " + valuePerDay
                + "\nPeriod in days: " + periodDays;
    }
}
