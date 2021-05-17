package com.babel.test.main;

import com.BabelORM.BabelUser.BabelUserHistory;
import com.BabelORM.Settings.BabelSystems;
import com.babel.test.details.student;

import java.sql.SQLException;

public class StudentFormat extends BabelSystems {

    public student sStudent(student stu) throws SQLException {
        if(stu != null) {
            return this.save(student.class, stu).current;
        }
        return null;
    }

    public student getStuByID(long id) throws SQLException {
        return getStuWithHistoryById(id).current;

    }

    public BabelUserHistory<student> getStuWithHistoryById(long id) throws SQLException {
        BabelUserHistory<student> stu = new BabelUserHistory<>();

        // get the test
        if(id >= 0) {
            stu = this.getEntityById(student.class, id);
        }

        return stu;
    }

}
