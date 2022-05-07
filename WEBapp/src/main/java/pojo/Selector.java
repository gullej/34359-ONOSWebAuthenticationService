package pojo;

import java.io.Serializable;
import java.util.List;
//import org.apache.commons.lang.builder.ToStringBuilder;

public class Selector implements Serializable {

    private List<Criterium> criteria = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Selector() {
    }

    /**
     *
     * @param criteria
     */
    public Selector(List<Criterium> criteria) {
        super();
        this.criteria = criteria;
    }

    /**
     *
     * @return
     * The criteria
     */
    public List<Criterium> getCriteria() {
        return criteria;
    }

    /**
     *
     * @param criteria
     * The criteria
     */
    public void setCriteria(List<Criterium> criteria) {
        this.criteria = criteria;
    }

  //  @Override
  //  public String toString() {
   ///     return ToStringBuilder.reflectionToString(this);
//    }

}

