/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.base;

import java.util.List;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * The type Mail helper.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class MailHelper {

    private HttpHelper httpHelper;

    /**
     * Instantiates a new Mail helper.
     */
    public MailHelper() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHelper httpHelper = new HttpHelper(restTemplate, "");
        this.httpHelper = httpHelper;
    }

    /**
     * Send mail.
     *
     * @param mail the mail
     */
    public void sendMail(Mail mail) {
        try {
            httpHelper.process(Map.class, HttpMethod.POST, mail.getUrl(), "", mail);
        } catch (Exception e) {
            throw new RuntimeException("Mail no enviado con éxito", e);
        }
    }

    /**
     * Builder mail builder.
     *
     * @return the mail builder
     */
    public static MailBuilder builder() {
        return new MailBuilder();
    }

    /**
     * The type Mail builder.
     */
    public static class MailBuilder {

        /**
         * The Mail.
         */
        Mail mail = new Mail();

        /**
         * Create mail builder.
         *
         * @return the mail builder
         */
        static public MailBuilder create() {
            return new MailBuilder();
        }

        /**
         * Sets url.
         *
         * @param url the url
         * @return the url
         */
        public MailBuilder setUrl(String url) {
            mail.url = url;
            return this;
        }

        /**
         * Sets from.
         *
         * @param from the from
         * @return the from
         */
        public MailBuilder setFrom(String from) {
            mail.from = from;
            return this;
        }

        /**
         * Sets to.
         *
         * @param to the to
         * @return the to
         */
        public MailBuilder setTo(String to) {
            mail.to = to;
            return this;
        }

        /**
         * Sets cc.
         *
         * @param cc the cc
         * @return the cc
         */
        public MailBuilder setCc(String cc) {
            mail.cc = cc;
            return this;
        }

        /**
         * Sets subject.
         *
         * @param subject the subject
         * @return the subject
         */
        public MailBuilder setSubject(String subject) {
            mail.subject = subject;
            return this;
        }

        /**
         * Sets template url.
         *
         * @param resourceUrl the resource url
         * @return the template url
         */
        public MailBuilder setTemplateUrl(String resourceUrl) {
            mail.templateUrl = resourceUrl;
            return this;
        }

        /**
         * Sets params.
         *
         * @param params the params
         * @return the params
         */
        public MailBuilder setParams(Map<String, Object> params) {
            mail.params = params;
            return this;
        }

        /**
         * Sets attachments.
         *
         * @param attachments the attachments
         * @return the attachments
         */
        public MailBuilder setAttachments(List<Map<String, String>> attachments) {
            mail.attachment = attachments;
            return this;
        }

        /**
         * Build mail.
         *
         * @return the mail
         */
        public Mail build() {
            Mail result = this.mail;
            mail = new Mail();
            return result;
        }

    }

    /**
     * The type Mail.
     */
    public static class Mail {

        private String url;
        private String from;
        private String to;
        private String cc;
        private String subject;
        private String templateUrl;
        private Map<String, Object> params;
        private List<Map<String, String>> attachment;

        /**
         * Instantiates a new Mail.
         */
        public Mail() {
        }

        /**
         * Gets url.
         *
         * @return the url
         */
        public String getUrl() {
            return url;
        }

        /**
         * Gets from.
         *
         * @return the from
         */
        public String getFrom() {
            return from;
        }

        /**
         * Gets to.
         *
         * @return the to
         */
        public String getTo() {
            return to;
        }

        /**
         * Gets cc.
         *
         * @return the cc
         */
        public String getCc() {
            return cc;
        }

        /**
         * Gets subject.
         *
         * @return the subject
         */
        public String getSubject() {
            return subject;
        }

        /**
         * Gets template url.
         *
         * @return the template url
         */
        public String getTemplateUrl() {
            return templateUrl;
        }

        /**
         * Gets params.
         *
         * @return the params
         */
        public Map<String, Object> getParams() {
            return params;
        }

        /**
         * Gets attachment.
         *
         * @return the attachment
         */
        public List<Map<String, String>> getAttachment() {
            return attachment;
        }

        /**
         * Sets attachment.
         *
         * @param attachment the attachment
         */
        public void setAttachment(List<Map<String, String>> attachment) {
            this.attachment = attachment;
        }

    }
}
