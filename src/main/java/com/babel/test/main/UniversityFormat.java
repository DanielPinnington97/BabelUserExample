package com.babel.test.main;

import com.BabelORM.BabelUser.BabelSQLResultsFilter;
import com.BabelORM.BabelUser.BabelStatementsSQL;
import com.BabelORM.BabelUser.BabelUserHistory;
import com.BabelORM.Settings.BabelSystems;
import com.babel.test.details.university;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UniversityFormat extends BabelSystems {

    public university saveUNI(university uni) throws SQLException {
        if (uni != null) {

            university existUni = getUniName(uni.name);
            if (existUni != null) {

                uni.UniID = existUni.UniID;
            }

            if (existUni != null) {
                return this.save(university.class, existUni).current;
            }
        }
        return null;

    }

    public university getUniById(long id) throws SQLException {
        return getUniWithHistoryById(id).current;

    }

    public BabelUserHistory<university> getUniWithHistoryById(long id) throws SQLException {
        BabelUserHistory<university> uni = new BabelUserHistory<>();

        // get the test
        if(id >= 0) {
            uni = this.getEntityById(university.class, id);

            // get the drivers
            uni.current.Stu = new StudentFormat().getStuByID(uni.current.UniID);
            for(university university: uni.hist) {
                university.Stu = new StudentFormat().getStuByID(university.UniID);
            }
        }

        return uni;
    }

    public BabelUserHistory<university> getUniWithHistByName(String name) {
        List<BabelUserHistory<university>> uniWithHistory = getEntitiesByValueForPassedField(university.class, "name", name);
        if(uniWithHistory.size() > 0) {
            return uniWithHistory.get(0);
        }
        return null;
    }

    public university getUniName(String name) {
        BabelUserHistory<university> histUni = getUniWithHistByName(name);
        if(histUni != null && histUni.current != null) {
            return histUni.current;
        }
        return null;
    }

    public List<BabelUserHistory<university>> getUniByYearWithHistory(int year) {

        List<BabelUserHistory<university>> uniByYear
                = getEntitiesByValueForPassedField(university.class, "year", Integer.toString(year));

        return uniByYear;
    }

    public List<university> getUniByYear(int year) {

        List<BabelUserHistory<university>> uniByYearWithHist = getUniByYearWithHistory(year);
        List<university> uni = new ArrayList<>();
        for(BabelUserHistory<university> uniWithHist: uniByYearWithHist) {
            if(uniWithHist.current != null) {
                uni.add(uniWithHist.current);
            }
        }
        return uni;
    }

    public BabelUserHistory<university> customUniQuery(String name) throws SQLException {
        BabelUserHistory<university> uni = new BabelUserHistory<>();

        if(uni != null) {

            // do the selection
            BabelSQLResultsFilter babelSQLResultsFilter = new BabelSQLResultsFilter();
            babelSQLResultsFilter.name = "name";
            babelSQLResultsFilter.operators = BabelSQLResultsFilter.Operators.EQUAL;
            babelSQLResultsFilter.comVal = name;
            babelSQLResultsFilter.primaryDataType = String.class;

            List<university> returnUni = BabelStatementsSQL.construct().where(babelSQLResultsFilter).run(university.class);

            return manageReturnedEntity(returnUni);
        }

        return uni;
    }

}
