package com.example.UserApplication;

public class UtilRecords {



  public  record Notes(String notes, String title) {

        public Notes(String notes, String title) {
            this.notes = notes;
            this.title = title;
        }

        @Override
        public String notes() {
            return notes;
        }

        @Override
        public String title() {
            return title;
        }
    }

}
