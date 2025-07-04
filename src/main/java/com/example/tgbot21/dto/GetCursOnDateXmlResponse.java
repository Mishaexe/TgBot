package com.example.tgbot21.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "GetCursOnDateXMLResponse", namespace = "http://web.cbr.ru/") //Указываем XML тег и пространство имен
@XmlAccessorType(XmlAccessType.FIELD) //Указываем, как получить/записать значения в поля данного объекта
@Data //Генерируем геттеры и сеттеры
public class GetCursOnDateXmlResponse {

    @XmlElement(name = "GetCursOnDateXMLResult", namespace = "http://web.cbr.ru/") //Имя xml элемента и его пространство имен
    private GetCursOnDateXmlResult getCursOnDateXmlResult;
}