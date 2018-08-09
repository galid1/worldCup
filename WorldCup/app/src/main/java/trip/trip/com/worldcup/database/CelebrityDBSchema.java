package trip.trip.com.worldcup.database;

/**
 * celebrity.db 스키마
 */
public class CelebrityDBSchema {

    public static final String DATABASE_NAME = "celebrity.db";

    public static final class CelebrityTable {
        public static final String NAME = "celebrity";

        public static final class Cols {
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String IMAGE_PATH = "image_path";
        }
    }

}
