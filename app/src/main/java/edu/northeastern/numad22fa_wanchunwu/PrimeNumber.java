package edu.northeastern.numad22fa_wanchunwu;

import static edu.northeastern.numad22fa_wanchunwu.R.id.runStatusText;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PrimeNumber extends AppCompatActivity{
    private static final String TAG = "_________PrimeNumber";
    private Handler textHandler = new Handler();
    static TextView statusText;

    public static Observer<String> mobileObserver = new Observer() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            Log.d(TAG, "onSubscribe() :: Someone just subscribed on me " );
        }

        @Override
        public void onNext(Object o) {
            Log.d(TAG, "onNext() :: Received value :: " + o);
            statusText.setText((String)o);
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onError() :: " + e.getMessage());
        }

        @Override
        public void onComplete() {
            Log.d(TAG, "onComplete() :: All items are emitted!");
        }
    };
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(runStatusText);
    }

    //Method which runs starts Observing
    public void startObservable(View view){

        Observable.just("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
                .subscribeOn(Schedulers.newThread()) // Schedulers can be thought of as a thread pool managing 1 or more threads
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mobileObserver);
    }

    //Method which runs on the UI thread.
    public void runOnMainThread(View view) {
        for (int i = 0; i < 10; i++) {
            Log.d(TAG, "Running on Main thread: " + i);
            try {
                Thread.sleep(1000); //Makes the thread sleep or be inactive for 10 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //Method which runs on a different thread which uses Thread class.
    public void runOnDifferentThread(View view) {
        differentThread differentThread = new differentThread();
        differentThread.start();
    }

    //Method which runs on a different thread which uses the Runnable interface.
    public void runOnRunnableThread(View view) {
        runnableThread runnableThread = new runnableThread();
        new Thread(runnableThread).start();
    }

    //Class which extends the Thread class.
    class differentThread extends Thread {
        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                final int finalI = i;
                //The handler changes the TextView running in the UI thread.
                textHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        statusText.setText("Running on a new Thread (Thread class): " + finalI);
                        if (finalI == 10) {
                            statusText.setText("");
                        }
                    }
                });
                Log.d(TAG, "Running on a different thread using Thread class: " + i);
                try {
                    Thread.sleep(1000); //Makes the thread sleep or be inactive for 10 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //Class which implements the Runnable interface.
    class runnableThread implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                final int finalI = i;
                //The handler changes the TextView running in the UI thread.
                textHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        statusText.setText("New thread (Runnable interface): " + finalI);
                        if (finalI == 10) {
                            statusText.setText("");
                        }
                    }
                });
                Log.d(TAG, "Running on a different thread using Runnable Interface: " + i);
                try {
                    Thread.sleep(1000); //Makes the thread sleep or be inactive for 10 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
