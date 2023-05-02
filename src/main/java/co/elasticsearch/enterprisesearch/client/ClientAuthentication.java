package co.elasticsearch.enterprisesearch.client;

import lombok.Getter;

/**
 * Represents different methods of authenticating with the App Search Server
 */
@Getter
public class ClientAuthentication {
    /**
     * Optional, if using BASIC authentication this is the username
     * @param username the username
     * @return The username
     */
    private final String username;
    /**
     * Optional, if using BASIC authentication this is the password
     * @param password the password
     * @return the password
     */
    private final String password;
    /**
     * Optional, Either an API Key or Elasticsearch tokens
     * @param token The API Key, or Elasticsearch token. Should NOT contain "Bearer " prefix
     * @return The API Key or token
     */
    private final String token;

    /**
     * Create an authentication object using a bearer token
     * @param token The token value. Generally starts with 'private-' or 'search-'
     * @return A token based ClientAuthentication
     */
    public static ClientAuthentication withBearerAuth(String token){
        if(token == null || token.isBlank()){
            throw new IllegalArgumentException("Token cannot be empty");
        }
        return new ClientAuthentication(token);
    }

    /**
     * Create an authentication object using username and password authentication
     * @param username the username
     * @param password the password
     * @return A client authentication object
     */
    public static ClientAuthentication withBasicAuth(String username, String password){
        if(username == null || password == null || username.isBlank() || password.isBlank()){
            throw new IllegalArgumentException("Username and/or Password cannot be empty");
        }
        return new ClientAuthentication(username,password);
    }

    boolean isTokenAuthentication(){
        return token != null;
    }
    private ClientAuthentication(String username,String password){
        this.username = username;
        this.password = password;
        this.token = null;
    }

    private ClientAuthentication(String token){
        this.username = null;
        this.password = null;
        this.token = token;
    }
}
