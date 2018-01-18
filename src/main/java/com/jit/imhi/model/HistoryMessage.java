package com.jit.imhi.model;

import java.util.Date;

public class HistoryMessage {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column history_message.message_id
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    private Integer messageId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column history_message.user_from_id
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    private Integer userFromId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column history_message.to_id
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    private Integer toId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column history_message.text_type
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    private Integer textType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column history_message.text_content
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    private String textContent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column history_message.message_type
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    private Integer messageType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column history_message.date
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    private Date date;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column history_message.message_id
     *
     * @return the value of history_message.message_id
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    public Integer getMessageId() {
        return messageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column history_message.message_id
     *
     * @param messageId the value for history_message.message_id
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column history_message.user_from_id
     *
     * @return the value of history_message.user_from_id
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    public Integer getUserFromId() {
        return userFromId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column history_message.user_from_id
     *
     * @param userFromId the value for history_message.user_from_id
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    public void setUserFromId(Integer userFromId) {
        this.userFromId = userFromId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column history_message.to_id
     *
     * @return the value of history_message.to_id
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    public Integer getToId() {
        return toId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column history_message.to_id
     *
     * @param toId the value for history_message.to_id
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    public void setToId(Integer toId) {
        this.toId = toId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column history_message.text_type
     *
     * @return the value of history_message.text_type
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    public Integer getTextType() {
        return textType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column history_message.text_type
     *
     * @param textType the value for history_message.text_type
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    public void setTextType(Integer textType) {
        this.textType = textType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column history_message.text_content
     *
     * @return the value of history_message.text_content
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    public String getTextContent() {
        return textContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column history_message.text_content
     *
     * @param textContent the value for history_message.text_content
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    public void setTextContent(String textContent) {
        this.textContent = textContent == null ? null : textContent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column history_message.message_type
     *
     * @return the value of history_message.message_type
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    public Integer getMessageType() {
        return messageType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column history_message.message_type
     *
     * @param messageType the value for history_message.message_type
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column history_message.date
     *
     * @return the value of history_message.date
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    public Date getDate() {
        return date;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column history_message.date
     *
     * @param date the value for history_message.date
     *
     * @mbggenerated Wed Jan 17 11:08:47 CST 2018
     */
    public void setDate(Date date) {
        this.date = date;
    }
}