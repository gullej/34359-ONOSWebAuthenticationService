package pojo;//import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

public class FlowObjective implements Serializable {

    private String flag;
    private int priority;
    private int timeout;
    private Boolean isPermanent;
    private String deviceId;
    private String operation;
    private Selector selector;
    private Treatment treatment;

    /**
     * No args constructor for use in serialization
     *
     */
    public FlowObjective() {
    }

    /**
     *
     * @param selector
     * @param operation
     * @param flag
     * @param treatment
     * @param priority
     * @param deviceId
     * @param timeout
     * @param isPermanent
     */
    public FlowObjective(String flag, int priority, int timeout, Boolean isPermanent, String deviceId, String operation, Selector selector, Treatment treatment) {
        super();
        this.flag = flag;
        this.priority = priority;
        this.timeout = timeout;
        this.isPermanent = isPermanent;
        this.deviceId = deviceId;
        this.operation = operation;
        this.selector = selector;
        this.treatment = treatment;
    }

    /**
     *
     * @return
     * The flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     *
     * @param flag
     * The flag
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     *
     * @return
     * The priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     *
     * @param priority
     * The priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     *
     * @return
     * The timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     *
     * @param timeout
     * The timeout
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     *
     * @return
     * The isPermanent
     */
    public Boolean getIsPermanent() {
        return isPermanent;
    }

    /**
     *
     * @param isPermanent
     * The isPermanent
     */
    public void setIsPermanent(Boolean isPermanent) {
        this.isPermanent = isPermanent;
    }

    /**
     *
     * @return
     * The deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     *
     * @param deviceId
     * The deviceId
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     *
     * @return
     * The operation
     */
    public String getOperation() {
        return operation;
    }

    /**
     *
     * @param operation
     * The operation
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     *
     * @return
     * The selector
     */
    public Selector getSelector() {
        return selector;
    }

    /**
     *
     * @param selector
     * The selector
     */
    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    /**
     *
     * @return
     * The treatment
     */
    public Treatment getTreatment() {
        return treatment;
    }

    /**
     *
     * @param treatment
     * The treatment
     */
    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

  /*  @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
 */
}
