package com.example.tgbot21.service;

import com.example.tgbot21.dto.GetCursOnDateXml;
import com.example.tgbot21.dto.GetCursOnDateXmlResponse;
import com.example.tgbot21.dto.ValuteCursOnDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

//Данный класс наследуется от WebServiceTemplate, который предоставляет удобный способ взаимодействия с SOAP веб сервисами
public class CentralRussianBankService extends WebServiceTemplate {
    //Тут случается некоторая магия Spring и в момент запуска вашего приложения, сюда поставляется значение из application.properties или application.yml
    @Value("${cbr.api.url}")
    private String cbrApiUrl;

    //Создаем метод получения данных
    public List<ValuteCursOnDate> getCurrenciesFromCbr() throws DatatypeConfigurationException {
        final GetCursOnDateXml getCursOnDateXML = new GetCursOnDateXml();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());

        XMLGregorianCalendar xmlGregCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        getCursOnDateXML.setOnDate(xmlGregCal);

        GetCursOnDateXmlResponse response = (GetCursOnDateXmlResponse) marshalSendAndReceive(cbrApiUrl, getCursOnDateXML);

        if (response == null) {
            throw new IllegalStateException("Could not get response from CBR Service");
        }

        final List<ValuteCursOnDate> courses = response.getGetCursOnDateXmlResult().getValuteData();
        courses.forEach(course -> course.setName(course.getName().trim()));
        return courses;
    }
    public ValuteCursOnDate getCourseForCurrency(String code) throws DatatypeConfigurationException {
        return getCurrenciesFromCbr().stream().filter(currency -> code.equals(currency.getChCode())).findFirst().get();
    }

}