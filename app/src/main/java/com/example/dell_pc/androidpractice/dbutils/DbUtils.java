package com.example.dell_pc.androidpractice.dbutils;

import com.example.dell_pc.androidpractice.bean.Students;
import com.example.dell_pc.androidpractice.dao.DaoMaster;
import com.example.dell_pc.androidpractice.dao.StudentsDao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DbUtils {
    private static DbUtils dbUtils;
    private final StudentsDao dao;

    private DbUtils(){
        DaoMaster.DevOpenHelper db = new DaoMaster.DevOpenHelper(MyApp.getMyApp(), "my.db");
        dao = new DaoMaster(db.getWritableDatabase()).newSession().getStudentsDao();
    }

    public static DbUtils getDbUtils() {
        if (dbUtils == null) {
            synchronized (DbUtils.class){
                if (dbUtils == null) {
                    dbUtils=new DbUtils();
                }
            }
        }
        return dbUtils;
    }
    public boolean insert(Students students){
        if (!has2(students)){
            dao.insertOrReplace(students);
            return true;
        }else {
            return false;
        }
    }
    public ArrayList<Students> qurey(){
        ArrayList<Students> list = (ArrayList<Students>) dao.queryBuilder().list();
        return list;
    }
    public boolean delete(Students students){
        if (has2(students)){
            dao.delete(students);
            return true;
        }else {
            return false;
        }
    }

    private boolean has2(Students students) {
        List<Students> list = dao.queryBuilder().where(StudentsDao.Properties.Title.eq(students.getTitle()), StudentsDao.Properties.PicUrl.eq(students.getPicUrl())).list();
        if (list!=null&&list.size()>0){
            return true;
        }else {
            return false;
        }

    }

    private boolean has() {
        List<Students> list = dao.queryBuilder().list();
        if (list.size()>0){
            return true;
        }
        return false;
    }
}
