package app.wakayama.harusame.karaoke

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_play_room.*

// Access a Cloud Firestore instance from your Activity
val db = Firebase.firestore

class playRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_room)

        //選択されたルーム名を取得
        val selectedRoom: String = intent.getStringExtra("selectedRoom").toString()
        Log.d("TAG","get roomID => ${selectedRoom}")
        selectedRoomIdTextView.text = selectedRoom//部屋名を表示

        //コレクションネームは不変、ドキュメントIDがルーム名、データを未再生リストとする。
        val collectionName: String = "karaoke"

        //初期化用データ。roomIdとPassWordはmakeRoomからもらう
        val roomId: String = selectedRoom
        val PassWord: String = "admin"
        val defaultData = hashMapOf(//初期化する時のデータ
            "pass" to PassWord,
            "sound1" to 0,
            "sound2" to 0,
            "sound3" to 0,
            "sound4" to 0
        )
        //初期化していくぅ！
        db.collection(collectionName).document(roomId)
            .set(defaultData)
            .addOnSuccessListener {
                Log.d("TAG", "successed to initializing")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "failed to initializing", e)
            }

        //データをクラウドに保存する関数
        fun createData(roomId:String,musicID:String,times:Int){//ならす音楽のIDと回数を入力
            val playData = hashMapOf(
                musicID.toString() to times//key to dataという構造でデータを記録する。
            )

            db.collection(collectionName).document(roomId)
                .set(playData, SetOptions.merge())
                //ドキュメントIDを指定していれば、既にあるドキュメントにデータをセットするのがset.ドキュメントID不定でドキュメントごとデータを入れるのがadd
                //SetOptions.mergeは、既存のIDのドキュメントにデータを追加する際に統合する(上書きではなく追記)という意味
                .addOnSuccessListener {
                    Log.d("TAG", "データの追加に成功しました。")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "データを追加できなかったよ...", e)
                }
        }

        fun pushMusic(roomId:String,musicId:String){//指定したルームIDのデータをとってきて成功なら音楽を再生
            //読み取り
            db.collection(collectionName).document(roomId)
                .get()
                .addOnSuccessListener { document ->
                    val documentId = document.id
                    var playListTimes = document.data?.get(musicId).toString().toInt()//クラウドからmusicIdの未再生回数をロード
                    Log.d("TAG", "データの取得に成功しました。")
                    Log.d("TAG", "roomID:${documentId} => musicID:${document.data} DATA:${playListTimes}")
                    Log.d("TAG", "local played!!!")//音楽をローカルで再生
                    playListTimes += 1//未再生回数を1増やす
                    createData(roomId,musicId,playListTimes)//クラウドにアップ
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "データの取得に失敗しました。", exception)
                }
        }

        //受信側。更新があると音楽を再生。
        val docRef = db.collection(collectionName).document(roomId)
        docRef.addSnapshotListener { document, e ->//更新がある度に動く。
            if (e != null) {
                Log.w("TAG", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (document != null && document.exists()) {
//                Log.d("TAG", "Current data: ${document.data}")

                Log.d("TAG", "receive played!!!")//音楽を再生
                val drumSound = MediaPlayer.create(this,R.raw.cymbal1)//Rはresのこと。.でパスを表している
                drumSound.seekTo(0)
                drumSound.start()
            } else {
                Log.d("TAG", "Current data: null")
            }
        }



        backToMainFromPlayButton.setOnClickListener {
            val toMainIntent:Intent = Intent(this,MainActivity::class.java)
            startActivity(toMainIntent)
            finish()
        }

        playSound1Button.setOnClickListener{
            pushMusic(roomId,"sound1")
        }

    }
}