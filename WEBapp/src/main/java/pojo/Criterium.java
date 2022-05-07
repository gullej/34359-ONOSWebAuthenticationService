package pojo;//import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Criterium implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ip;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String ethType;

    /**
     * No args constructor for use in serialization
     *
     */
    public Criterium() {
    }

    /**
     *
     * @param ethType
     * @param type
     * @param ip
     */
    public Criterium(String type, String ip, String ethType) {
        super();
        this.type = type;
        this.ip = ip;
        this.ethType = ethType;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The ip
     */
    public String getIp() {
        return ip;
    }

    /**
     *
     * @param ip
     * The ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     *
     * @return
     * The ethType
     */
    public String getEthType() {
        return ethType;
    }

    /**
     *
     * @param ethType
     * The ethType
     */
    public void setEthType(String ethType) {
        this.ethType = ethType;
    }

//    @Override
//    public String toString() {
//        return ToStringBuilder.reflectionToString(this);
//    }

}

