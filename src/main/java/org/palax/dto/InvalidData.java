package org.palax.dto;

/**
 * The {@code InvalidData} class is a data transfer object that is used to inform the view layer
 * about the incorrect input data from the user and determine what kind of class that is used for input field
 *
 * @author Taras Palashynskyy
 */

public class InvalidData {
    /**The value is used for store {@code invalidLogin}. */
    private String invalidLogin;
    /**The value is used for store {@code invalidFirstName}. */
    private String invalidFirstName;
    /**The value is used for store {@code invalidLastName}. */
    private String invalidLastName;
    /**The value is used for store {@code invalidStreet}. */
    private String invalidStreet;
    /**The value is used for store {@code invalidHouse}. */
    private String invalidHouse;
    /**The value is used for store {@code invalidCity}. */
    private String invalidCity;
    /**The value is used for store {@code invalidApartment}. */
    private String invalidApartment;
    /**The value is used for store {@code invalidPhoneNumber}. */
    private String invalidPhoneNumber;

    private InvalidData() {

    }

    /**
     * Returns {@code InvalidData} user input invalid login
     *
     * @return {@code invalidLogin} specifies what a class to use in view layer
     */
    public String getInvalidLogin() {
        return invalidLogin;
    }

    /**
     * Returns {@code InvalidData} user input invalid first name
     *
     * @return {@code invalidFirstName} specifies what a class to use in view layer
     */
    public String getInvalidFirstName() {
        return invalidFirstName;
    }

    /**
     * Returns {@code InvalidData} user input invalid last name
     *
     * @return {@code invalidLastName} specifies what a class to use in view layer
     */
    public String getInvalidLastName() {
        return invalidLastName;
    }

    /**
     * Returns {@code InvalidData} user input invalid street
     *
     * @return {@code invalidStreet} specifies what a class to use in view layer
     */
    public String getInvalidStreet() {
        return invalidStreet;
    }

    /**
     * Returns {@code InvalidData} user input invalid house number
     *
     * @return {@code invalidHouse} specifies what a class to use in view layer
     */
    public String getInvalidHouse() {
        return invalidHouse;
    }

    /**
     * Returns {@code InvalidData} user input invalid city
     *
     * @return {@code invalidCity} specifies what a class to use in view layer
     */
    public String getInvalidCity() {
        return invalidCity;
    }

    /**
     * Returns {@code InvalidData} user input invalid apartment
     *
     * @return {@code invalidApartment} specifies what a class to use in view layer
     */
    public String getInvalidApartment() {
        return invalidApartment;
    }

    /**
     * Returns {@code InvalidData} user input invalid phone number
     *
     * @return {@code invalidPhoneNumber} specifies what a class to use in view layer
     */
    public String getInvalidPhoneNumber() {
        return invalidPhoneNumber;
    }

    /**
     * Returns {@link Builder} for building {@link InvalidData} object
     *
     * @param attribute {@code attribute} specifies what a class to use in view layer
     * @return {@link Builder} for building {@link InvalidData} object
     */
    public static Builder newBuilder(String attribute) {
        return new InvalidData().new Builder(attribute);
    }

    /**
     * The {@code Builder} is a inner class which building {@link InvalidData} instance
     *
     * @author Taras Palashynskyy
     */
    public class Builder {
        /** The value is used to specifies what a class to use in view layer */
        private final String attribute;

        /**
         * Constructor to create {@link Builder} instance
         *
         * @param attribute {@code attribute} specifies what a class to use in view layer
         */
        private Builder(String attribute) {
            this.attribute = attribute;
        }


        /**
         * Sets {@code invalidLogin} to {@link InvalidData} instance
         *
         * @return {@link Builder} to invoke methods chains
         */
        public Builder setInvalidLoginAttr() {
            InvalidData.this.invalidLogin = attribute;

            return this;
        }

        /**
         * Sets {@code invalidFirstName} to {@link InvalidData} instance
         *
         * @return {@link Builder} to invoke methods chains
         */
        public Builder setInvalidFirstNameAttr() {
            InvalidData.this.invalidFirstName = attribute;

            return this;
        }

        /**
         * Sets {@code invalidLastName} to {@link InvalidData} instance
         *
         * @return {@link Builder} to invoke methods chains
         */
        public Builder setInvalidLastNameAttr() {
            InvalidData.this.invalidLastName = attribute;

            return this;
        }

        /**
         * Sets {@code invalidStreet} to {@link InvalidData} instance
         *
         * @return {@link Builder} to invoke methods chains
         */
        public Builder setInvalidStreetAttr() {
            InvalidData.this.invalidStreet = attribute;

            return this;
        }

        /**
         * Sets {@code invalidHouse} to {@link InvalidData} instance
         *
         * @return {@link Builder} to invoke methods chains
         */
        public Builder setInvalidHouseAttr() {
            InvalidData.this.invalidHouse = attribute;

            return this;
        }

        /**
         * Sets {@code invalidCity} to {@link InvalidData} instance
         *
         * @return {@link Builder} to invoke methods chains
         */
        public Builder setInvalidCityAttr() {
            InvalidData.this.invalidCity = attribute;

            return this;
        }

        /**
         * Sets {@code invalidApartment} to {@link InvalidData} instance
         *
         * @return {@link Builder} to invoke methods chains
         */
        public Builder setInvalidApartment() {
            InvalidData.this.invalidApartment = attribute;

            return this;
        }

        /**
         * Sets {@code invalidPhoneNumber} to {@link InvalidData} instance
         *
         * @return {@link Builder} to invoke methods chains
         */
        public Builder setInvalidPhoneNumber() {
            InvalidData.this.invalidPhoneNumber = attribute;

            return this;
        }

        /**
         * Method used to get initialized instance of {@link InvalidData}
         *
         * @return initialized instance of {@link InvalidData}
         */
        public InvalidData build() {
            return InvalidData.this;
        }
    }
}
