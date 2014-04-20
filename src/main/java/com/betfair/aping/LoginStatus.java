package com.betfair.aping;

/**
 * Created by Paulo. 20-04-2014.
 */
public enum LoginStatus {
    /**
     * Login was successful
     */
    SUCCESS,
    /**
     * the username or password are invalid
     */
    INVALID_USERNAME_OR_PASSWORD,
    /**
     * the account was just locked
     */
    ACCOUNT_NOW_LOCKED,
    /**
     * the account is already locked
     */
    ACCOUNT_ALREADY_LOCKED,
    /**
     * pending authentication
     */
    PENDING_AUTH,
    /**
     * Telbet terms and conditions rejected
     */
    TELBET_TERMS_CONDITIONS_NA,
    /**
     * duplicate cards
     */
    DUPLICATE_CARDS,
    /**
     * the user has entered wrong the security question 3 times
     */
    SECURITY_QUESTION_WRONG_3X,
    /**
     * KYC suspended
     */
    KYC_SUSPEND,
    /**
     * the account is suspended
     */
    SUSPENDED,
    /**
     * the account is suspended
     */
    CLOSED,
    /**
     * the account has been self excluded
     */
    SELF_EXCLUDED,
    /**
     * the DK regulator cannot be accessed due to some internal problems in the system behind or in at regulator; timeout cases included.
     */
    INVALID_CONNECTIVITY_TO_REGULATOR_DK,
    /**
     * the user identified by the given credentials is not authorized in the DK's jurisdictions due to the regulators' policies. Ex: the user for which this session should be created is not allowed to act(play, bet) in the DK's jurisdiction.
     */
    NOT_AUTHORIZED_BY_REGULATOR_DK,
    /**
     * the IT regulator cannot be accessed due to some internal problems in the system behind or in at regulator; timeout cases included.
     */
    INVALID_CONNECTIVITY_TO_REGULATOR_IT,
    /**
     * the user identified by the given credentials is not authorized in the IT's jurisdictions due to the regulators' policies. Ex: the user for which this session should be created is not allowed to act(play, bet) in the IT's jurisdiction.
     */
    NOT_AUTHORIZED_BY_REGULATOR_IT,
    /**
     * the account is restricted due to security concerns
     */
    SECURITY_RESTRICTED_LOCATION,
    /**
     * the account is accessed from a location where betting is restricted
     */
    BETTING_RESTRICTED_LOCATION,
    /**
     * Trading Master Account
     */
    TRADING_MASTER,
    /**
     * Suspended Trading Master Account
     */
    TRADING_MASTER_SUSPENDED,
    /**
     * Agent Client Master
     */
    AGENT_CLIENT_MASTER,
    /**
     * Suspended Agent Client Master
     */
    AGENT_CLIENT_MASTER_SUSPENDED,
    /**
     * Danish authorization required
     */
    DANISH_AUTHORIZATION_REQUIRED,
    /**
     * Spain migration required
     */
    SPAIN_MIGRATION_REQUIRED,
    /**
     * Denmark migration required
     */
    DENMARK_MIGRATION_REQUIRED,
    /**
     * The latest spanish terms and conditions version must be accepted
     */
    SPANISH_TERMS_ACCEPTANCE_REQUIRED,
    /**
     * The latest Italian contract version must be accepted
     */
    ITALIAN_CONTRACT_ACCEPTANCE_REQUIRED,
    /**
     * Certificate required or certificate present but could not authenticate with it
     */
     CERT_AUTH_REQUIRED,
    /**
     * change password required
     */
    CHANGE_PASSWORD_REQUIRED,
    /**
     * personal message required for the user
     */
    PERSONAL_MESSAGE_REQUIRED;
}
