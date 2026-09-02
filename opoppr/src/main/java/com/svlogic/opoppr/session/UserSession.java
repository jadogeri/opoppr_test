package com.svlogic.opoppr.session;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.NamingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import com.password4j.Password;
import com.svlogic.opoppr.annotation.CurrentForm;
import com.svlogic.opoppr.annotation.CurrentUser;
import com.svlogic.opoppr.annotation.CurrentUserSession;
import com.svlogic.opoppr.app.AppListener;
import com.svlogic.opoppr.controllers.FormJpaController;
import com.svlogic.opoppr.controllers.NoaPpLat5FilingJpaController;
import com.svlogic.opoppr.controllers.NoaPpLat5InventoriesJpaController;
import com.svlogic.opoppr.controllers.NoaPpLat5JpaController;
import com.svlogic.opoppr.controllers.UserChangeJpaController;
import com.svlogic.opoppr.controllers.UserJpaController;
import com.svlogic.opoppr.model.Form;
import com.svlogic.opoppr.model.NoaPpLat5;
import com.svlogic.opoppr.model.NoaPpLat5Filing;
import com.svlogic.opoppr.model.NoaPpLat5Inventories;
import com.svlogic.opoppr.model.PropertyAsset;
import com.svlogic.opoppr.model.Status;
import com.svlogic.opoppr.model.User;
import com.svlogic.opoppr.model.UserChange;
import com.svlogic.opoppr.model.UserChangeType;
import com.svlogic.opoppr.model.UserRole;
import com.svlogic.opoppr.model.UserStatus;

/**
 *
 * @author David
 */
@Named("userSession")
@CurrentUserSession
@SessionScoped
public class UserSession implements Serializable {
    static private Logger logger = Logger.getLogger(UserSession.class.getName());

    static final long VERIFICATION_CODE_VALID_DURATION = 1L * 60L * 60L * 1000L;
    static final String VERIFICATION_CODE_VALID_DURATION_STRING = "1 hour";

    static final int MAX_FAILED_LOGINS = 3;

    private User currentUser;
    private Form currentForm;

    public LoginResult login(String username, String password) {
        LoginResult ret = LoginResult.FAILED;
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();

        UserJpaController userJpaController = new UserJpaController(AppListener.getEntityManagerFactory());
        User user = userJpaController.findUserByUsername(username);
        try {
            if (user != null && user.getUserStatus().equals(UserStatus.ENABLED)
                    && checkPassword(password, user.getPassword())) {
                request.login(username, user.getPassword());
                setCurrentUser(user);
                user.setLastLoginTime(new Date());
                user.setFailedLogins(0);
                userJpaController.edit(user);
                ret = LoginResult.SUCCESS;
            } else {
                if (user != null) {
                    if (user.getUserStatus().equals(UserStatus.ENABLED)) {
                        user.setFailedLogins(user.getFailedLogins() != null ? user.getFailedLogins() + 1 : 1);
                        if (user.getFailedLogins() >= MAX_FAILED_LOGINS) {
                            user.setUserStatus(UserStatus.LOCKED);
                        }
                        userJpaController.edit(user);
                    } else if (user.getUserStatus().equals(UserStatus.LOCKED)) {
                        ret = LoginResult.LOCKED;
                    }
                }
            }
        } catch (ServletException se) {
            logger.log(Level.WARNING, "An error occurred in login", se);
        }

        return ret;
    }

    public void logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        currentUser = null;
    }

    public boolean createUserAccount(String username, String password, String fullName, String phoneNumber,
            String emailAddress) {
        boolean ret = true;
        String sendConfirmationEmail = System.getProperty("sendConfirmationEmail");
        String fromAddress = System.getProperty("sendFromAddress");
        String fromPerson = System.getProperty("sendFromPerson");
        UserJpaController userJpaController = new UserJpaController(AppListener.getEntityManagerFactory());

        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(hashPassword(password));
            user.setFullName(fullName);
            user.setPhoneNumber(phoneNumber);
            user.setEmailAddress(emailAddress);
            user.setUserStatus(UserStatus.DISABLED);
            user.setUserRoleId(UserRole.TAX_PREPARER);
            userJpaController.create(user);

            UserChangeJpaController userChangeJpaController = new UserChangeJpaController(
                    AppListener.getEntityManagerFactory());
            UserChange userChange = new UserChange();
            userChange.setUserId(user);
            userChange.setUserChangeTypeId(UserChangeType.ACTIVATE);
            userChange.setVerificationCode(UUID.randomUUID().toString());
            userChangeJpaController.create(userChange);

            // TODO: validate that fromAddress is an email address.
            if (sendConfirmationEmail != null && sendConfirmationEmail.equalsIgnoreCase("true")
                    && fromAddress != null && !fromAddress.isEmpty()) {
                sendVerificationEmail(fromAddress, fromPerson, user.getEmailAddress(), user.getFullName(),
                        userChange.getVerificationCode());
            }
        } catch (RuntimeException re) {
            logger.log(Level.WARNING, "An error occurred in createUserAccount", re);
            ret = false;
        }

        return ret;
    }



    public boolean adminCreateUserAccount(String username, String password, String fullName, String phoneNumber,
            String emailAddress,UserRole userRole) {
        boolean ret = true;
        String sendConfirmationEmail = System.getProperty("sendConfirmationEmail");
        String fromAddress = System.getProperty("sendFromAddress");
        String fromPerson = System.getProperty("sendFromPerson");
        UserJpaController userJpaController = new UserJpaController(AppListener.getEntityManagerFactory());

        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(hashPassword(password));
            user.setFullName(fullName);
            user.setPhoneNumber(phoneNumber);
            user.setEmailAddress(emailAddress);
            user.setUserStatus(UserStatus.ENABLED);
            user.setUserRoleId(userRole);
            userJpaController.create(user);

            UserChangeJpaController userChangeJpaController = new UserChangeJpaController(
                    AppListener.getEntityManagerFactory());
            UserChange userChange = new UserChange();
            userChange.setUserId(user);
            userChange.setUserChangeTypeId(UserChangeType.ACTIVATE);
            userChange.setVerificationCode(UUID.randomUUID().toString());
            userChangeJpaController.create(userChange);

            // TODO: validate that fromAddress is an email address.
            if (sendConfirmationEmail != null && sendConfirmationEmail.equalsIgnoreCase("true")
                    && fromAddress != null && !fromAddress.isEmpty()) {
                sendVerificationEmail(fromAddress, fromPerson, user.getEmailAddress(), user.getFullName(),
                        userChange.getVerificationCode());
            }
        } catch (RuntimeException re) {
            logger.log(Level.WARNING, "An error occurred in adminCreateUserAccount", re);
            ret = false;
        }

        return ret;
    }


    private String hashPassword(String clearTextPassword) {
        return AppListener.hashPassword(clearTextPassword);
    }

    private boolean checkPassword(String clearTextPassword, String hashedPassword) {
        return AppListener.checkPassword(clearTextPassword, hashedPassword);
    }

    public void resendVerificationEmail(String expiredVerificationCode) {
        String sendConfirmationEmail = System.getProperty("sendConfirmationEmail");
        String fromAddress = System.getProperty("sendFromAddress");
        String fromPerson = System.getProperty("sendFromPerson");

        UserChangeJpaController userChangeJpaController = new UserChangeJpaController(
                AppListener.getEntityManagerFactory());
        UserChange userChange = userChangeJpaController.findUserChangeByVerificationCode(expiredVerificationCode);
        if (userChange != null) {
            User user = userChange.getUserId();
            UserChange newUserChange = new UserChange();
            newUserChange.setUserId(user);
            newUserChange.setVerificationCode(UUID.randomUUID().toString());
            newUserChange.setInitiatedTime(new Date());
            newUserChange.setUserChangeTypeId(userChange.getUserChangeTypeId());
            userChangeJpaController.create(newUserChange);
            userChangeJpaController.destroy(userChange.getUserChangeId());

            // TODO: validate that fromAddress is an email address.
            if (sendConfirmationEmail != null && sendConfirmationEmail.equalsIgnoreCase("true")
                    && fromAddress != null && !fromAddress.isEmpty()) {
                sendVerificationEmail(fromAddress, fromPerson, user.getEmailAddress(), user.getFullName(),
                        newUserChange.getVerificationCode());
            }
        }
    }

    public boolean submitResetPasswordRequest(String emailAddress) {
        boolean ret = false;
        String sendConfirmationEmail = System.getProperty("sendConfirmationEmail");
        String fromAddress = System.getProperty("sendFromAddress");
        String fromPerson = System.getProperty("sendFromPerson");
        UserChangeJpaController userChangeJpaController = new UserChangeJpaController(
                AppListener.getEntityManagerFactory());
        UserJpaController userJpaController = new UserJpaController(AppListener.getEntityManagerFactory());

        User user = userJpaController.findUserByEmailAddress(emailAddress);
        if (user != null && !user.getUserStatus().equals(UserStatus.DISABLED)) {
            UserChange userChange = new UserChange();
            userChange.setUserId(user);
            userChange.setUserChangeTypeId(UserChangeType.CHANGE_PASSWORD);
            userChange.setVerificationCode(UUID.randomUUID().toString());
            userChangeJpaController.create(userChange);
            ret = true;

            // TODO: validate that fromAddress is an email address.
            if (sendConfirmationEmail != null && sendConfirmationEmail.equalsIgnoreCase("true")
                    && fromAddress != null && !fromAddress.isEmpty()) {
                sendResetPasswordEmail(fromAddress, fromPerson, user.getEmailAddress(), user.getFullName(),
                        userChange.getVerificationCode());
            }
        }

        return ret;
    }

    public void resendResetPasswordEmail(String expiredVerificationCode) {
        String sendConfirmationEmail = System.getProperty("sendConfirmationEmail");
        String fromAddress = System.getProperty("sendFromAddress");
        String fromPerson = System.getProperty("sendFromPerson");

        UserChangeJpaController userChangeJpaController = new UserChangeJpaController(
                AppListener.getEntityManagerFactory());
        UserChange userChange = userChangeJpaController.findUserChangeByVerificationCode(expiredVerificationCode);
        if (userChange != null) {
            User user = userChange.getUserId();
            UserChange newUserChange = new UserChange();
            newUserChange.setUserId(user);
            newUserChange.setVerificationCode(UUID.randomUUID().toString());
            newUserChange.setInitiatedTime(new Date());
            newUserChange.setUserChangeTypeId(userChange.getUserChangeTypeId());
            userChangeJpaController.create(newUserChange);
            userChangeJpaController.destroy(userChange.getUserChangeId());

            // TODO: validate that fromAddress is an email address.
            if (sendConfirmationEmail != null && sendConfirmationEmail.equalsIgnoreCase("true")
                    && fromAddress != null && !fromAddress.isEmpty()) {
                sendResetPasswordEmail(fromAddress, fromPerson, user.getEmailAddress(), user.getFullName(),
                        newUserChange.getVerificationCode());
            }
        }
    }

    public int checkVerificationCode(String verificationCode) {
        int ret = 1;
        UserChangeJpaController userChangeJpaController = new UserChangeJpaController(
                AppListener.getEntityManagerFactory());
        UserChange userChange = userChangeJpaController.findUserChangeByVerificationCode(verificationCode);
        if (userChange == null) {
            ret = 0;
        } else if (new Date().getTime() - userChange.getInitiatedTime().getTime() > VERIFICATION_CODE_VALID_DURATION) {
            ret = -1;
        }
        return ret;
    }

    public boolean verifyEmailAddress(String verificationCode, String password) {
        boolean ret = false;

        UserChangeJpaController userChangeJpaController = new UserChangeJpaController(
                AppListener.getEntityManagerFactory());
        UserJpaController userJpaController = new UserJpaController(AppListener.getEntityManagerFactory());
        if (checkVerificationCode(verificationCode) == 1) {
            UserChange userChange = userChangeJpaController.findUserChangeByVerificationCode(verificationCode);
            User user = userChange.getUserId();
            if (userChange != null && checkPassword(password, user.getPassword())) {
                user.setUserStatus(UserStatus.ENABLED);
                userJpaController.edit(user);
                ret = true;
                userChangeJpaController.destroy(userChange.getUserChangeId());
            }
        }
        return ret;
    }

    public boolean resetPassword(String verificationCode, String newPassword) {
        boolean ret = false;

        UserChangeJpaController userChangeJpaController = new UserChangeJpaController(
                AppListener.getEntityManagerFactory());
        UserJpaController userJpaController = new UserJpaController(AppListener.getEntityManagerFactory());
        if (checkVerificationCode(verificationCode) == 1) {
            UserChange userChange = userChangeJpaController.findUserChangeByVerificationCode(verificationCode);
            if (userChange != null) {
                User user = userChange.getUserId();
                if (!user.getUserStatus().equals(UserStatus.DISABLED)) {
                    if (!user.getUserStatus().equals(UserStatus.DISABLED)) {
                    user.setPassword(hashPassword(newPassword));
                    user.setUserStatus(UserStatus.ENABLED);
                    user.setFailedLogins(0);
                    userJpaController.edit(user);
                    ret = true;
                    userChangeJpaController.destroy(userChange.getUserChangeId());
                }
                }
            }
        }
        return ret;
    }
    public boolean resetProfilePassword(Integer userId, String newPassword) {
        boolean ret = false;

        UserJpaController userJpaController = new UserJpaController(AppListener.getEntityManagerFactory());
            User user = userJpaController.findUser(userId);
            if (user instanceof  User) {
                user.setPassword(hashPassword(newPassword));
                user.setUserStatus(UserStatus.ENABLED);
                user.setFailedLogins(0);
                userJpaController.edit(user);
                ret = true;
            }

        return ret;
    }
    
    public boolean resetPhoneNumber(Integer userId, String phoneNumber) {
        boolean ret = false;

        UserJpaController userJpaController = new UserJpaController(AppListener.getEntityManagerFactory());
            User user = userJpaController.findUser(userId);
            if (user instanceof  User) {
                user.setPhoneNumber(phoneNumber);
                userJpaController.edit(user);
                ret = true;
            }

        return ret;
    }

    public boolean updateProfile(Integer userId, User updatedUser) {
        boolean ret = false;

        UserJpaController userJpaController = new UserJpaController(AppListener.getEntityManagerFactory());
            User user = userJpaController.findUser(userId);
            if (user instanceof User) {
                user.setUsername(updatedUser.getUsername());
                user.setFullName(updatedUser.getFullName());
                user.setPhoneNumber(updatedUser.getPhoneNumber());
                userJpaController.edit(user);
                ret = true;
            }

        return ret;

    }

    public boolean isAdmin() {
        return currentUser != null && currentUser.getUserRoleId().equals(UserRole.ADMINISTRATOR);
    }

    public boolean isSuper() {
        return currentUser != null && currentUser.getUserRoleId().equals(UserRole.SUPERUSER);
    }

    public boolean isUserEnabled() {
        return currentUser != null && currentUser.getUserStatus().equals(UserStatus.ENABLED);
    }
    
    public Collection<Form> getForms() {
        ArrayList<Form> ret = new ArrayList<Form>();

        if (getCurrentUser() != null) {
            for (Form f : getCurrentUser().getFormCollection()) {
                ret.add(f);
            }
        }

        ret.sort((f1, f2) -> {
            int result = -Integer.compare(f1.getFilingYear(), f2.getFilingYear());
            if (result == 0) {
                result = Integer.compare(f1.getStatus().getStatusId(), f2.getStatus().getStatusId());
            }
            return result;
        });

        return ret;
    }

    public Collection<Form> getAllForms(int filingYear) {
        ArrayList<Form> ret = new ArrayList<Form>();

        FormJpaController formJpaController = new FormJpaController(AppListener.getEntityManagerFactory());
        ret.addAll(formJpaController.findFormsByFilingYear(filingYear));

        ret.sort((f1, f2) -> {
            int result = -Integer.compare(f1.getFilingYear(), f2.getFilingYear());
            if (result == 0) {
                result = Integer.compare(f1.getStatus().getStatusId(), f2.getStatus().getStatusId());
            }
            return result;
        });

        return ret;
    }

    public void setCurrentForm(Form form) {
        this.currentForm = form;
    }

    @Produces
    @Named("currentForm")
    @RequestScoped
    @CurrentForm
    public Form getCurrentForm() {
        return this.currentForm;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Produces
    @Named("currentUser")
    @SessionScoped
    @CurrentUser
    public User getCurrentUser() {
        return currentUser;
    }

    public String getUserFullName() {
        return currentUser != null ? currentUser.getFullName() : "";
    }

    public boolean isCurrentFormSubmitted() {
        return getCurrentForm().getUserId() == null
                || getCurrentForm().getUserId().getUserId() != getCurrentUser().getUserId()
                || getCurrentForm().getStatus().equals(Status.SUBMITTED)
                || getCurrentForm().getStatus().equals(Status.CLOSED);
    }

    public boolean fileNewLAT5(String billNumber, String PIN) {
        boolean ret = false;
        UserJpaController userJpaController = new UserJpaController(AppListener.getEntityManagerFactory());
        FormJpaController formJpaController = new FormJpaController(AppListener.getEntityManagerFactory());
        Form form = formJpaController.findFormByBillNumberAndPINAndStatusName(billNumber, PIN, "New");
        if (form != null && form.getUserId() == null) {
            getCurrentUser().getFormCollection().add(form);
            userJpaController.edit(getCurrentUser());
            ret = true;
        }
        return ret;
    }

    public void storeBusinessInfo(NoaPpLat5 noaPpLat5) {
        NoaPpLat5JpaController noaPpLat5JpaController = new NoaPpLat5JpaController(
                AppListener.getEntityManagerFactory());
        noaPpLat5JpaController.edit(noaPpLat5);
        updateCurrentFormStatus(Status.IN_PROGRESS);
    }

    public void storeInventories(Collection<NoaPpLat5Inventories> inventories) {
        NoaPpLat5InventoriesJpaController noaPpLat5InventoriesJpaController = new NoaPpLat5InventoriesJpaController(
                AppListener.getEntityManagerFactory());
        NoaPpLat5 noaPpLat5 = getCurrentForm().getNoaPpLat5Collection().get(0);
        for (NoaPpLat5Inventories i : inventories) {
            if (i.getNoaPpLat5InventoriesId() == null) {
                i.setNoaPpLat5(noaPpLat5);
                i.setParid(noaPpLat5.getParid());
                i.setJur(noaPpLat5.getJur());
                i.setFileyr(noaPpLat5.getTaxyr());
                i.setTaxyr(noaPpLat5.getTaxyr());
                noaPpLat5InventoriesJpaController.create(i);
                noaPpLat5.getNoaPpLat5InventoriesCollection().add(i);
            } else {
                noaPpLat5InventoriesJpaController.edit(i);
            }
        }
        updateCurrentFormStatus(Status.IN_PROGRESS);
    }

    public void storeFilings(Collection<NoaPpLat5Filing> filings) {
        NoaPpLat5FilingJpaController noaPpLat5FilingJpaController = new NoaPpLat5FilingJpaController(
                AppListener.getEntityManagerFactory());
        NoaPpLat5 noaPpLat5 = getCurrentForm().getNoaPpLat5Collection().get(0);
        for (NoaPpLat5Filing f : filings) {
            if (f.getPropertyAsset() != null) {
                PropertyAsset pa = f.getPropertyAsset();
                f.setCategory(pa.getCategory());
                f.setPptype(pa.getPptype());
                f.setEffectiveLife(pa.getEffectiveLife());
            } else {
                f.setCategory("99");
                f.setPptype("0");
            }

            if (f.getNoaPpLat5FilingId() == null) {
                f.setNoaPpLat5(noaPpLat5);
                f.setParid(noaPpLat5.getParid());
                f.setJur(noaPpLat5.getJur());
                f.setFileyr(noaPpLat5.getTaxyr());
                f.setTaxyr(noaPpLat5.getTaxyr());

                noaPpLat5FilingJpaController.create(f);
                noaPpLat5.getNoaPpLat5FilingCollection().add(f);
            } else {
                noaPpLat5FilingJpaController.edit(f);
            }
        }
        updateCurrentFormStatus(Status.IN_PROGRESS);
    }

    public void deleteFilings(List<NoaPpLat5Filing> filings) {
        NoaPpLat5FilingJpaController noaPpLat5FilingJpaController = new NoaPpLat5FilingJpaController(
                AppListener.getEntityManagerFactory());
        for (NoaPpLat5Filing f : filings) {
            noaPpLat5FilingJpaController.destroy(f.getNoaPpLat5FilingId());
        }
        filings.clear();
        updateCurrentFormStatus(Status.IN_PROGRESS);
    }

    public void submitCurrentForm() {
        updateCurrentFormStatus(Status.SUBMITTED);
        String sendConfirmationEmail = System.getProperty("sendConfirmationEmail");
        String fromAddress = System.getProperty("sendFromAddress");
        String fromPerson = System.getProperty("sendFromPerson");
        // TODO: validate that fromAddress is an email address.
        if (sendConfirmationEmail != null && sendConfirmationEmail.equalsIgnoreCase("true")
                && fromAddress != null && !fromAddress.isEmpty()) {
            sendConfirmationEmail(fromAddress, fromPerson, getCurrentUser().getFullName());
        }
    }

    public void unsubmitCurrentForm() {
        updateCurrentFormStatus(Status.IN_PROGRESS);
    }

    private void updateCurrentFormStatus(Status status) {
        FormJpaController formJpaController = new FormJpaController(AppListener.getEntityManagerFactory());
        getCurrentForm().setStatus(status);
        getCurrentForm().setLastModifiedDate(new Date());
        formJpaController.edit(getCurrentForm());
    }

    private Session getEmailSession() {
        Session session = null;
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", System.getProperty("smtpHost"));
        props.setProperty("mail.smtp.port", System.getProperty("smtpPort"));
        props.setProperty("mail.smtp.starttls.enable", System.getProperty("smtpStartTLSEnable"));
        props.setProperty("mail.smtp.auth", System.getProperty("smtpAuth"));

        session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(System.getProperty("smtpUsername"),
                        System.getProperty("smtpPassword"));
            }
        });
        return session;
    }

    private void sendVerificationEmail(String fromAddress, String fromPerson, String toAddress, String name,
            String verificationCode) {
        logger.info("Sending verification email.");

        Session session = null;
        String siteHostname = System.getProperty("siteHostname");
        String siteRoot = System.getProperty("siteRoot", "");

        Context c = null;
        try {
            session = getEmailSession();
            Message message = new MimeMessage(session);

            InternetAddress from = new InternetAddress();
            from.setAddress(fromAddress);
            from.setPersonal(fromPerson);
            message.setFrom(from);

            ArrayList<InternetAddress> recipients = new ArrayList<InternetAddress>();

            InternetAddress to = new InternetAddress();
            to.setAddress(toAddress);
            recipients.add(to);

            message.setRecipients(Message.RecipientType.TO, recipients.toArray(new InternetAddress[recipients.size()]));

            message.setSubject("OPOPPR - User Email Verificaton - %s".formatted(name));

            String content = """
                    <html>
                      <body>
                        <p>
                          Dear %s;
                        </p>
                        <p>
                          Please click <a href="https://%s%s/verifyEmailAddress.xhtml?verificationCode=%s">THIS LINK</a> to verify your email address in order to activate your OPOPPR account.
                        </p>
                        <p>
                          This link will expire in %s.
                        </p>
                        <p>
                          If you have any problems setting up your account, please contact our office.
                        </p>
                        <p>
                          Thank you.
                        </p>
                        <p>
                          Orleans Parish Assessor's Office<br/>
                          Business Personal Property Department<br/>
                          Contact Phone: (504) 754-8818<br/>
                          Contact Email: personal_property@orleansassessors.com
                        </p>
                      </body>
                    </html>
                    """
                    .formatted(name, siteHostname, siteRoot, verificationCode, VERIFICATION_CODE_VALID_DURATION_STRING);
            message.setContent(content, "text/html");
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (NamingException ne) {
                }
            }
        }
    }

    private void sendResetPasswordEmail(String fromAddress, String fromPerson, String toAddress, String name,
            String verificationCode) {
        logger.info("Sending reset password email.");

        Session session = null;
        String siteHostname = System.getProperty("siteHostname");
        String siteRoot = System.getProperty("siteRoot", "");

        Context c = null;
        try {
            session = getEmailSession();
            Message message = new MimeMessage(session);

            InternetAddress from = new InternetAddress();
            from.setAddress(fromAddress);
            from.setPersonal(fromPerson);
            message.setFrom(from);

            ArrayList<InternetAddress> recipients = new ArrayList<InternetAddress>();

            InternetAddress to = new InternetAddress();
            to.setAddress(toAddress);
            recipients.add(to);

            message.setRecipients(Message.RecipientType.TO, recipients.toArray(new InternetAddress[recipients.size()]));

            message.setSubject("OPOPPR - Reset Password - " + name);

            String content = """
                    <html>
                      <body>
                        <p>
                          Dear %s;
                        </p>
                        <p>
                          Please click <a href="https://%s%s/resetPassword.xhtml?verificationCode=%s">THIS LINK</a> to reset your password.
                        </p>
                        <p>
                            This link will expire in %s.
                        </p>
                        <p>
                          Thank you.
                        </p>
                        <p>
                          Orleans Parish Assessor's Office<br/>
                          Business Personal Property Department<br/>
                          Contact Phone: (504) 754-8818<br/>
                          Contact Email: personal_property@orleansassessors.com
                        </p>
                      </body>
                    </html>
                    """
                    .formatted(name, siteHostname, siteRoot, verificationCode, VERIFICATION_CODE_VALID_DURATION_STRING);
            message.setContent(content, "text/html");
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (NamingException ne) {
                }
            }
        }
    }

    private void sendConfirmationEmail(String fromAddress, String fromPerson, String name) {
        logger.info("Sending confirmation email.");

        Session session = null;
        Context c = null;
        try {
            session = getEmailSession();
            Message message = new MimeMessage(session);

            InternetAddress from = new InternetAddress();
            from.setAddress(fromAddress);
            from.setPersonal(fromPerson);
            message.setFrom(from);

            NoaPpLat5 noaPpLat5 = getCurrentForm().getNoaPpLat5Collection().get(0);
            ArrayList<InternetAddress> recipients = new ArrayList<InternetAddress>();

            InternetAddress to = new InternetAddress();
            to.setAddress(noaPpLat5.getContactEmail());
            recipients.add(to);

            if (!noaPpLat5.getTaxPreparerEmail().equals(noaPpLat5.getContactEmail())) {
                to = new InternetAddress();
                to.setAddress(getCurrentForm().getNoaPpLat5Collection().get(0).getTaxPreparerEmail());
                recipients.add(to);
            }

            message.setRecipients(Message.RecipientType.TO, recipients.toArray(new InternetAddress[recipients.size()]));

            message.setSubject("OPOPPR - Confirmation - ID: " + getCurrentForm().getFormId());

            String content = """
                    <html>
                      <body>
                        <p>Dear %s:</p>
                        <p>This is a confirmation that you have submitted a LAT5 on %s for:</p>
                        <p>
                          Business Name: %s<br/>
                          Business Address: %s<br/>
                          Tax Bill number: %s
                        <p>
                        <p>We will review the LAT5 and contact you if additional information is required.</p>
                        <p>Thank you.</p>
                        <p>
                          Orleans Parish Assessor's Office<br/>
                          Business Personal Property Department<br/>
                          Contact Phone: (504) 754-8818<br/>
                          Contact Email: personal_property@orleansassessors.com
                        </p>
                      </body>
                    </html>
                    """.formatted(name,
                    DateFormat.getDateInstance().format(getCurrentForm().getLastModifiedDate()),
                    getCurrentForm().getNoaPpLat5Collection().get(0).getOwnername(),
                    getCurrentForm().getNoaPpLat5Collection().get(0).getAddr1(),
                    getCurrentForm().getBillNumber());
            message.setContent(content, "text/html");
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (NamingException ne) {
                }
            }
        }
    }

    public boolean validateCurrentForm() {
        NoaPpLat5InventoriesJpaController noaPpLat5InventoriesJpaController = new NoaPpLat5InventoriesJpaController(
                AppListener.getEntityManagerFactory());
        NoaPpLat5FilingJpaController noaPpLat5FilingJpaController = new NoaPpLat5FilingJpaController(
                AppListener.getEntityManagerFactory());

        NoaPpLat5 noaPpLat5 = getCurrentForm().getNoaPpLat5Collection().get(0);

        return noaPpLat5InventoriesJpaController.getNoaPpLat5InventoriesCountFilledIn(noaPpLat5) != 0
                || noaPpLat5FilingJpaController.getNoaPpLat5FilingCountFilledIn(noaPpLat5) != 0;
    }

    public String getEmptyTableMessage() {
        String ret;

        if (!getCurrentForm().getStatus().equals(Status.SUBMITTED)) {
            ret = "Click <strong>Add New Row</strong> button to add entries.";
        } else {
            ret = "No records found.";
        }

        return ret;
    }

    public Form getForm(String billNumber, int filingYear) {
        FormJpaController formJpaController = new FormJpaController(AppListener.getEntityManagerFactory());
        return formJpaController.findFormByBillNumberAndFilingYear(billNumber, filingYear);
    }

    public List<NoaPpLat5Filing> getCurrentFormFilingsWithCategory(String category) {
        return getCurrentForm().getNoaPpLat5Collection().get(0).getNoaPpLat5FilingCollection()
                .stream()
                .filter(f -> f.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    public Long getCurrentFormSumOfFilingsWithCategory(String category) {
        return getCurrentFormFilingsWithCategory(category)
                .stream()
                .mapToLong(f -> f.getAcquisitionCost())
                .sum();
    }

    public void flushEntityManagerCache() {
        AppListener.getEntityManagerFactory().getCache().evictAll();
    }

	public boolean adminEnableUserAccount(User user) {
        boolean ret = false;
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();

        UserJpaController userJpaController = new UserJpaController(AppListener.getEntityManagerFactory());
        User registeredUser = userJpaController.findUserByEmailAddress(user.getEmailAddress());
        try {
            if(registeredUser == null) {
                logger.log(Level.WARNING, "user not found: " + user.getEmailAddress() + " by "
                        + request.getRemoteAddr() + " (" + request.getRemoteHost() + ")");
                ret = false;
            }else {
                if(!registeredUser.getUserStatus().equals(UserStatus.ENABLED)){
                    registeredUser.setUserStatus(UserStatus.ENABLED);
                }
                if(registeredUser.getFailedLogins() != null && registeredUser.getFailedLogins() > 0){
                    registeredUser.setFailedLogins(0);
                }       

                userJpaController.edit(registeredUser);
                ret = true;
            }
            return ret;
       
        } catch (RuntimeException se) {
            logger.log(Level.WARNING, "An error occurred in enabling user account", se);
            return ret;
        }

	}

	public boolean adminLockUserAccount(User user) {
      boolean ret = false;
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();

        UserJpaController userJpaController = new UserJpaController(AppListener.getEntityManagerFactory());
        User registeredUser = userJpaController.findUserByEmailAddress(user.getEmailAddress());
        try {
            if(registeredUser == null) {
                logger.log(Level.WARNING, "user not found: " + user.getEmailAddress() + " by "
                        + request.getRemoteAddr() + " (" + request.getRemoteHost() + ")");
                ret = false;
            }else {
                if(!registeredUser.getUserStatus().equals(UserStatus.LOCKED)){
                    registeredUser.setUserStatus(UserStatus.LOCKED);
                }
                if(registeredUser.getFailedLogins() != null && !(registeredUser.getFailedLogins() < MAX_FAILED_LOGINS)){
                    registeredUser.setFailedLogins(MAX_FAILED_LOGINS);
                }  
                if(!registeredUser.getUserStatus().equals(UserStatus.LOCKED)){
                    registeredUser.setUserStatus(UserStatus.LOCKED);
                }

                userJpaController.edit(registeredUser);
                ret = true;
            }
            return ret;
       
        } catch (RuntimeException se) {
            logger.log(Level.WARNING, "An error occurred in locking user account", se);
            return ret;
        }
    }

	public boolean adminDisableUserAccount(User user) {
      boolean ret = false;
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();

        UserJpaController userJpaController = new UserJpaController(AppListener.getEntityManagerFactory());
        User registeredUser = userJpaController.findUserByEmailAddress(user.getEmailAddress());
        try {
            if(registeredUser == null) {
                logger.log(Level.WARNING, "user not found: " + user.getEmailAddress() + " by "
                        + request.getRemoteAddr() + " (" + request.getRemoteHost() + ")");
                ret = false;
            }else {
                if(!registeredUser.getUserStatus().equals(UserStatus.DISABLED)){
                    registeredUser.setUserStatus(UserStatus.DISABLED);
                }

                userJpaController.edit(registeredUser);
                ret = true;
            }
            return ret;
       
        } catch (RuntimeException se) {
            logger.log(Level.WARNING, "An error occurred in disabling user account", se);
            return ret;
        }
	}

}
