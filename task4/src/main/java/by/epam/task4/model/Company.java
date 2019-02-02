package by.epam.task4.model;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Company entity class.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "certificate",
        "aPackage",
        "dosage"
})
@XmlRootElement(name = "company")
public final class Company {

    /**
     * A certificate.
     */
    @XmlElement(required = true)
    private Certificate certificate;

    /**
     * A package.
     */
    @XmlElement(name = "package", required = true)
    private Package aPackage;

    /**
     * A dosage.
     */
    @XmlElement(required = true)
    private Dosage dosage;

    /**
     * A name.
     */
    @XmlAttribute(name = "name", required = true)
    private String name;

    @Override
    public String toString() {
        return    "\nName: " + name
                + "\nCertificate: " + certificate
                + "\nPackage: " + aPackage
                + "\nDosage: " + dosage;
    }
}
