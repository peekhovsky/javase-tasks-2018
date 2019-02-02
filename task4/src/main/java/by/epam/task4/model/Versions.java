package by.epam.task4.model;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Versions entity class.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"versionList"})
@XmlRootElement(name = "versions")
public final class Versions {

    /**
     * A list of versions.
     */
    @XmlElement(required = true)
    private List<Version> versionList = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Version version : versionList) {
            sb.append("Version: ").append(version);
        }
        return sb.toString();
    }
}
