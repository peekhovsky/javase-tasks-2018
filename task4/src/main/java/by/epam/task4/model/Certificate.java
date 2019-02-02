package by.epam.task4.model;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.Date;


/**
 * Certificate entity class.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "certificate")
public final class Certificate {

    /**
     * A number of certificate.
     */
    @XmlAttribute(name = "num", required = true)
    @XmlSchemaType(name = "unsignedLong")
    private Long num;

    /**
     * Issue date.
     */
    @XmlAttribute(name = "issueDate")
    @XmlSchemaType(name = "date")
    private Date issueDate;

    /**
     * Expire date.
     */
    @XmlAttribute(name = "expireDate", required = true)
    @XmlSchemaType(name = "date")
    private Date expireDate;

    /**
     * Registration organisation.
     */
    @XmlAttribute(name = "regOrganisation", required = true)
    private String regOrganisation;

    @Override
    public String toString() {
        return    "\nNumber: " + num
                + "\nIssue date: " + issueDate
                + "\nExpire dare: " + expireDate
                + "\nRegistration organisation: " + regOrganisation;
    }
}
