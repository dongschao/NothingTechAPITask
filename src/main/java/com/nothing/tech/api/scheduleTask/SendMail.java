package com.nothing.tech.api.scheduleTask;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
/**
 * Created by admin on 2017/12/18.
 */
public class SendMail extends Authenticator
{
//    private MailSender mailSender;
//    public void setMailSender(MailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//    public void send(){
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("dzw@mail.com");
//        message.setTo("tom");
//        message.setSubject("问好");
//        message.setText("好久不见，最近好吗？");
//        mailSender.send(message);
//    }
//
//    private String mailServer, mailPort,username, password;
//    private Session mailSession;
//    private Properties prop;
//    private Message message;
//
//    public SendMail(String mailServer, String mailPort, String username, String password) {
//        this.mailServer = mailServer;
//        this.mailPort = mailPort;
//        this.username = username;
//        this.password = password;
//    }
//
//    public void send(String to, String mailSubject, String mailContent) {
//        PasswordAuthentication mailauth =  new PasswordAuthentication(username, password);
//        // 设置邮件服务器
//        prop = System.getProperties();
//        prop.put("mail.smtp.auth", "true");
//        prop.put("mail.smtp.host", mailServer);
//        prop.put("mail.smtp.port", mailPort);
//        // 产生新的Session服务
//        mailSession = mailSession.getDefaultInstance(prop, (Authenticator) mailauth);
//        message = new MimeMessage(mailSession);
//
//        try {
//            message.setFrom(new InternetAddress(username)); // 设置发件人
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));// 设置收件人
//            message.setSubject(mailSubject);// 设置主题
////            message.setContent(mailContent, "text/plain");// 设置内容,中文乱码，用下面的方法
//
//            // 设置内容
//            Multipart multipart = new MimeMultipart();
//            MimeBodyPart mimeBodyPart = new MimeBodyPart();
//            mimeBodyPart.setText(mailContent);
////            mimeBodyPart.setFileName(MimeUtility.encodeText(mailContent, "GBK", "B")); //对邮件头采用base64方式编码
//            multipart.addBodyPart(mimeBodyPart);
//            message.setContent(multipart);
//
//            // 设置日期
//            message.setSentDate(new Date());
//            Transport tran = mailSession.getTransport("smtp");
//            tran.connect(mailServer, username, password);
//            tran.send(message, message.getAllRecipients());
//            tran.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



    public static final int SHOW_MESSAGES = 1;
    public static final int CLEAR_MESSAGES = 2;
    public static final int SHOW_AND_CLEAR =
            SHOW_MESSAGES + CLEAR_MESSAGES;

    protected String from;
    protected Session session;
    protected PasswordAuthentication authentication;

    public SendMail(String user, String host)
    {
        this(user, host, false);
    }

    public SendMail(String user, String host, boolean debug)
    {
        from = user + '@' + host;
        authentication = new PasswordAuthentication(user, user);  //由于创建的user和password一样，所以传两个user就OK
        Properties props = new Properties();
        props.put("mail.user", user);
        props.put("mail.host", host);
        props.put("mail.debug", debug ? "true" : "false");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        session = Session.getInstance(props, this);
    }

    public PasswordAuthentication getPasswordAuthentication()
    {
        return authentication;
    }

    public void sendMessage(
            String to, String cc, String subject, String content)
            throws MessagingException
    {
        System.out.println("SENDING message from " + from + " to " + to);
        System.out.println();
        MimeMessage msg = new MimeMessage(session);
        msg.addRecipients(Message.RecipientType.TO, to);  //添加收件人
        msg.addRecipients(Message.RecipientType.CC, cc);  //添加转发人
        msg.setSubject(subject);
        msg.setText(content);
        Transport.send(msg);
    }

    public void checkInbox(int mode)
            throws MessagingException, IOException
    {
        if (mode == 0) return;
        boolean show = (mode & SHOW_MESSAGES) > 0;
        boolean clear = (mode & CLEAR_MESSAGES) > 0;
        String action =
                (show ? "Show" : "") +
                        (show && clear ? " and " : "") +
                        (clear ? "Clear" : "");
        System.out.println(action + " INBOX for " + from);
        Store store = session.getStore();
        store.connect();
        Folder root = store.getDefaultFolder();
        Folder inbox = root.getFolder("inbox");
        inbox.open(Folder.READ_WRITE);
        Message[] msgs = inbox.getMessages();
        if (msgs.length == 0 && show)
        {
            System.out.println("No messages in inbox");
        }
        for (int i = 0; i < msgs.length; i++)
        {
            MimeMessage msg = (MimeMessage)msgs[i];
            if (show)
            {
                System.out.println("    From: " + msg.getFrom()[0]);
                System.out.println(" Subject: " + msg.getSubject());
                System.out.println(" Content: " + msg.getContent());
            }
            if (clear)
            {
                msg.setFlag(Flags.Flag.DELETED, true);
            }
        }
        inbox.close(true);
        store.close();
        System.out.println();
    }















}
