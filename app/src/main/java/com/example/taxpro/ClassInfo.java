package com.example.taxpro;

import java.util.ArrayList;
import java.util.List;

public class ClassInfo
{
    private static ClassInfo instance = new ClassInfo();
    private int theNumberOfStudent=22;

    private List<String> listOfSavingProduct= new ArrayList<>();
    private List<String> studentList= new ArrayList<>();

    private ClassInfo() {}

    public static ClassInfo getInstance() { return instance; }

    public int getTheNumberOfStudent() { return instance.theNumberOfStudent; }
    public List<String> getListOfSavingProduct() { return instance.listOfSavingProduct; }
    public List<String> getStudentList() { return instance.studentList; }

    public void setTheNumberOfStudent(int theNumberOfStudent) { instance.theNumberOfStudent = theNumberOfStudent; }
    public void setListOfSavingProduct(List<String> listOfSavingProduct) { instance.listOfSavingProduct = listOfSavingProduct; }
    public void setStudentList(List<String> studentList) { instance.studentList = studentList; }
}
