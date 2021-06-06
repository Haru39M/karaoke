package app.wakayama.harusame.karaoke

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.google.firebase.firestore.FieldValue
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

        //joinで選択されたルーム名を取得
        val selectedRoom: String = intent.getStringExtra("selectedRoom").toString()
        Log.d("TAG","get roomID => ${selectedRoom}")
        selectedRoomIdTextView.text = selectedRoom//部屋名を表示

        //コレクションネームは不変、ドキュメントIDがルーム名、データを未再生リストとする。
        val collectionName: String = "karaoke"

        //受け取ったIDとパスワード
        val roomId: String = selectedRoom

        //再生リストに+1する関数
        fun pushMusic(roomId:String,musicId:String){
            db.collection(collectionName).document(roomId)
                .update(musicId,FieldValue.increment(1))
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
                fun playMusic(soundId:String){
                    //未再生回数を取得
                    var playList:Int = document.data?.get(soundId).toString().toInt()
                    if(0 < playList){//再生リストに残っていれば
                        //音楽を再生
                        Log.d("TAG", "receive played!!!")
                        when(soundId){
                            "sound1" -> {
                                val drumSound = MediaPlayer.create(this,R.raw.hyoushigi)//Rはresのこと。.でパスを表している
                                drumSound.seekTo(0)
                                drumSound.start()
                                //データを更新
                                playList -= 1
                                val newData = hashMapOf(
                                    soundId to playList
                                )
                                docRef.set(newData, SetOptions.merge())//データを統合
                            }
                            "sound2" -> {
                                val drumSound = MediaPlayer.create(this,R.raw.hyoushigi2)//Rはresのこと。.でパスを表している
                                drumSound.seekTo(0)
                                drumSound.start()
                                //データを更新
                                playList -= 1
                                val newData = hashMapOf(
                                    soundId to playList
                                )
                                docRef.set(newData, SetOptions.merge())//データを統合
                            }
                            "sound3" -> {
                                val drumSound = MediaPlayer.create(this,R.raw.wadaiko)//Rはresのこと。.でパスを表している
                                drumSound.seekTo(0)
                                drumSound.start()
                                //データを更新
                                playList -= 1
                                val newData = hashMapOf(
                                    soundId to playList
                                )
                                docRef.set(newData, SetOptions.merge())//データを統合
                            }
                            "sound4" -> {
                                val drumSound = MediaPlayer.create(this,R.raw.wadaiko2)//Rはresのこと。.でパスを表している
                                drumSound.seekTo(0)
                                drumSound.start()
                                //データを更新
                                playList -= 1
                                val newData = hashMapOf(
                                    soundId to playList
                                )
                                docRef.set(newData, SetOptions.merge())//データを統合
                            }
                            "sound5" -> {
                                val drumSound = MediaPlayer.create(this,R.raw.dora)//Rはresのこと。.でパスを表している
                                drumSound.seekTo(0)
                                drumSound.start()
                                //データを更新
                                playList -= 1
                                val newData = hashMapOf(
                                    soundId to playList
                                )
                                docRef.set(newData, SetOptions.merge())//データを統合
                            }
                            "sound6" -> {
                                val drumSound = MediaPlayer.create(this,R.raw.castanet)//Rはresのこと。.でパスを表している
                                drumSound.seekTo(0)
                                drumSound.start()
                                //データを更新
                                playList -= 1
                                val newData = hashMapOf(
                                    soundId to playList
                                )
                                docRef.set(newData, SetOptions.merge())//データを統合
                            }
                            "sound7" -> {
                                val drumSound = MediaPlayer.create(this,R.raw.kozutsumi)//Rはresのこと。.でパスを表している
                                drumSound.seekTo(0)
                                drumSound.start()
                                //データを更新
                                playList -= 1
                                val newData = hashMapOf(
                                    soundId to playList
                                )
                                docRef.set(newData, SetOptions.merge())//データを統合
                            }
                            "sound8" -> {
                                val drumSound = MediaPlayer.create(this,R.raw.oh_man)//Rはresのこと。.でパスを表している
                                drumSound.seekTo(0)
                                drumSound.start()
                                //データを更新
                                playList -= 1
                                val newData = hashMapOf(
                                    soundId to playList
                                )
                                docRef.set(newData, SetOptions.merge())//データを統合
                            }
                        }
                    }
                }
                playMusic("sound1")
                playMusic("sound2")
                playMusic("sound3")
                playMusic("sound4")
                playMusic("sound5")
                playMusic("sound6")
                playMusic("sound7")
                playMusic("sound8")
            } else {
                Log.d("TAG", "Current data: null")
            }
        }



        backToMainFromPlayButton.setOnClickListener {

            val toMainIntent:Intent = Intent(this,MainActivity::class.java)
            startActivity(toMainIntent)
            finish()
        }

        //ボタンが押されると音楽を送信・再生
        playSound1Button.setOnClickListener{
            pushMusic(roomId,"sound1")
        }
        playSound2Button.setOnClickListener {
            pushMusic(roomId,"sound2")
        }
        playSound3Button.setOnClickListener {
            pushMusic(roomId,"sound3")
        }
        playSound4Button.setOnClickListener {
            pushMusic(roomId,"sound4")
        }
        playSound5Button.setOnClickListener {
            pushMusic(roomId,"sound5")
        }
        playSound6Button.setOnClickListener {
            pushMusic(roomId,"sound6")
        }
        playSound7Button.setOnClickListener {
            pushMusic(roomId,"sound7")
        }
        playSound8Button.setOnClickListener {
            pushMusic(roomId,"sound8")
        }

    }
    //入室処理
    override fun onResume() {
        super.onResume()
        //joinで選択されたルーム名を取得
        val selectedRoom: String = intent.getStringExtra("selectedRoom").toString()
        Log.d("TAG","get roomID => ${selectedRoom}")
        //コレクションネームは不変、ドキュメントIDがルーム名、データを未再生リストとする。
        val collectionName: String = "karaoke"
        //受け取ったIDとパスワード
        val roomId: String = selectedRoom

        //参加したので入室人数を+1する。
        db.collection(collectionName).document(roomId)
            .update("people",FieldValue.increment(1))
            .addOnSuccessListener {
                Log.d("TAG","入室しました。")
            }
            .addOnFailureListener {
                Log.d("TAG","入室人数の更新(入室)に失敗しました。")
            }
    }
    //退出処理
    override fun onPause() {
        super.onPause()
        //joinで選択されたルーム名を取得
        val selectedRoom: String = intent.getStringExtra("selectedRoom").toString()
        Log.d("TAG","get roomID => ${selectedRoom}")
        //コレクションネームは不変、ドキュメントIDがルーム名、データを未再生リストとする。
        val collectionName: String = "karaoke"
        //受け取ったIDとパスワード
        val roomId: String = selectedRoom

        //退出するので入室人数を-1する。
        db.collection(collectionName).document(roomId)
            .update("people",FieldValue.increment(-1))
            .addOnSuccessListener {document ->
                Log.d("TAG","退室しました。")
            }
            .addOnFailureListener {
                Log.d("TAG","入室人数の更新(退室)に失敗しました。")
            }
    }
}