package com.example.taxpro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassInfo
{
    private static ClassInfo instance = new ClassInfo();
    private int theNumberOfStudent=22;

    private List<String> listOfSavingProduct= new ArrayList<>();
    private HashMap<Integer,String> studentMap= new HashMap<>();

    private ClassInfo() {}

    public static ClassInfo getInstance() { return instance; }

    public int getTheNumberOfStudent() { return instance.theNumberOfStudent; }
    public List<String> getListOfSavingProduct() { return instance.listOfSavingProduct; }
    public HashMap<Integer,String> getStudentMap() { return instance.studentMap; }

    public void setTheNumberOfStudent(int theNumberOfStudent) { instance.theNumberOfStudent = theNumberOfStudent; }
    public void setListOfSavingProduct(List<String> listOfSavingProduct) { instance.listOfSavingProduct = listOfSavingProduct; }
    public void setStudentMap(HashMap<Integer,String> studentMap) { instance.studentMap = studentMap; }
}
