package by.epam.task4.model;

import lombok.*;

import javax.xml.bind.annotation.*;

/**
 * Analog entity class.
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
@XmlRootElement(name = "analog")
public final class Analog {

    /**
     * Analog name.
     */
    @XmlAttribute(name = "name", required = true)
    @XmlSchemaType(name = "anySimpleType")
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
