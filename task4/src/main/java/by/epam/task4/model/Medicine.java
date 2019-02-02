package by.epam.task4.model;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Medicine entity class.
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
@XmlType(name = "", propOrder = {
        "analogs",
        "versions"
})
@XmlRootElement(name = "medicine")
public final class Medicine {

    /**
     * Analog list.
     */
    private Analogs analogs;

    /**
     * Version list.
     */
    @XmlElement(required = true)
    private Versions versions;

    /**
     * Id of medicine.
     */
    @XmlAttribute(name = "id")
    private Integer id;

    /**
     * Name.
     */
    @XmlAttribute(name = "name", required = true)
    private String name;

    /**
     * Pharmacy company.
     */
    @XmlAttribute(name = "pharm", required = true)
    private String pharm;

    /**
     * Group (for instance, antibiotics).
     */
    @XmlAttribute(name = "group")
    private String group;


    @Override
    public String toString() {
        return "\nId: " + id
                + "\nName: " + name
                + "\nPharm: " + pharm
                + "\nGroup: " + group
                + "\nAnalogs: " + analogs
                + "\nVersions: " + versions;
    }
}
