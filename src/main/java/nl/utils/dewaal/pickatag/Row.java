package nl.utils.dewaal.pickatag;


import org.jsefa.csv.annotation.CsvDataType;
import org.jsefa.csv.annotation.CsvField;

@CsvDataType
public class Row
{
    @CsvField(pos = 1)
    int    tagLineID;

    @CsvField(pos = 2)
    String  tagLineText;
}