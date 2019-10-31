package com.jacob.reservationapp.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Reservation.class}, version = 2)
public abstract class ReservationDatabase extends RoomDatabase {

    private static ReservationDatabase instance;

    public abstract ReservationDao reservationDao();

    public static synchronized ReservationDatabase getInstance(Context context){
      if (instance == null){
          instance = Room.databaseBuilder(context.getApplicationContext(),
                  ReservationDatabase.class, "reservation_database")
                  .fallbackToDestructiveMigration()
                  .addCallback(roomCallback)
                  .build();
      }
      return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
//            new PopulateDbAsyncTask(instance).execute();
        }
    };
//    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
//        private ReservationDao reservationDao;
//
//        private PopulateDbAsyncTask(ReservationDatabase db){
//            reservationDao = db.reservationDao();
//        }
//        @Override
//        protected Void doInBackground(Void... voids) {
//            reservationDao.insert(new Reservation( 102010, 56568675, "asdsgd06312", "Undervisning", 3));
//            reservationDao.insert(new Reservation( 102010, 56568675, "asdsgd8634", "Møde", 3));
//            reservationDao.insert(new Reservation( 102010, 56568675, "DHFGJ75838", "Fordrag", 3));
//            return null;
//        }
//    }
}
