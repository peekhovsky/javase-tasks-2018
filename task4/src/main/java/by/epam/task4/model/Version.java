package by.epam.task4.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Version entity class.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "companyList"
})
@XmlRootElement(name = "version")
public final class Version {

    /**
     * A list of companies.
     */
    @XmlElement(required = true)
    private List<Company> companyList = new ArrayList<>();

    /**
     * Type of version.
     */
    @XmlAttribute(name = "type", required = true)
    private String type = "pills";

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nType: ").append(type);
        sb.append("\nCompanies");
        for (Company company : companyList) {
            sb.append("\nCompany").append(company);
        }
        return sb.toString();
    }
}
