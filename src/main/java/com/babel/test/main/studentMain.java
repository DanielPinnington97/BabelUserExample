package com.babel.test.main;

import com.BabelORM.DBConfiguration.DBConfig.BabelConSettings;
import com.BabelORM.Settings.BabelSettings;
import com.BabelORM.Settings.BabelTesting;
import com.babel.test.details.student;
import com.babel.test.details.university;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class studentMain {
    public static void main(String[] args) {

        System.out.println("Initialising Babel ORM");

        List<Class> babelTest = new ArrayList<>();

        babelTest.add(student.class);
        babelTest.add(university.class);

        BabelTesting.startBabel(babelTest);

        try {
            persistStudentData();
        }
        catch (InterruptedException | SQLException e) {
            e.printStackTrace();
        }

    }

    private static void babelOptions() {

        BabelSettings settings = BabelSettings.getINST();

        settings.PERST = BabelConSettings.DB_TYPE.MARIADB;
        settings.PROT = BabelConSettings.DB_PROT.JDBC_MARIADB;

        settings.USERNAME = "root";
        settings.PASSWORD = "";

        settings.DBNAME = "babelStudentTest";

        settings.DBHOST = "localhost";

        settings.DBPORT = 3306;
    }

    private static void persistStudentData() throws InterruptedException, SQLException {

        StudentFormat sf = new StudentFormat();
        UniversityFormat uf = new UniversityFormat();

        // example students

        student daniel = new student();
        daniel.firstName = "daniel";
        daniel.lastName = "pinnington";
        daniel.phoneNumber = "07597484752";
        daniel = sf.sStudent(daniel);

        student emily = new student();
        emily.firstName = "emily";
        emily.lastName = "neild";
        emily.phoneNumber = "07598747852";
        emily = sf.sStudent(emily);

        student sophie = new student();
        sophie.firstName = "sophie";
        sophie.lastName = "pinnington";
        sophie.phoneNumber = "07585696542";
        sophie = sf.sStudent(emily);

        university mmu = new university();
        mmu.UniName = "MMU";
        mmu.City = "Manchester";
        mmu.size = 34000;
        mmu.year = 2020;
        mmu.ispublicUni = true;
        mmu.UniID = daniel.ID;
        uf.saveUNI(mmu);

        university uom = new university();
        uom.UniName = "Uni of Manchester";
        uom.City = "Manchester";
        uom.size = 40000;
        uom.ispublicUni = true;
        uom.UniID = emily.ID;
        uf.saveUNI(uom);

        university uol = new university();
        uol.UniName = "Uni of Liverpool";
        uol.City = "Liverpool";
        uol.size = 20000;
        uol.ispublicUni = true;
        uol.UniID = sophie.ID;
        uf.saveUNI(uol);

        Thread.sleep(5000);

        mmu = uf.getUniName("Manchester Metropolitan University");
        if (mmu != null) {
            System.out.println("University" + mmu.UniName + mmu.UniID);
        }

        mmu.UniID = daniel.ID;

        Thread.sleep(1000);
        uf.saveUNI(mmu);

        mmu = uf.getUniName("MMU");
        System.out.println("Uni" + mmu.UniName + mmu.UniID + mmu.Stu);

        List<university> uni = uf.getUniByYear(2020);
        for(university unis: uni) {
            if(unis.UniID >=0) {
                unis.Stu = sf.getStuByID(unis.UniID);
            }
            String student = (unis.Stu != null) ? unis.Stu.firstName : "empty";

            System.out.println("2020 uni " + unis.UniName + unis.City + unis.size);
        }

    }
}
