/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mobile;

/**
 *
 * @author Константин
 */
public class Confirmation {
    private String confirmationId;
    private String confirmationKey;
    private String confirmationOfferId;
    private String confirmationDescription;

    public Confirmation(String confirmationId, String confirmationKey, String confirmationOfferId, String confirmationDescription)
    {
        this.confirmationId = confirmationId;
        this.confirmationKey = confirmationKey;
        this.confirmationOfferId = confirmationOfferId;
        this.confirmationDescription = confirmationDescription;
    }

    /**
     * @return string
     */
    public String getConfirmationId()
    {
        return this.confirmationId;
    }

    /**
     * @return string
     */
    public String getConfirmationKey()
    {
        return this.confirmationKey;
    }

    public String getConfirmationDescription()
    {
        return this.confirmationDescription;
    }

    /**
     * @return string
     */
    public String getConfirmationOfferId()
    {
        return this.confirmationOfferId;
    }
}
