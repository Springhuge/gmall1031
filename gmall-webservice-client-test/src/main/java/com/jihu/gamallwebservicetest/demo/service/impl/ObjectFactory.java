
package com.jihu.gamallwebservicetest.demo.service.impl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.jihu.gamallwebservicetest.demo.service.impl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetWeatherByCityname_QNAME = new QName("http://impl.service.demo.gamallwebservicetest.jihu.com/", "getWeatherByCityname");
    private final static QName _GetWeatherByCitynameResponse_QNAME = new QName("http://impl.service.demo.gamallwebservicetest.jihu.com/", "getWeatherByCitynameResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.jihu.gamallwebservicetest.demo.service.impl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetWeatherByCitynameResponse }
     * 
     */
    public GetWeatherByCitynameResponse createGetWeatherByCitynameResponse() {
        return new GetWeatherByCitynameResponse();
    }

    /**
     * Create an instance of {@link GetWeatherByCityname }
     * 
     */
    public GetWeatherByCityname createGetWeatherByCityname() {
        return new GetWeatherByCityname();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWeatherByCityname }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.demo.gamallwebservicetest.jihu.com/", name = "getWeatherByCityname")
    public JAXBElement<GetWeatherByCityname> createGetWeatherByCityname(GetWeatherByCityname value) {
        return new JAXBElement<GetWeatherByCityname>(_GetWeatherByCityname_QNAME, GetWeatherByCityname.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWeatherByCitynameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.service.demo.gamallwebservicetest.jihu.com/", name = "getWeatherByCitynameResponse")
    public JAXBElement<GetWeatherByCitynameResponse> createGetWeatherByCitynameResponse(GetWeatherByCitynameResponse value) {
        return new JAXBElement<GetWeatherByCitynameResponse>(_GetWeatherByCitynameResponse_QNAME, GetWeatherByCitynameResponse.class, null, value);
    }

}
