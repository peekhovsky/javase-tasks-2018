package by.epam.task4.model;

import lombok.*;

import javax.xml.bind.annotation.*;


/**
 * Package entity class.
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
@XmlRootElement(name = "package")
public final class Package {

    /**
     * Type of package (for instance, flask).
     */
    @XmlAttribute(name = "type", required = true)
    private String type;

    /**
     * Quantity of medicine in package.
     */
    @XmlAttribute(name = "quantity", required = true)
    @XmlSchemaType(name = "unsignedInt")
    private long quantity;

    /**
     * Price for this package.
     */
    @XmlAttribute(name = "price", required = true)
    @XmlSchemaType(name = "unsignedLong")
    private Long price;


    @Override
    public String toString() {
        return "\nType: " + type
                + "\nQuantity: " + quantity
                + "\nPrice: " + price;
    }
}
