package org.example.util;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

/**
 * double converter.
 * implementation of AbstractbeanField to convert double values from file
 */
public class DoubleConverter extends AbstractBeanField<String, String> {

    /**
     * {@inheritDoc}
     * @return converted double value
     */
    @Override
    protected Object convert(final String value)
                                         throws CsvDataTypeMismatchException {
        return Double.parseDouble(value.replace(",", "."));
        // Replace comma with period for decimal separator
    }
}
