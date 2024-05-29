package com.jadehh.androiddownload.utils;

import com.jadehh.androiddownload.mvp.e.DownloadTaskEntity;
import com.jadehh.androiddownload.ui.common.Const;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;


public class DBTools {
    private static DBTools dbTools=null;
    private DbManager.DaoConfig daoConfig;
    private DBTools(){
        FileTools.mkdirs(Const.DB_SDCARD_PATH);
        daoConfig = new DbManager.DaoConfig()
                .setDbName(Const.DB_NAME)
                .setDbDir(new File(Const.DB_SDCARD_PATH))
                .setDbVersion(2)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                }).setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        try {
                            db.dropTable(DownloadTaskEntity.class);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public static synchronized DBTools getInstance() {
        if (dbTools == null) {
            dbTools = new DBTools();
        }
        return dbTools;
    }

    public DbManager db() throws DbException {
        return x.getDb(daoConfig);
    }
}
