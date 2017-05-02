package com.theironyard;

/**
 * Created by JamesHartanto on 4/27/17.
 */
public class Dictator {
    private User user;
    private String overviewBlurb;
    private String overviewDictatorshipName;
    private String overviewMascot;
//    Picture
    private String overviewContentType;
    private byte[] overviewImage;

    private String econLabor;
    private String econTax;
    private String econTrade;
    private String econInfrastructure;
    private String econMilitary;

    private String socialHealthcare;
    private String socialRetirement;
    private String socialEducation;
    private String socialEnvironment;
    private String socialWelfare;

    private String legalPunishment;
    private String legalImmigration;
    private String legalVotingRights;
    private String legalPrivacyLaws;
    private String legalWeapons;

    private Integer revolt;
    private Integer pledge;

    public Dictator(User user, String overviewBlurb, String overviewDictatorshipName, String overviewMascot, String overviewContentType, byte[] overviewImage, String econLabor, String econTax, String econTrade, String econInfrastructure, String econMilitary, String socialHealthcare, String socialRetirement, String socialEducation, String socialEnvironment, String socialWelfare, String legalPunishment, String legalImmigration, String legalVotingRights, String legalPrivacyLaws, String legalWeapons, Integer revolt, Integer pledge) {
        this.user = user;
        this.overviewBlurb = overviewBlurb;
        this.overviewDictatorshipName = overviewDictatorshipName;
        this.overviewMascot = overviewMascot;
        this.overviewContentType = overviewContentType;
        this.overviewImage = overviewImage;
        this.econLabor = econLabor;
        this.econTax = econTax;
        this.econTrade = econTrade;
        this.econInfrastructure = econInfrastructure;
        this.econMilitary = econMilitary;
        this.socialHealthcare = socialHealthcare;
        this.socialRetirement = socialRetirement;
        this.socialEducation = socialEducation;
        this.socialEnvironment = socialEnvironment;
        this.socialWelfare = socialWelfare;
        this.legalPunishment = legalPunishment;
        this.legalImmigration = legalImmigration;
        this.legalVotingRights = legalVotingRights;
        this.legalPrivacyLaws = legalPrivacyLaws;
        this.legalWeapons = legalWeapons;
        this.revolt = revolt;
        this.pledge = pledge;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOverviewBlurb() {
        return overviewBlurb;
    }

    public void setOverviewBlurb(String overviewBlurb) {
        this.overviewBlurb = overviewBlurb;
    }

    public String getOverviewDictatorshipName() {
        return overviewDictatorshipName;
    }

    public void setOverviewDictatorshipName(String overviewDictatorshipName) {
        this.overviewDictatorshipName = overviewDictatorshipName;
    }

    public String getOverviewMascot() {
        return overviewMascot;
    }

    public void setOverviewMascot(String overviewMascot) {
        this.overviewMascot = overviewMascot;
    }

    public String getOverviewContentType() {
        return overviewContentType;
    }

    public void setOverviewContentType(String overviewContentType) {
        this.overviewContentType = overviewContentType;
    }

    public byte[] getOverviewImage() {
        return overviewImage;
    }

    public void setOverviewImage(byte[] overviewImage) {
        this.overviewImage = overviewImage;
    }

    public String getEconLabor() {
        return econLabor;
    }

    public void setEconLabor(String econLabor) {
        this.econLabor = econLabor;
    }

    public String getEconTax() {
        return econTax;
    }

    public void setEconTax(String econTax) {
        this.econTax = econTax;
    }

    public String getEconTrade() {
        return econTrade;
    }

    public void setEconTrade(String econTrade) {
        this.econTrade = econTrade;
    }

    public String getEconInfrastructure() {
        return econInfrastructure;
    }

    public void setEconInfrastructure(String econInfrastructure) {
        this.econInfrastructure = econInfrastructure;
    }

    public String getEconMilitary() {
        return econMilitary;
    }

    public void setEconMilitary(String econMilitary) {
        this.econMilitary = econMilitary;
    }

    public String getSocialHealthcare() {
        return socialHealthcare;
    }

    public void setSocialHealthcare(String socialHealthcare) {
        this.socialHealthcare = socialHealthcare;
    }

    public String getSocialRetirement() {
        return socialRetirement;
    }

    public void setSocialRetirement(String socialRetirement) {
        this.socialRetirement = socialRetirement;
    }

    public String getSocialEducation() {
        return socialEducation;
    }

    public void setSocialEducation(String socialEducation) {
        this.socialEducation = socialEducation;
    }

    public String getSocialEnvironment() {
        return socialEnvironment;
    }

    public void setSocialEnvironment(String socialEnvironment) {
        this.socialEnvironment = socialEnvironment;
    }

    public String getSocialWelfare() {
        return socialWelfare;
    }

    public void setSocialWelfare(String socialWelfare) {
        this.socialWelfare = socialWelfare;
    }

    public String getLegalPunishment() {
        return legalPunishment;
    }

    public void setLegalPunishment(String legalPunishment) {
        this.legalPunishment = legalPunishment;
    }

    public String getLegalImmigration() {
        return legalImmigration;
    }

    public void setLegalImmigration(String legalImmigration) {
        this.legalImmigration = legalImmigration;
    }

    public String getLegalVotingRights() {
        return legalVotingRights;
    }

    public void setLegalVotingRights(String legalVotingRights) {
        this.legalVotingRights = legalVotingRights;
    }

    public String getLegalPrivacyLaws() {
        return legalPrivacyLaws;
    }

    public void setLegalPrivacyLaws(String legalPrivacyLaws) {
        this.legalPrivacyLaws = legalPrivacyLaws;
    }

    public String getLegalWeapons() {
        return legalWeapons;
    }

    public void setLegalWeapons(String legalWeapons) {
        this.legalWeapons = legalWeapons;
    }

    public Integer getRevolt() {
        return revolt;
    }

    public void setRevolt(Integer revolt) {
        this.revolt = revolt;
    }

    public Integer getPledge() {
        return pledge;
    }

    public void setPledge(Integer pledge) {
        this.pledge = pledge;
    }
}
