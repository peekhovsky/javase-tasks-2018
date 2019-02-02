package by.epam.task4.builder;

import by.epam.task4.model.*;
import by.epam.task4.property.AppProperties;
import lombok.Getter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Abstract class that contains data for XML builders for medicines class.
 *
 * @author Rostislav Pekhovsky 2018
 * @version 0.1
 */
public abstract class MedicinesXMLBuilder {

    /**
     * A date format for all dates in xml file (XML default dates).
     */
    @Getter
    protected static final DateFormat DATA_FORMAT = new SimpleDateFormat(
            AppProperties.getInstance().getDateFormat());

    /**
     * @return medicines
     */
    public abstract Medicines build();
}
