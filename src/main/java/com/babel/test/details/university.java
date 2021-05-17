package com.babel.test.details;

import com.BabelORM.Annotation.BabelAnnotation;
import com.BabelORM.BabelUser.BabelUserDetails;

public class university extends BabelUserDetails {

    public String name = null;

    public String UniName = "";
    public String City = "";
    public Integer size = 0;
    public Boolean ispublicUni = false;
    public Integer year = 0;

    public Long UniID = -1L;

    @BabelAnnotation
    public student Stu = null;

    public university() {
        super();
    }

}
