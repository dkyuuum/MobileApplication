package ddwu.com.mobile.project.ui

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class ExerciseDBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {

	val TAG = "ExerciseDBHelper"

	companion object {
		const val DB_NAME = "exercise_db"
		const val TABLE_NAME = "exercise_table"
		const val COL_exercise_date = "date"
		const val COL_exercise_name = "name"
		const val COL_exercise_content = "content"
		const val COL_exercise_time = "time"
	}

	override fun onCreate(db: SQLiteDatabase?) {
		val CREATE_TABLE =
			"CREATE TABLE $TABLE_NAME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
			"$COL_exercise_date TEXT, $COL_exercise_name TEXT, $COL_exercise_content TEXT, $COL_exercise_time TEXT)"

		db?.execSQL(CREATE_TABLE)

		db?.execSQL("INSERT INTO $TABLE_NAME VALUES (null, '2023/12/01', '주짓수', '라쏘가드', '45:00')")
		db?.execSQL("INSERT INTO $TABLE_NAME VALUES (null, '2023/12/03', '주짓수', '트라이앵글 초크', '45:00')")
		db?.execSQL("INSERT INTO $TABLE_NAME VALUES (null, '2023/12/07', '수영', '1000m', '28:00')")
		db?.execSQL("INSERT INTO $TABLE_NAME VALUES (null, '2023/12/15', '헬스', '러닝머신 5km', '28:00')")
		db?.execSQL("INSERT INTO $TABLE_NAME VALUES (null, '2023/12/18', '등산', '북한산 등반', '145:00')")
		db?.execSQL("INSERT INTO $TABLE_NAME VALUES (null, '2023/12/20', '수영', '자유형 1500m', '50:00')")

	}

	override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
		val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
		db?.execSQL(DROP_TABLE)
		onCreate(db)
	}
}