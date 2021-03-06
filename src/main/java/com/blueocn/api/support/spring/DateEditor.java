package com.blueocn.api.support.spring;

import com.blueocn.api.support.utils.DateUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Title: 时间工具类
 * Description: 用于解析字符串为时间类型的工具
 *
 * @author Yufan
 * @version 1.0.0
 * @since 2015-12-15 16:38
 */
public class DateEditor extends PropertyEditorSupport {

    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DateFormat dateFormat;
    private boolean allowEmpty = true;

    public DateEditor() {
        // Nothing to do
    }

    public DateEditor(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DateEditor(DateFormat dateFormat, boolean allowEmpty) {
        this.dateFormat = dateFormat;
        this.allowEmpty = allowEmpty;
    }

    /**
     * Parse the Date from the given text, using the specified DateFormat.
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            // Treat empty String as null value.
            setValue(null);
        } else {
            try {
                if (this.dateFormat != null)
                    setValue(this.dateFormat.parse(text));
                else {
                    setValue(DateUtils.parse(text));
                }
            } catch (ParseException ex) {
                throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
            }
        }
    }

    /**
     * Format the Date as String, using the specified DateFormat.
     * May have some concurrent issues.
     */
    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        DateFormat format = this.dateFormat;
        if (format == null)
            format = TIME_FORMAT;
        return value != null ? format.format(value) : "";
    }
}
