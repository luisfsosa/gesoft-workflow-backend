/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * GESOFT-WORKFLOW-BACKEND
 */
package cl.com.gesoft.workflow.model;

import java.io.Serializable;

/**
 * The Class Address.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class Address implements Serializable {


    private static final long serialVersionUID = -4595227996314269890L;


    /**
     * Gets serial version uid.
     *
     * @return the serial version uid
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    private String postalCode;
    private String state;
    private String city;
    private String street;
    private String suburb;
    private String country;
    private String noExterior;
    private String noInterior;

    /**
     * Gets postal code.
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets postal code.
     *
     * @param postalCode the postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets street.
     *
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets street.
     *
     * @param street the street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets suburb.
     *
     * @return the suburb
     */
    public String getSuburb() {
        return suburb;
    }

    /**
     * Sets suburb.
     *
     * @param suburb the suburb
     */
    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets no exterior.
     *
     * @return the no exterior
     */
    public String getNoExterior() {
        return noExterior;
    }

    /**
     * Sets no exterior.
     *
     * @param noExterior the no exterior
     */
    public void setNoExterior(String noExterior) {
        this.noExterior = noExterior;
    }

    /**
     * Gets no interior.
     *
     * @return the no interior
     */
    public String getNoInterior() {
        return noInterior;
    }

    /**
     * Sets no interior.
     *
     * @param noInterior the no interior
     */
    public void setNoInterior(String noInterior) {
        this.noInterior = noInterior;
    }


    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Address {" +
                "postalCode='" + getPostalCode() + '\'' +
                ", state='" + getState() + '\'' +
                ", city='" + getCity() + '\'' +
                ", street='" + getStreet() + '\'' +
                ", suburb='" + getSuburb() + '\'' +
                ", country='" + getCountry() + '\'' +
                ", noExterior='" + getNoExterior() + '\'' +
                ", noInterior='" + getNoInterior() + '\'' +
                '}';
    }
}
