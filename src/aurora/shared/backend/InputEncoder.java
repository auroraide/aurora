package aurora.shared.backend;

/**
 * This class reads the current Input and the current User Library entries and saves them into an URL. It uses goo.gl to shorten the link.
 */
public interface  InputEncoder {
    /**
     * This method takes the userinput in the textfield as a string and the current Userlibrary and
     * encodes them both into an url String
     * @param input the userinput in the textfield
     * @param userlibrary the current userlibrary
     * @return both parameters get converted into an encoded string.
     */
    public String encode(String input, Library userlibrary);

    /**
     * This method gets an encoded input and decodes the input.
     * @param encodedInput The encoded input
     * @return The decoded input
     */
    public String decode(String encodedInput);

    /**
     * Standard getter
     * @return Returns the decoded userlibrary
     */
    public Library getLibrary();

    /**
     * Standard getter
     * @return Returns the decoded input
     */
    public String getInput();

}
