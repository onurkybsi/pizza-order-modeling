package com.modelDataProducer.PizzaStore.model;

public class CreditCard {
    private String nameOnCard;
    private long cardNumber;
    private int expirationMonth;
    private int expirationYear;
    private byte cVV;

    /**
     * @return String return the nameOnCard
     */
    public String getNameOnCard() {
        return nameOnCard;
    }

    /**
     * @param nameOnCard the nameOnCard to set
     */
    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    /**
     * @return long return the cardNumber
     */
    public long getCardNumber() {
        return cardNumber;
    }

    /**
     * @param cardNumber the cardNumber to set
     */
    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * @return int return the expirationMonth
     */
    public int getExpirationMonth() {
        return expirationMonth;
    }

    /**
     * @param expirationMonth the expirationMonth to set
     */
    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    /**
     * @return int return the expirationYear
     */
    public int getExpirationYear() {
        return expirationYear;
    }

    /**
     * @param expirationYear the expirationYear to set
     */
    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }

    /**
     * @return byte return the cVV
     */
    public byte getCVV() {
        return cVV;
    }

    /**
     * @param cVV the cVV to set
     */
    public void setCVV(byte cVV) {
        this.cVV = cVV;
    }

}
