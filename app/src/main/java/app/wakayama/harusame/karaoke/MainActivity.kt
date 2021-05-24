package app.wakayama.harusame.karaoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //goto makeroom
        makeRoomButton.setOnClickListener {
            val toMakeRoomIntent:Intent = Intent(this,makeRoomActivity::class.java)
            startActivity(toMakeRoomIntent)
        }
        //goto joinroom
        joinRoomButton.setOnClickListener {
            val toJoinRoomIntent:Intent = Intent(this,joinRoomActivity::class.java)
            startActivity(toJoinRoomIntent)
        }

    }
}