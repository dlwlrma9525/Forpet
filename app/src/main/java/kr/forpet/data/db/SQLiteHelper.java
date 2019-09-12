package kr.forpet.data.db;

import android.content.Context;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import kr.forpet.R;

public class SQLiteHelper {

    public static AppDatabase assetsToDisk(Context context) {
        try {
            File file = context.getDatabasePath(context.getString(R.string.db_name));

            if (file.exists())
                return Room.databaseBuilder(context, AppDatabase.class, context.getString(R.string.db_name)).build();

            InputStream is = context.getAssets().open(context.getString(R.string.db_name));
            BufferedInputStream bis = new BufferedInputStream(is);
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            byte buffer[] = new byte[1024];

            int read;
            while ((read = bis.read(buffer, 0, 1024)) != -1)
                bos.write(buffer, 0, read);

            bos.flush();

            is.close();
            bis.close();
            fos.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO: database migration..
        return Room.databaseBuilder(context, AppDatabase.class, context.getString(R.string.db_name))
                .addMigrations(VERSION_1)
                .build();
    }

    // https://woovictory.github.io/2019/01/25/Android-Room-Basic/
    static final Migration VERSION_1 = new Migration(0, 1) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // database.execSQL("ALTER TABLE..");
        }
    };
}